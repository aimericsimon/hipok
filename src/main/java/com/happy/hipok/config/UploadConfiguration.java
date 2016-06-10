package com.happy.hipok.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@Component
public class UploadConfiguration extends WebMvcConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(UploadConfiguration.class);

    @Value("${upload.directory}")
    private String uploadDirectory;

    @Value("${ios.p12.file}")
    private String iosPushCertif;
    @Value("${ios.p12.password}")
    private String iosPushCertifPassword;
    @Value("${ios.prod}")
    private boolean iosProductionEnv;

    @Value("${android.push.key}")
    private String androidPushKey;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        Path folder = Paths.get(uploadDirectory);
        if (!Files.exists(folder)) {
            log.error(uploadDirectory+" doesn't exist !");
        }

        log.info("Configuring Upload to "+uploadDirectory);

        log.info("Android push token "+androidPushKey);

        log.info("IOS push certificat "+iosPushCertif);

        String path = "file:///"+uploadDirectory;

        registry.addResourceHandler("/upload/**")
            .addResourceLocations(path).setCachePeriod(0);

        super.addResourceHandlers(registry);
    }
}
