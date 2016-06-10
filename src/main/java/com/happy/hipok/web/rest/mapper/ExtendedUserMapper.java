package com.happy.hipok.web.rest.mapper;

import com.happy.hipok.domain.*;
import com.happy.hipok.web.rest.dto.ExtendedUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExtendedUser and its DTO ExtendedUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ExtendedUserMapper {

    @Mapping(source = "medicalTypeRef.id", target = "medicalTypeRefId")
    @Mapping(source = "rppsRef.id", target = "rppsRefId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "titleRef.id", target = "titleRefId")
    @Mapping(source = "titleRef.label", target = "titleRefLabel")
    @Mapping(source = "situationRef.id", target = "situationRefId")
    ExtendedUserDTO extendedUserToExtendedUserDTO(ExtendedUser extendedUser);

    @Mapping(source = "medicalTypeRefId", target = "medicalTypeRef")
    @Mapping(source = "rppsRefId", target = "rppsRef")
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "addressId", target = "address")
    @Mapping(source = "titleRefId", target = "titleRef")
    @Mapping(source = "situationRefId", target = "situationRef")
    ExtendedUser extendedUserDTOToExtendedUser(ExtendedUserDTO extendedUserDTO);

    default MedicalTypeRef medicalTypeRefFromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicalTypeRef medicalTypeRef = new MedicalTypeRef();
        medicalTypeRef.setId(id);
        return medicalTypeRef;
    }

    default RppsRef rppsRefFromId(Long id) {
        if (id == null) {
            return null;
        }
        RppsRef rppsRef = new RppsRef();
        rppsRef.setId(id);
        return rppsRef;
    }

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Address addressFromId(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }

    default TitleRef titleRefFromId(Long id) {
        if (id == null) {
            return null;
        }
        TitleRef titleRef = new TitleRef();
        titleRef.setId(id);
        return titleRef;
    }

    default SituationRef situationRefFromId(Long id) {
        if (id == null) {
            return null;
        }
        SituationRef situationRef = new SituationRef();
        situationRef.setId(id);
        return situationRef;
    }
}
