package com.petrolal.ahun.ahunmembersservice.application.ports;

import com.petrolal.ahun.ahunmembersservice.domain.model.Member;

import java.util.List;

public interface MemberPort {

    List<Member> getMembers();

    List<Member> getMembersByCurrentMonth();

}
