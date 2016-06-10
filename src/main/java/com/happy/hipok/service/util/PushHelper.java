package com.happy.hipok.service.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gcm.server.Message;
import com.happy.hipok.web.rest.app.dto.AppPushMessageDTO;
import javapns.notification.PushNotificationBigPayload;
import javapns.notification.PushNotificationPayload;
import org.json.JSONException;

/**
 * Created by Dahwoud on 18/05/2016.
 */
public class PushHelper {

    /**
     *
     * @param dto
     * @return
     */
    public static Message buildAndroidMessage(AppPushMessageDTO dto) throws JsonProcessingException{

        ObjectMapper jsonMapper = new ObjectMapper();

        Message message = new Message.Builder()
            .timeToLive(5)
            .addData("data",jsonMapper.writeValueAsString(dto)).build();
        return message;
    }

    /**
     *
     * @param dto
     * @return
     * @throws JSONException
     * @throws JsonProcessingException
     */
    public static PushNotificationPayload buildIOSPayload(AppPushMessageDTO dto) throws JSONException, JsonProcessingException{

        ObjectMapper jsonMapper = new ObjectMapper();

        PushNotificationPayload payload = PushNotificationBigPayload.complex();

        payload.addAlert(dto.getAlert());
        payload.addBadge(dto.getBadge().intValue());
        payload.addSound(dto.getSound());
        payload.addCustomDictionary("payload",jsonMapper.writeValueAsString(dto));

        return payload;
    }
}
