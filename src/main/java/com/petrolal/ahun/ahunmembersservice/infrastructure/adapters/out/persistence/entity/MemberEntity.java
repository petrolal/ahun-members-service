package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.entity;

import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "members")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(name = "member_name", nullable = false, length = 50)
    private String memberName;
    private String email;
    private LocalDate birthday;
    private LocalDateTime createdAt;

    @Column(name = "month_birthday", insertable = false, updatable = false)
    private int monthBirthday;

    public MemberEntity() {
    }

    public MemberEntity(String email, String memberName, LocalDate birthday,  LocalDateTime createdAt) {
        this.email = email;
        this.memberName = memberName;
        this.birthday = birthday;
        this.createdAt = createdAt;
    }

    public static Member toDomain(MemberEntity memberEntity) {
        return new Member(
                memberEntity.getUuid(),
                memberEntity.getMemberName(),
                memberEntity.getEmail(),
                memberEntity.getBirthday(),
                memberEntity.getCreatedAt()
        );
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

}
