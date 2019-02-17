package com.dglozano.escale.web.dto;

import com.dglozano.escale.db.entity.Patient;

import java.util.Date;

public class PatientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Patient.Gender gender;
    private int scaleUserPin;
    private int scaleUserIndex;
    private int heightInCm;
    private int physicalActivity;
    private Date birthday;
    private boolean changedDefaultPassword;
    private int doctorId;

    public PatientDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Patient.Gender getGender() {
        return gender;
    }

    public void setGender(Patient.Gender gender) {
        this.gender = gender;
    }

    public int getScaleUserPin() {
        return scaleUserPin;
    }

    public void setScaleUserPin(int scaleUserPin) {
        this.scaleUserPin = scaleUserPin;
    }

    public int getScaleUserIndex() {
        return scaleUserIndex;
    }

    public void setScaleUserIndex(int scaleUserIndex) {
        this.scaleUserIndex = scaleUserIndex;
    }

    public int getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(int heightInCm) {
        this.heightInCm = heightInCm;
    }

    public int getPhysicalActivity() {
        return physicalActivity;
    }

    public void setPhysicalActivity(int physicalActivity) {
        this.physicalActivity = physicalActivity;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean hasChangedDefaultPassword() {
        return changedDefaultPassword;
    }

    public void setChangedDefaultPassword(boolean changedDefaultPassword) {
        this.changedDefaultPassword = changedDefaultPassword;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public String toString() {
        return String.format("{\n " +
                "   id: %s \n" +
                "   firstName: %s \n" +
                "   lastName: %s \n" +
                "   email: %s \n" +
                "   userindex: %s \n" +
                "   gender: %s \n" +
                "   changedpass: %s \n" +
                "   height: %s \n" +
                "   physicalactivity: %s \n" +
                "}", id, firstName, lastName, email,scaleUserIndex,gender,changedDefaultPassword, heightInCm, physicalActivity);
    }
}