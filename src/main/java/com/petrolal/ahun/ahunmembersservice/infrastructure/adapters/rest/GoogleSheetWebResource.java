package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.rest;

import com.petrolal.ahun.ahunmembersservice.application.ports.GoogleSheetPort;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.domain.dto.MemberFromSheetDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
