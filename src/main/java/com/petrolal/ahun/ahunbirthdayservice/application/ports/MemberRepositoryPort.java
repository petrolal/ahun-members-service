package com.petrolal.ahun.ahunbirthdayservice.application.ports;

import com.petrolal.ahun.ahunbirthdayservice.domain.model.Member;

import java.time.Month;
import java.util.List;

public interface MemberRepositoryPort {

    List<Member> findall();

    List<Member> findByMonth(int monthValue);

}
