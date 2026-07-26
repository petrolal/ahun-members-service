package com.petrolal.commons.telegram.application.ports;

import com.petrolal.commons.telegram.domain.model.Member;

import java.util.List;

public interface MemberPort {

    List<Member> getMembers();

    List<Member> getMembersByCurrentMonth();

    List<Member> getBirthdaysByMonthAndDate();

}
