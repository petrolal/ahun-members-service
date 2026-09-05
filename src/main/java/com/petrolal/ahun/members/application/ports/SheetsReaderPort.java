package com.petrolal.ahun.members.application.ports;

import com.petrolal.ahun.members.domain.dto.MemberFromSheetDto;
import java.util.List;

public interface SheetsReaderPort {
  List<MemberFromSheetDto> readMemberSheet();
}
