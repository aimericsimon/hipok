package com.happy.hipok.service;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
import com.happy.hipok.domain.enumeration.NotificationType;
import com.happy.hipok.service.util.PushHelper;
import com.happy.hipok.web.rest.app.dto.AppPushMessageDTO;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class PushServiceTest {

    private AppPushMessageDTO createFakeAppPushMessageDTO(){
        AppPushMessageDTO dto = new AppPushMessageDTO();
        dto.setId(2268L);
        dto.setRead(false);
        dto.setType(NotificationType.SHARE);
        dto.setReceiverProfileId(1003L);
        dto.setEmitterProfileId(1121L);
        dto.setItemId(2L);
        dto.setEmitterTitleAbbreviation("M.");
        dto.setEmitterFirstName("Jibé");
        dto.setEmitterLastName("Barth");
        dto.setPriority(AppPushMessageDTO.DefaultPriority);
        dto.setTitle(AppPushMessageDTO.DefaultTitle);
        dto.setVibrate(AppPushMessageDTO.DefaultVibrate);
        dto.setSound(AppPushMessageDTO.DefaultSound);
        dto.setAlert(dto.getEmitterFullname()+" est trop trop fort !");
        dto.setBadge(10L);

        return dto;
    }

    @Test
    public void shouldSendAndroidPush() {

        String deviceToken = "APA91bHD4zo13XGwzerc3px05dEmarZ1Qburt2ibSlZ1N4iQTjfFc6VDZaLtHGqz0FRxCWOKAs8W28Bogg5RaHALyPeCTkwlsJD5wRQFB8qnUUJHdxvR0j4C_FLuT_5yi93FsebAleka";
        String pushToken = "AIzaSyDcyg65EfCURTwu5_IOJPobikKYOGvT40s";

        try {

            Message message = PushHelper.buildAndroidMessage(createFakeAppPushMessageDTO());

            Sender sender = new Sender(pushToken);

            List<String> tokens = new ArrayList<String>();
            tokens.add(deviceToken);

            MulticastResult result = sender.sendNoRetry(message,tokens);

        } catch (IOException e) {

        }
    }

    /*
    @Test
    public void shouldSendIOSPush() {

        String deviceToken = "b64cf2952c73726257550f5639a0358de2f00ef3a7bf8fd3dfb11e7a25914c11";

        try {

            AppPushMessageDTO dto = createFakeAppPushMessageDTO();
            PushNotificationPayload payload = PushHelper.buildIOSPayload(dto);

            PushedNotifications result = Push.payload(payload, "C://Users//Dahwoud//Source//Repos//HipokServer//apn//hipok.apns-dev-cert.p12", "microclimat", false, new BasicDevice(deviceToken));
            if(result.getFailedNotifications().size() > 0){
                PushedNotification fail = result.getFailedNotifications().get(0);
                throw new UncheckedException(fail.toString(), fail.getException());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }*/
}
