package com.dglozano.escale.web.dto;

import java.util.Date;

public class WeightGoalDTO {

    private Long id;
    private float goalInKg;
    private Date startDate;
    private Date dueDate;
    private boolean isAccomplished;
    private boolean loseGoal;

    public WeightGoalDTO() {
    }

    public WeightGoalDTO(Long id, float goalInKg, Date startDate, Date dueDate, boolean isAccomplished, boolean isLoseGoal) {
        this.id = id;
        this.goalInKg = goalInKg;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.isAccomplished = isAccomplished;
        this.loseGoal = isLoseGoal;
    }

    public boolean isLoseGoal() {
        return loseGoal;
    }

    public void setLoseGoal(boolean loseGoal) {
        this.loseGoal = loseGoal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getGoalInKg() {
        return goalInKg;
    }

    public void setGoalInKg(float goalInKg) {
        this.goalInKg = goalInKg;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isAccomplished() {
        return isAccomplished;
    }

    public void setAccomplished(boolean accomplished) {
        isAccomplished = accomplished;
    }
}
