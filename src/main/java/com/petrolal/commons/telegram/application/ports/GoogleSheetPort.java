package com.petrolal.commons.telegram.application.ports;

import com.petrolal.commons.telegram.domain.model.Member;
import com.petrolal.commons.telegram.domain.dto.MemberFromSheetDto;

import java.util.List;

public interface GoogleSheetPort {

    List<MemberFromSheetDto> readMemberSheet();

    List<Member> syncSheet();

}
