package com.petrolal.ahun.members.infrastructure.adapters.persistence.repository;

import com.petrolal.ahun.members.infrastructure.adapters.persistence.entity.MemberEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberSpringRepository extends JpaRepository<MemberEntity, UUID> {
  List<MemberEntity> findByMonthBirthday(int monthBirthday);

  @Query("SELECT r FROM MemberEntity r WHERE MONTH(r.birthday) = :month AND DAY(r.birthday) = :day")
  List<MemberEntity> findByDayAndMonth(@Param("month") int month, @Param("day") int day);
}
