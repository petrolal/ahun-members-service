package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.in.webresource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrolal.ahun.ahunmembersservice.application.ports.TelegramPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.SendMessageDto;
import com.petrolal.ahun.ahunmembersservice.domain.dto.TelegramResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TelegramWebResource.class)
@ActiveProfiles("test")
class TelegramWebResourceIT {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private TelegramPort telegramPort;

    @Test
    void shouldSendDailyMessage() throws Exception {
        SendMessageDto dto = new SendMessageDto(true);
        TelegramResponseDto responseDto = new TelegramResponseDto(true, null);

        when(telegramPort.sendDailyMessage()).thenReturn(responseDto);

        mockMvc.perform(post("/api/messaging/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true));
    }

    @Test
    void shouldSendMonthlyMessage() throws Exception {
        SendMessageDto dto = new SendMessageDto(false);
        TelegramResponseDto responseDto = new TelegramResponseDto(true, null);

        when(telegramPort.sendMonthlyMessage()).thenReturn(responseDto);

        mockMvc.perform(post("/api/messaging/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok").value(true));
    }
}
