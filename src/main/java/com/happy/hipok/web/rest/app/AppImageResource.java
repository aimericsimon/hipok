package com.happy.hipok.web.rest.app;

import com.codahale.metrics.annotation.Timed;
import com.happy.hipok.Application;
import com.happy.hipok.domain.Image;
import com.happy.hipok.repository.ImageRepository;
import com.happy.hipok.service.ImageService;
import com.happy.hipok.web.rest.app.dto.AppImageDTO;
import com.happy.hipok.web.rest.app.mapper.AppImageMapper;
import com.happy.hipok.web.rest.util.HeaderUtil;
import com.happy.hipok.web.rest.util.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/app")
public class AppImageResource {

    private final Logger log = LoggerFactory.getLogger(AppImageResource.class);

    @Inject
    private ImageService imageService;

    @Inject
    private ImageRepository imageRepository;

    @Inject
    private AppImageMapper appImageMapper;

    /**
     * POST  /iimage -> Create a new image.
     */
    @RequestMapping(value = "/images",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppImageDTO> createImage(@Valid @RequestBody AppImageDTO appPublicationDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save Image");

        String[] paths = imageService.persistFullImageAndThumbnail(appPublicationDTO.getImageBinary());

        Image image = new Image();
        image.setImageUrl(paths[0]);
        image.setImageThumbnailUrl(paths[1]);

        Image result  = imageRepository.save(image);

        return ResponseEntity.created(new URI("/app/images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("image", result.getId().toString()))
            .body(appImageMapper.imageToImageDTO(result, RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY));
    }

    /**
     * POST  /images/:imageId -> get image info.
     */
    @RequestMapping(value = "/images/{imageId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AppImageDTO> getImage(@PathVariable Long imageId, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to get Image "+imageId.toString());

        Image result  = imageRepository.getOne(imageId);

        return Optional.ofNullable(result)
            .map(model -> appImageMapper.imageToImageDTO(result,RequestUtils.getBaseUrl(request) + "/" + Application.UPLOAD_DIRECTORY))
            .map(modelDTO -> new ResponseEntity<>(
                modelDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
