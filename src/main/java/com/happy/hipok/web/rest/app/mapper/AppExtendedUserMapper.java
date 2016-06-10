package com.happy.hipok.web.rest.app.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.repository.AddressRepository;
import com.happy.hipok.repository.AuthorityRepository;
import com.happy.hipok.repository.ExtendedUserRepository;
import com.happy.hipok.repository.RppsRefRepository;
import com.happy.hipok.web.rest.app.dto.AppExtendedUserDTO;
import com.happy.hipok.web.rest.app.dto.RegisterAppExtendedUserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Mapper for the entity ExtendedUser and its DTO RegisterAppExtendedUserDTO.
 */
@Component
public class AppExtendedUserMapper {

    @Inject
    private AddressRepository addressRepository;

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private RppsRefRepository rppsRefRepository;

    @Inject
    private ExtendedUserRepository extendedUserRepository;

    /**
     * Map RegisterAppExtendedUserDTO received via API to Extendeduser entity.
     *
     * @param registerAppExtendedUserDTO
     * @return extended user
     */
    public ExtendedUser extendedUserDTOToExtendedUser(RegisterAppExtendedUserDTO registerAppExtendedUserDTO) {
        if (registerAppExtendedUserDTO == null) {
            return null;
        }

        User user = userDTOToUser(registerAppExtendedUserDTO);

        ExtendedUser extendedUser = new ExtendedUser();

        extendedUser.setId(registerAppExtendedUserDTO.getId());
        extendedUser.setUser(user);
        extendedUser.setBirthDate(registerAppExtendedUserDTO.getBirthDate());
        extendedUser.setSex(registerAppExtendedUserDTO.getSex());
        extendedUser.setPracticeLocation(registerAppExtendedUserDTO.getPracticeLocation());
        extendedUser.setAdeliNumber(registerAppExtendedUserDTO.getAdeliNumber());
        extendedUser.setAddress(registerAppExtendedUserDTO.getAddress());

        Long medicalTypeRefId = registerAppExtendedUserDTO.getMedicalTypeRefId();
        if (medicalTypeRefId != null) {
            MedicalTypeRef medicalTypeRef = new MedicalTypeRef();
            medicalTypeRef.setId(medicalTypeRefId);
            extendedUser.setMedicalTypeRef(medicalTypeRef);
        }

        Long situationRefId = registerAppExtendedUserDTO.getSituationRefId();
        if (situationRefId != null) {
            SituationRef situationRef = new SituationRef();
            situationRef.setId(situationRefId);
            extendedUser.setSituationRef(situationRef);
        }

        Long titleRefId = registerAppExtendedUserDTO.getTitleRefId();
        if (titleRefId != null) {
            TitleRef titleRef = new TitleRef();
            titleRef.setId(titleRefId);
            extendedUser.setTitleRef(titleRef);
        }

        Optional<RppsRef> rppsRef = rppsRefRepository.findOneByNumber(registerAppExtendedUserDTO.getRppsRefNumber());
        if (rppsRef.isPresent()) {
            extendedUser.setRppsRef(rppsRef.get());
        }

        return extendedUser;
    }

