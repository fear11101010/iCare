package com.example.arafathossain.icare;


public class Reminder {
    private String key;
    private String keyId;
    private String id;
    private String profileId;


    public Reminder(String key, String keyId, String profileId) {
        this.key = key;
        this.keyId = keyId;
        this.profileId = profileId;
    }

    public Reminder(String key, String keyId, String id, String profileId) {
        this.key = key;
        this.keyId = keyId;
        this.id = id;
        this.profileId = profileId;
    }

    public String getProfileId() {
        return profileId;
    }

    public String getKey() {
        return key;
    }

    public String getKeyId() {
        return keyId;
    }

    public String getId() {
        return id;
    }
}
