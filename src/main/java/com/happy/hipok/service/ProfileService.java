package com.happy.hipok.service;

import com.happy.hipok.domain.*;
import com.happy.hipok.repository.*;
import com.happy.hipok.web.rest.app.dto.AppProfileDTO;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileService.class);

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private ExtendedUserRepository extendedUserRepository;

    @Inject
    private PublicationService publicationService;

    @Inject
    private DeclarationRepository declarationRepository;

    @PersistenceContext
    EntityManager entityManager;

    /**
     * @return profile which is currently authenticated.
     */
    public Profile getCurrentProfile() {
        return profileRepository.getCurrentProfile();
    }

    /**
     * Search profile by email or first name or last name
     * @param query
     * @param pageable
     * @return
     */
    public Page<Profile> search(String query, Pageable pageable){

        List<Profile> result = new ArrayList<>();

        String[] tokens = query.trim().toUpperCase().split(" ");

        if(tokens != null && tokens.length > 0){

            String login = this.getCurrentProfile().getExtendedUser().getUser().getLogin();

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<Profile> criteriaQuery = criteriaBuilder.createQuery(Profile.class);

            Root<Profile> profileRoot = criteriaQuery.from(Profile.class);

            Join<Profile,ExtendedUser> extendedUser = profileRoot.join("extendedUser");

            Join<ExtendedUser,User> user = extendedUser.join("user");

            //On n'inclut pas le profile qui fait la recherche.
            Predicate noCurrentProfilePredicate = criteriaBuilder.notEqual(user.get("login"),login);

            List<Predicate> predicates = new ArrayList<>();

            for(String token : tokens) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(user.get("email")),"%"+token+"%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(user.get("firstName")),"%"+token+"%"));
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(user.get("lastName")),"%"+token+"%"));
            }

            Predicate orPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[] {}));

            Predicate andPredicate = criteriaBuilder.and(noCurrentProfilePredicate,orPredicate);

            criteriaQuery = criteriaQuery.where(andPredicate);

            result = entityManager.createQuery(criteriaQuery).getResultList();
        }

        return new PageImpl<>(result,pageable,result.size());
    }

    /**
     *
     * @param pageable
     * @return
     */
    public Page<Suggestion> getSuggestions(Pageable pageable)
    {
        Profile currentProfile = this.getCurrentProfile();
        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        Page<Suggestion> list = profileRepository.findProfileBySuggestion(pageable);

        log.debug("Suggestions count for "+currentProfile.getId()+":"+list.getContent().size());

        //Filter all suggestion with followers and city and medicaltype in common
        List<Suggestion> list1 = list.getContent().stream()
            .filter(
                p -> p.getCount() > 0
                    && p.getProfile().getExtendedUser().getAddress().getCity().equals(currentProfile.getExtendedUser().getAddress().getCity())
                    && p.getProfile().getExtendedUser().getMedicalTypeRef().getId() == currentProfile.getExtendedUser().getMedicalTypeRef().getId()
            ).collect(Collectors.toList());

        //Sort the suggestion with followers by followers count desc
        list1.sort(new Comparator<Suggestion>() {
            @Override
            public int compare(Suggestion s1, Suggestion s2) {
               return s2.getCount().compareTo(s1.getCount());
            }
        });

        //Filter all suggestion with same city and same medical type
        List<Suggestion> list2 =  list.getContent().stream()
            .filter(
                p ->
                    p.getProfile().getExtendedUser().getAddress().getCity().equals(currentProfile.getExtendedUser().getAddress().getCity())
                    && p.getProfile().getExtendedUser().getMedicalTypeRef().getId() == currentProfile.getExtendedUser().getMedicalTypeRef().getId()
            ).collect(Collectors.toList());

        //Filter all suggestion with no followers in common but with same city
        List<Suggestion> list3 =  list.getContent().stream()
            .filter(
                p -> p.getProfile().getExtendedUser().getAddress().getCity().equals(currentProfile.getExtendedUser().getAddress().getCity())
            ).collect(Collectors.toList());

        //The final suggestion list
        List<Suggestion> finalList = new ArrayList<>();

        list1.stream().forEach(p -> finalList.add(p));

        for (Suggestion suggestion: list2) {
            if(finalList.stream().filter(p -> p.getProfile().getId() == suggestion.getProfile().getId()).count() == 0){
                finalList.add(suggestion);
            }
        }

        for (Suggestion suggestion: list3) {
            if(finalList.stream().filter(p -> p.getProfile().getId() == suggestion.getProfile().getId()).count() == 0){
                finalList.add(suggestion);
            }
        }

        Page<Suggestion> newPage = new PageImpl<>(finalList,pageable,list.getTotalElements());

        return newPage;
    }

    /**
     *
     * @param profile
     * @param paths
     */
    public void setImageUrls(Profile profile, String[] paths) {
        profile.setAvatarUrl(paths[0]);
        profile.setAvatarThumbnailUrl(paths[1]);
    }

    /**
     *
     * @param dto
     */
    public void updateProfile(AppProfileDTO dto){
        Profile profile = profileRepository.findOne(dto.getId());
        profile.setDescription(dto.getDescription());

        ExtendedUser extendedUser = profile.getExtendedUser();

        User user = extendedUser.getUser();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        extendedUser.setBirthDate(dto.getBirthDate());
        extendedUser.setPracticeLocation(dto.getPracticeLocation());

        Long medicalTypeRefId = dto.getMedicalTypeRefId();
        if (medicalTypeRefId != null) {
            MedicalTypeRef medicalTypeRef = new MedicalTypeRef();
            medicalTypeRef.setId(medicalTypeRefId);
            extendedUser.setMedicalTypeRef(medicalTypeRef);
        }
        else{
            extendedUser.setMedicalTypeRef(null);
        }

        Long titleRefId = dto.getTitleRefId();
        if (titleRefId != null) {
            TitleRef titleRef = new TitleRef();
            titleRef.setId(titleRefId);
            extendedUser.setTitleRef(titleRef);
        }
        else{
            extendedUser.setMedicalTypeRef(null);
        }

        Long situationRefId = dto.getSituationRefId();
        if (situationRefId != null) {
            SituationRef situationRef = new SituationRef();
            situationRef.setId(situationRefId);
            extendedUser.setSituationRef(situationRef);
        }

        Address address = extendedUser.getAddress();
        address.setCity(dto.getAddress().getCity());
        address.setLabel(dto.getAddress().getLabel());
        address.setPostalCode(dto.getAddress().getPostalCode());

        userRepository.save(user);

        addressRepository.save(address);

        extendedUserRepository.save(extendedUser);

        profileRepository.save(profile);
    }

    public void delete(Profile profile){
        publicationService.deleteProfilePublications();

        declarationRepository.delete(declarationRepository.findAllByProfile(profile));

        if(profile.getExtendedUser() != null && profile.getExtendedUser().getUser() != null){
            userRepository.delete(profile.getExtendedUser().getUser());
        }

        if(profile.getExtendedUser() != null){
            if(profile.getExtendedUser().getAddress() != null){
                addressRepository.delete(profile.getExtendedUser().getAddress() );
            }

            extendedUserRepository.delete(profile.getExtendedUser());
        }

        profileRepository.delete(profile);
    }
}
