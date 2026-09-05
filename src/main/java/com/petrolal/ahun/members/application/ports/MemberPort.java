package com.petrolal.ahun.members.application.ports;

import com.petrolal.ahun.members.domain.model.Member;
import java.util.List;

public interface MemberPort {

  List<Member> getMembers();

  List<Member> getMembersByCurrentMonth();

  List<Member> getBirthdaysByMonthAndDate();
}
