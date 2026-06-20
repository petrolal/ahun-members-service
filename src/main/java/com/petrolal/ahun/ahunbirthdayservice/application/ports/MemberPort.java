package com.petrolal.ahun.ahunbirthdayservice.application.ports;

import com.petrolal.ahun.ahunbirthdayservice.domain.model.Member;

import java.util.List;

public interface MemberPort {

    List<Member> getMembers();

    List<Member> getMembersByCurrentMonth();

}
