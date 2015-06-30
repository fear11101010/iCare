package com.example.arafathossain.icare;


public class VaccineDetail {
    private String diseaseName;
    private String vaccineName;
    private String doseNo;
    private String date;
    private String reminder;
    private String status;
    private String profileId;
    private String id;
    public static final String ALARM_KEY_VACCINE="vaccine";
    public static final String ACTION_VACCINE="vaccine";
    public String getProfileId() {
        return profileId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getDoseNo() {
        return doseNo;
    }

    public void setDoseNo(String doseNo) {
        this.doseNo = doseNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
