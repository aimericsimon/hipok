package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.domain.Device;
import com.happy.hipok.domain.Profile;
import com.happy.hipok.repository.DeviceRepository;
import com.happy.hipok.service.ProfileService;
import com.happy.hipok.web.rest.app.dto.AppDeviceDTO;
import com.happy.hipok.web.rest.errors.CustomParameterizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Device .
 */
@RestController
@RequestMapping("/app")
public class AppDeviceResource {

    private final Logger log = LoggerFactory.getLogger(AppDeviceResource.class);

    @Inject
    private ProfileService profileService;

    @Inject
    private DeviceRepository deviceRepository;

    /**
     * POST  /device -> Add or update a device
     */
    @RequestMapping(value = "/device",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppDeviceDTO> addOrUpdateDevice(@Valid @RequestBody AppDeviceDTO appDeviceDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to add or update device");

        Profile currentProfile = profileService.getCurrentProfile();
        if (currentProfile == null) {
            throw new CustomParameterizedException("Pas de profil pour cet utilisateur.");
        }

        if(!appDeviceDTO.getType().equals(Device.IOSDevice) && !appDeviceDTO.getType().equals(Device.AndroidDevice)){
            throw new CustomParameterizedException(appDeviceDTO.getType()+" n'est pas supporté");
        }

        Optional<Device> device = deviceRepository.findDevice(currentProfile.getId(),appDeviceDTO.getToken(),appDeviceDTO.getType());
        if(device.isPresent()){
            device.get().setEnabled(appDeviceDTO.isEnabled());
            deviceRepository.save(device.get());
        }
        else{
            Device newDevice = new Device();
            newDevice.setEnabled(appDeviceDTO.isEnabled());
            newDevice.setToken(appDeviceDTO.getToken());
            newDevice.setType(appDeviceDTO.getType());
            newDevice.setDeviceProfile(currentProfile);
            deviceRepository.save(newDevice);
        }

        return new ResponseEntity<AppDeviceDTO>(HttpStatus.OK);
    }
}
