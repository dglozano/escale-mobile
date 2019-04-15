package com.dglozano.escale.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Doctor.class,
        parentColumns = "id",
        childColumns = "doctorId",
        onDelete = CASCADE))
public class PatientInfo {

    @PrimaryKey
    private Long patientId;
    private String fullName;
    private int alerts;
    private int messages;
    private Float lastWeight;
    private Long doctorId;

    public PatientInfo() {
    }

    @Ignore
    public PatientInfo(Long patientId,
                       String fullName,
                       int alerts,
                       int messages,
                       float lastWeight,
                       Long doctorId) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.alerts = alerts;
        this.messages = messages;
        this.lastWeight = lastWeight;
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAlerts() {
        return alerts;
    }

    public void setAlerts(int alerts) {
        this.alerts = alerts;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    public Float getLastWeight() {
        return lastWeight;
    }

    public void setLastWeight(Float lastWeight) {
        this.lastWeight = lastWeight;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}