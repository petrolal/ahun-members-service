package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.repository;

import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.entity.MemberEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface MemberSpringRepository extends JpaRepository<MemberEntity, UUID> {
    List<MemberEntity> findByMonthBirthday(int monthBirthday);

    @Query("SELECT r FROM MemberEntity r WHERE MONTH(r.birthday) = :month AND DAY(r.birthday) = :day")
    List<MemberEntity> findByDayAndMonth(@Param("month") int month, @Param("day") int day);
}
