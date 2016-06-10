package com.happy.hipok.service;

import com.happy.hipok.config.locale.AngularCookieLocaleResolver;
import com.happy.hipok.domain.*;
import com.happy.hipok.domain.enumeration.MedicalSubType;
import com.happy.hipok.repository.*;
import com.happy.hipok.web.rest.app.dto.AppExtendedUserDTO;
import com.happy.hipok.web.rest.app.dto.RegisterAppExtendedUserDTO;
import com.happy.hipok.web.rest.app.mapper.AppExtendedUserMapper;
import com.happy.hipok.web.rest.dto.ExtendedUservalidationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class ExtendedUserService implements MessageSourceAware {

    /**
     *
     */
    private MessageSource messageSource;

    private final String messagePrefix = "extendedUserService.message.";

    private final String medicalTypeForProfessorAndDoctor = "medicalTypeForProfessorAndDoctor";

    private final String mandatoryRPPSNumberForMedical = "mandatoryRPPSNumberForMedical";

    private final String existingrppsnumber = "existingrppsnumber";

    private final String alreadyusedrppsandadelinumber = "alreadyusedrppsandadelinumber";

    private final String mandatoryADELINumberForParamedical = "mandatoryADELINumberForParamedical";

    private final String existingemail = "existingemail";

    /**
     *
     * @param messageSource
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private final Logger log = LoggerFactory.getLogger(ExtendedUserService.class);


    @Inject
    private ExtendedUserRepository extendedUserRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private SituationRefRepository situationRefRepository;

    @Inject
    private TitleRefRepository titleRefRepository;

    @Inject
    private MedicalTypeRefRepository medicalTypeRefRepository;

    @Inject
    private AppExtendedUserMapper appExtendedUserMapper;

    @Inject
    private RppsRefRepository rppsRefRepository;

    /**
     *
     */
    private AngularCookieLocaleResolver localeResolver = null;

    @Transactional
    public ExtendedUser createExtendedUser(RegisterAppExtendedUserDTO registerAppExtendedUserDTO) {

        ExtendedUser newExtendedUser = appExtendedUserMapper.extendedUserDTOToExtendedUser(registerAppExtendedUserDTO);

        ExtendedUser extendedUser = extendedUserRepository.save(newExtendedUser);

        log.debug("Created Information for ExtendedUser: {}", newExtendedUser);

        initProfile(extendedUser);

        return extendedUser;
    }

    @Transactional
    public void updateExtendedUser(AppExtendedUserDTO appExtendedUserDTO){

        if(isMedical(appExtendedUserDTO)){
            appExtendedUserDTO.setAdeliNumber("");
        }
        else{
            appExtendedUserDTO.setRppsRefNumber("");
        }

        ExtendedUser extendedUser = appExtendedUserMapper.appExtendedUserDTOtoExtendedUser(appExtendedUserDTO);

        extendedUserRepository.save(extendedUser);
    }

    private void initProfile(ExtendedUser createdExtendedUser) {
        Profile profile = new Profile();
        profile.setExtendedUser(createdExtendedUser);

        profileRepository.save(profile);
    }

    /**
     *
     * @param key
     * @param request
     * @return
     */
    private ExtendedUservalidationMessage getValidationMessage(String key, HttpServletRequest request){
        if(localeResolver == null){
            localeResolver = new AngularCookieLocaleResolver();
        }
        Locale locale = localeResolver.resolveLocale(request);

        ExtendedUservalidationMessage message = new ExtendedUservalidationMessage();
        message.setMessage(messageSource.getMessage(messagePrefix+key, null, locale));

        return message;
    }

    /**
     *
     * @param appExtendedUserDTO
     * @throws Exception
     */
    public ExtendedUservalidationMessage validateAppExtendedUserDTO(AppExtendedUserDTO appExtendedUserDTO, HttpServletRequest request) {

        String adeliNumber = appExtendedUserDTO.getAdeliNumber();

        String rppsNumber = appExtendedUserDTO.getRppsRefNumber();

        SituationRef situationRef = this.getSituationRef(appExtendedUserDTO);
        if(isStudentOrRetired(situationRef)){
            if((adeliNumber != null && !adeliNumber.isEmpty()) || (rppsNumber != null && !rppsNumber.isEmpty())){
                String noADELIAndRPPSNumberForStudentOrRetired = "noADELIAndRPPSNumberForStudentOrRetired";
                return getValidationMessage(noADELIAndRPPSNumberForStudentOrRetired,request);
            }
        }
        else{

            if(isDoctorOrProfessor(appExtendedUserDTO)){
                if(!isMedical(appExtendedUserDTO)){
                    return getValidationMessage(medicalTypeForProfessorAndDoctor,request);
                }
            }

            if(isMedical(appExtendedUserDTO)){
                if(rppsNumber == null || rppsNumber.isEmpty()){
                    return getValidationMessage(mandatoryRPPSNumberForMedical,request);
                }

                Optional<RppsRef> rppsRef = rppsRefRepository.findOneByNumber(rppsNumber);
                if (!rppsRef.isPresent()) {
                    return getValidationMessage(existingrppsnumber,request);
                }
                else{
                    Optional<ExtendedUser> extendedUserWithRppsRefNumber = extendedUserRepository.findOneByRppsRefNumber(rppsNumber);

                    if (extendedUserWithRppsRefNumber.isPresent()) {

                        //Si l'extendeduser existe.
                        if (appExtendedUserDTO.getId() == null || (appExtendedUserDTO.getId() != null && !extendedUserWithRppsRefNumber.get().getId().equals(appExtendedUserDTO.getId()))) {
                            return getValidationMessage(alreadyusedrppsandadelinumber,request);
                        }
                    }
                }
            }else{
                if(adeliNumber == null || adeliNumber.isEmpty()){
                    return getValidationMessage(mandatoryADELINumberForParamedical,request);
                }
            }
        }

        return null;
    }

    /**
     *
     * @param registerAppExtendedUserDTO
     * @throws Exception
     */
    public ExtendedUservalidationMessage validateRegisterAppExtendedUserDTO(RegisterAppExtendedUserDTO registerAppExtendedUserDTO, HttpServletRequest request){

        ExtendedUservalidationMessage message = this.validateAppExtendedUserDTO(registerAppExtendedUserDTO, request);
        if(message != null){
            return message;
        }

        if(registerAppExtendedUserDTO.getId() == null && userRepository.findOneByEmail(registerAppExtendedUserDTO.getEmail()).isPresent()){
            return getValidationMessage(existingemail,request);
        }

        return null;
    }

    /**
     *
     * @param situationRef
     * @return
     */
    private boolean isStudentOrRetired(SituationRef situationRef){
        if(situationRef.getCode().equals("E") || situationRef.getCode().equals("R")){
            return true;
        }
        return false;
    }

    /**
     *
     * @param appExtendedUserDTO
     * @return
     */
    private SituationRef getSituationRef(AppExtendedUserDTO appExtendedUserDTO) {
        SituationRef situationRef = null;
        Long situationRefId = appExtendedUserDTO.getSituationRefId();
        situationRef = situationRefRepository.findOne(situationRefId);
        return situationRef;
    }

    /**
     *
     * @param appExtendedUserDTO
     * @return
     */
    private boolean isMedical(AppExtendedUserDTO appExtendedUserDTO) {
        boolean isMedical = false;
        Long medicalTypeRefId = appExtendedUserDTO.getMedicalTypeRefId();
        MedicalTypeRef medicalTypeRef = medicalTypeRefRepository.findOne(medicalTypeRefId);
        isMedical = MedicalSubType.MEDICAL.equals(medicalTypeRef.getSubtype());
        return isMedical;
    }

    /**
     *
     * @param appExtendedUserDTO
     * @return
     */
    private boolean isDoctorOrProfessor(AppExtendedUserDTO appExtendedUserDTO){
        TitleRef titleRef = null;
        Long TitleRefId = appExtendedUserDTO.getTitleRefId();
        titleRef = titleRefRepository.findOne(TitleRefId);
        if(titleRef.getCode().equals("PR") || titleRef.getCode().equals("DR")){
            return true;
        }
        return false;
    }
}
