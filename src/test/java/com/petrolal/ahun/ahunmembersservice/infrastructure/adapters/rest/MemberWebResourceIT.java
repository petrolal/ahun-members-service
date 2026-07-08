package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.rest;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberPort;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberWebResource.class)
@ActiveProfiles("test")
class MemberWebResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemberPort memberPort;

    @Test
    void shouldGetAllMembers() throws Exception {
        Member member = new Member(UUID.randomUUID(), "Alice", "alice@example.com", LocalDate.of(1995, 6, 15), LocalDateTime.now());
        when(memberPort.getMembers()).thenReturn(List.of(member));

        mockMvc.perform(get("/api/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberName").value("Alice"))
                .andExpect(jsonPath("$[0].email").value("alice@example.com"));
    }

    @Test
    void shouldGetCurrentMonthBirthdays() throws Exception {
        Member member = new Member(UUID.randomUUID(), "Bob", "bob@example.com", LocalDate.of(1990, 6, 28), LocalDateTime.now());
        when(memberPort.getMembersByCurrentMonth()).thenReturn(List.of(member));

        mockMvc.perform(get("/api/members/current")
                        .param("today", "false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberName").value("Bob"));
    }

    @Test
    void shouldGetTodayBirthdays() throws Exception {
        Member member = new Member(UUID.randomUUID(), "Charlie", "charlie@example.com", LocalDate.of(1990, 6, 28), LocalDateTime.now());
        when(memberPort.getBirthdaysByMonthAndDate()).thenReturn(List.of(member));

        mockMvc.perform(get("/api/members/current")
                        .param("today", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberName").value("Charlie"));
    }
}
