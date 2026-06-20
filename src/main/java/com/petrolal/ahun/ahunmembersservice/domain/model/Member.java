package com.petrolal.ahun.ahunmembersservice.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Member {

    private UUID uuid;
    private LocalDateTime createdAt;
    private String email;
    private String memberName;
    private LocalDate birthday;
    private int monthBirthday;

    public Member(UUID uuid, String memberName, String email, LocalDate birthday, int monthBirthday) {
        this.uuid = uuid;
        this.memberName = memberName;
        this.email = email;
        this.birthday = birthday;
        this.createdAt = LocalDateTime.now();
        this.monthBirthday = monthBirthday;
        validar();
    }

    private void validar() {
        if (memberName == null || memberName.isBlank()) {
            throw new IllegalArgumentException("memberName cannot be empty.");
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public int getMonthBirthday() {
        return monthBirthday;
    }

    public void setMonthBirthday(int monthBirthday) {
        this.monthBirthday = monthBirthday;
    }
}
