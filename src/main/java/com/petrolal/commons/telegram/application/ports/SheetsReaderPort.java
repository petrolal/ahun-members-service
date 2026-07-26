package com.petrolal.commons.telegram.application.ports;

import com.petrolal.commons.telegram.domain.dto.MemberFromSheetDto;
import java.util.List;

public interface SheetsReaderPort {
    List<MemberFromSheetDto> readMemberSheet();
}
