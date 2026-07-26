package com.petrolal.ahun.members.application.ports;

import com.petrolal.ahun.members.domain.model.Member;

import java.util.List;

public interface MemberRepositoryPort {

    List<Member> findall();

    List<Member> findByMonth(int monthValue);

    List<Member> findByMonthAndDay(int month, int day);

    void deleteAll();
    List<Member> saveAll(List<Member> members);

}
