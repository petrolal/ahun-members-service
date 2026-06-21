package com.petrolal.ahun.ahunmembersservice.application.ports;

import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.domain.dto.MemberFromSheetDto;

import java.util.List;

public interface GoogleSheetPort {

    List<MemberFromSheetDto> readMemberSheet();

    List<Member> syncSheet();

}
