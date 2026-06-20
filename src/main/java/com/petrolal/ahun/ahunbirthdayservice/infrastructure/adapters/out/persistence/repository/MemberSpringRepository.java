package com.petrolal.ahun.ahunbirthdayservice.infrastructure.adapters.out.persistence.repository;

import com.petrolal.ahun.ahunbirthdayservice.infrastructure.adapters.out.persistence.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MemberSpringRepository extends JpaRepository<MemberEntity, UUID> {
    List<MemberEntity> findByMonthBirthday(int monthBirthday);
}