    /**
     *
     * @param registerAppExtendedUserDTO
     * @return
     */
    private User userDTOToUser(RegisterAppExtendedUserDTO registerAppExtendedUserDTO) {
        User newUser = new User();

        Authority authority = authorityRepository.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        newUser.setAuthorities(authorities);

        String encryptedPassword = passwordEncoder.encode(registerAppExtendedUserDTO.getPassword());
        newUser.setLogin(registerAppExtendedUserDTO.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(registerAppExtendedUserDTO.getFirstName());
        newUser.setLastName(registerAppExtendedUserDTO.getLastName());
        newUser.setEmail(registerAppExtendedUserDTO.getEmail());
        newUser.setLangKey("fr");
        newUser.setActivated(registerAppExtendedUserDTO.isActivated());

        return newUser;
    }

    /**
     *
     * @param appExtendedUserDTO
     * @return
     */
    public ExtendedUser appExtendedUserDTOtoExtendedUser(AppExtendedUserDTO appExtendedUserDTO){
        ExtendedUser extendedUser = extendedUserRepository.findOne(appExtendedUserDTO.getId());

        User user = extendedUser.getUser();

        user.setFirstName(appExtendedUserDTO.getFirstName());
        user.setLastName(appExtendedUserDTO.getLastName());

        extendedUser.setBirthDate(appExtendedUserDTO.getBirthDate());
        extendedUser.setPracticeLocation(appExtendedUserDTO.getPracticeLocation());
        extendedUser.setAdeliNumber(appExtendedUserDTO.getAdeliNumber());
        extendedUser.setSex(appExtendedUserDTO.getSex());

        Long medicalTypeRefId = appExtendedUserDTO.getMedicalTypeRefId();
        if (medicalTypeRefId != null) {
            MedicalTypeRef medicalTypeRef = new MedicalTypeRef();
            medicalTypeRef.setId(medicalTypeRefId);
            extendedUser.setMedicalTypeRef(medicalTypeRef);
        }
        else{
            extendedUser.setMedicalTypeRef(null);
        }

        Long situationRefId = appExtendedUserDTO.getSituationRefId();
        if (situationRefId != null) {
            SituationRef situationRef = new SituationRef();
            situationRef.setId(situationRefId);
            extendedUser.setSituationRef(situationRef);
        }

        Long titleRefId = appExtendedUserDTO.getTitleRefId();
        if (titleRefId != null) {
            TitleRef titleRef = new TitleRef();
            titleRef.setId(titleRefId);
            extendedUser.setTitleRef(titleRef);
        }
        else{
            extendedUser.setMedicalTypeRef(null);
        }

        Optional<RppsRef> rppsRef = rppsRefRepository.findOneByNumber(appExtendedUserDTO.getRppsRefNumber());
        if (rppsRef.isPresent()) {
            extendedUser.setRppsRef(rppsRef.get());
        }

        Address address = extendedUser.getAddress();
        address.setCity(appExtendedUserDTO.getAddress().getCity());
        address.setLabel(appExtendedUserDTO.getAddress().getLabel());
        address.setPostalCode(appExtendedUserDTO.getAddress().getPostalCode());

        return extendedUser;
    }

    /**
     *
     * @param extendedUser
     * @return
     */
    public AppExtendedUserDTO extendedUserToappExtendedUserDTO(ExtendedUser extendedUser){
        if (extendedUser == null) {
            return null;
        }

        AppExtendedUserDTO appExtendedUserDTO = new AppExtendedUserDTO();

        appExtendedUserDTO.setTitleRefId(extendedUserTitleRefId(extendedUser));
        appExtendedUserDTO.setSituationRefId(extendedUserSituationRefId(extendedUser));
        appExtendedUserDTO.setMedicalTypeRefId(extendedUserMedicalTypeRefId(extendedUser));
        appExtendedUserDTO.setId(extendedUser.getId());
        appExtendedUserDTO.setBirthDate(extendedUser.getBirthDate());
        appExtendedUserDTO.setSex(extendedUser.getSex());
        appExtendedUserDTO.setPracticeLocation(extendedUser.getPracticeLocation());
        appExtendedUserDTO.setAdeliNumber(extendedUser.getAdeliNumber());
        appExtendedUserDTO.setAddress(extendedUser.getAddress());
        appExtendedUserDTO.setLangKey(extendedUser.getUser().getLangKey());
        appExtendedUserDTO.setFirstName(extendedUser.getUser().getFirstName());
        appExtendedUserDTO.setLastName(extendedUser.getUser().getLastName());
        appExtendedUserDTO.setRppsRefNumber(extendedUserRppsRefNumber(extendedUser));
        return appExtendedUserDTO;
    }

    private Long extendedUserTitleRefId(ExtendedUser extendedUser) {

        if (extendedUser == null) {
            return null;
        }
        TitleRef titleRef = extendedUser.getTitleRef();
        if (titleRef == null) {
            return null;
        }
        Long id = titleRef.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private Long extendedUserSituationRefId(ExtendedUser extendedUser) {

        if (extendedUser == null) {
            return null;
        }
        SituationRef situationRef = extendedUser.getSituationRef();
        if (situationRef == null) {
            return null;
        }
        Long id = situationRef.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private Long extendedUserMedicalTypeRefId(ExtendedUser extendedUser) {

        if (extendedUser == null) {
            return null;
        }
        MedicalTypeRef medicalTypeRef = extendedUser.getMedicalTypeRef();
        if (medicalTypeRef == null) {
            return null;
        }
        Long id = medicalTypeRef.getId();
        if (id == null) {
            return null;
        }
        return id;
    }

    private String extendedUserRppsRefNumber(ExtendedUser extendedUser){
        if (extendedUser == null) {
            return null;
        }

        RppsRef rppsRef = extendedUser.getRppsRef();
        if (rppsRef == null) {
            return null;
        }

        return rppsRef.getNumber();
    }
}
