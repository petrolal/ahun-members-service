package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.rest;

import com.petrolal.ahun.ahunmembersservice.application.ports.GoogleSheetPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.MemberFromSheetDto;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GoogleSheetWebResource.class)
@ActiveProfiles("test")
class GoogleSheetWebResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GoogleSheetPort googleSheetPort;

    @Test
    void shouldReadMemberSheet() throws Exception {
        MemberFromSheetDto dto = new MemberFromSheetDto(
                LocalDateTime.now(),
                "jane@example.com",
                "Jane Doe",
                LocalDate.of(1990, 5, 15)
        );
        when(googleSheetPort.readMemberSheet()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/sheets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].member_name").value("Jane Doe"))
                .andExpect(jsonPath("$[0].email").value("jane@example.com"));
    }

    @Test
    void shouldSyncSheet() throws Exception {
        Member member = new Member(
                UUID.randomUUID(),
                "Jane Doe",
                "jane@example.com",
                LocalDate.of(1990, 5, 15),
                LocalDateTime.now()
        );
        when(googleSheetPort.syncSheet()).thenReturn(List.of(member));

        mockMvc.perform(post("/api/sheets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberName").value("Jane Doe"));
    }
}
