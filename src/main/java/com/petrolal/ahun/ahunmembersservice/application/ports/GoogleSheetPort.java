package com.petrolal.ahun.ahunmembersservice.application.ports;

import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.domain.model.MemberFromSheet;

import java.util.List;

public interface GoogleSheetPort {

    List<MemberFromSheet> readMemberSheet();

    List<Member> syncSheet();

}
