package com.petrolal.ahun.ahunmembersservice.application.ports;

import com.petrolal.ahun.ahunmembersservice.domain.dto.MemberFromSheetDto;
import java.util.List;

public interface SheetsReaderPort {
    List<MemberFromSheetDto> readMemberSheet();
}
