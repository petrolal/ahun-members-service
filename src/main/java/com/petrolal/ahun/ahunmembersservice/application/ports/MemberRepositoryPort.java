package com.petrolal.ahun.ahunmembersservice.application.ports;

import com.petrolal.ahun.ahunmembersservice.domain.model.Member;

import java.time.Month;
import java.util.List;

public interface MemberRepositoryPort {

    List<Member> findall();

    List<Member> findByMonth(int monthValue);

}
