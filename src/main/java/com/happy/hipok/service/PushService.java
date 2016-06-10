package com.happy.hipok.service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.happy.hipok.domain.Device;
import com.happy.hipok.service.util.PushHelper;
import com.happy.hipok.web.rest.app.dto.AppPushMessageDTO;
import javapns.Push;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class PushService  {

    private final Logger log = LoggerFactory.getLogger(PushService.class);

    @Value("${ios.p12.file}")
    private String iosPushCertif;
    @Value("${ios.p12.password}")
    private String iosPushCertifPassword;
    @Value("${ios.prod}")
    private boolean iosProductionEnv;

    @Value("${android.push.key}")
    private String androidPushKey;

    /**
     *
     * @param message
     * @param devices
     */

    @Async
    public void sendPush(AppPushMessageDTO message, List<Device> devices){

        List<String> androidTokens = new ArrayList<>();
        List<String> iosTokens = new ArrayList<>();

        for(Device device: devices){
            if(device.getType().equals(Device.AndroidDevice)){
                androidTokens.add(device.getToken());
            }
            else{
                if(device.getType().equals(Device.IOSDevice)){
                    iosTokens.add((device.getToken()));
                }
            }
        }

        if(androidTokens.size()>0 || iosTokens.size()>0){

            if(androidTokens.size()>0){
                sendAndroidPush(message,androidTokens);
            }

            if(iosTokens.size()>0){
                sendIOSPush(message,iosTokens);
            }
        }
    }

    /**
     *
     * @param dto
     * @param tokens
     */
    private void sendAndroidPush(AppPushMessageDTO dto, List<String> tokens) {
        try{
            log.debug("Start sending "+dto.getType()+" push message(s) to "+tokens.size()+" device(s)");
            Message message = PushHelper.buildAndroidMessage(dto);
            Sender sender = new Sender(this.androidPushKey);
            sender.sendNoRetry(message,tokens);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     *
     * @param dto
     * @param tokens
     */
    private void sendIOSPush (AppPushMessageDTO dto, List<String> tokens) {
        try {

            log.debug("Start sending "+dto.getType()+" push message(s) to "+tokens.size()+" device(s)");
            PushNotificationPayload payload = PushHelper.buildIOSPayload(dto);

            PushedNotifications result  = Push.payload(payload,iosPushCertif,iosPushCertifPassword,iosProductionEnv,Runtime.getRuntime().availableProcessors(),tokens);

            if(result.getFailedNotifications().size() > 0){
                log.debug("Failed to send "+result.getFailedNotifications().size()+" message(s) push");
                for(PushedNotification fail: result.getFailedNotifications()){
                    log.error(fail.toString());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
