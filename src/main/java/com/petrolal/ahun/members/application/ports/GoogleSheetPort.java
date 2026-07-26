package com.petrolal.ahun.members.application.ports;

import com.petrolal.ahun.members.domain.model.Member;
import com.petrolal.ahun.members.domain.dto.MemberFromSheetDto;

import java.util.List;

public interface GoogleSheetPort {

    List<MemberFromSheetDto> readMemberSheet();

    List<Member> syncSheet();

}
