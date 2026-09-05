package com.petrolal.ahun.members.infrastructure.adapters.rest;

import com.petrolal.ahun.members.application.ports.GoogleSheetPort;
import com.petrolal.ahun.members.domain.dto.MemberFromSheetDto;
import com.petrolal.ahun.members.domain.model.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Google Sheets")
@RestController
@RequestMapping("/api/sheets")
public class GoogleSheetWebResource {

  private final GoogleSheetPort googleSheetPort;

  public GoogleSheetWebResource(GoogleSheetPort googleSheetPort) {
    this.googleSheetPort = googleSheetPort;
  }

  @GetMapping
  public List<MemberFromSheetDto> readMemberSheet() {
    return googleSheetPort.readMemberSheet();
  }

  @PostMapping
  public List<Member> syncSheet() {
    return googleSheetPort.syncSheet();
  }
}
