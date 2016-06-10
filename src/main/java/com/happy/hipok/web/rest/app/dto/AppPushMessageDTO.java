package com.happy.hipok.web.rest.app.dto;

import com.happy.hipok.domain.enumeration.NotificationType;

/**
 * Created by Dahwoud on 18/05/2016.
 */
public class AppPushMessageDTO {

    /**
     *
     */
    public final static int DefaultPriority = 2;

    /**
     *
     */
    public final static boolean DefaultVibrate = true;

    /**
     *
     */
    public final static String DefaultTitle = "Hipok";

    /**
     *
     */
    public final static String DefaultSound = "default";

    private Long id;

    private Boolean read;

    private NotificationType type;

    private Long receiverProfileId;

    private Long emitterProfileId;

    private Long itemId;

    private String emitterTitleAbbreviation;

    private String emitterFirstName;

    private String emitterLastName;

    private String alert;

    private String title;

    private String sound;

    private int priority;

    private boolean vibrate;

    private Long badge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Long getReceiverProfileId() {
        return receiverProfileId;
    }

    public void setReceiverProfileId(Long profileId) {
        this.receiverProfileId = profileId;
    }
    public Long getEmitterProfileId() {
        return emitterProfileId;
    }

    public void setEmitterProfileId(Long profileId) {
        this.emitterProfileId = profileId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getEmitterTitleAbbreviation() {
        return emitterTitleAbbreviation;
    }

    public void setEmitterTitleAbbreviation(String emitterTitleAbbreviation) {
        this.emitterTitleAbbreviation = emitterTitleAbbreviation;
    }

    public String getEmitterFirstName() {
        return emitterFirstName;
    }

    public void setEmitterFirstName(String emitterFirstName) {
        this.emitterFirstName = emitterFirstName;
    }

    public String getEmitterLastName() {
        return emitterLastName;
    }

    public void setEmitterLastName(String emitterLastName) {
        this.emitterLastName = emitterLastName;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public String getEmitterFullname(){
        return this.getEmitterTitleAbbreviation()+" "+this.getEmitterFirstName()+" "+this.getEmitterLastName();
    }

    public Long getBadge(){
        return this.badge;
    }

    public void setBadge(Long badge){
        this.badge = badge;
    }
}
