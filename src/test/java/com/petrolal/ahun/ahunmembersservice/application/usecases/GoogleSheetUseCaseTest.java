package com.petrolal.ahun.ahunmembersservice.application.usecases;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.SheetsReaderPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.MemberFromSheetDto;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoogleSheetUseCaseTest {

    @Mock
    private SheetsReaderPort sheetsReaderPort;

    @Mock
    private MemberRepositoryPort memberRepositoryPort;

    private GoogleSheetUseCase googleSheetUseCase;

    @BeforeEach
    void setUp() {
        googleSheetUseCase = new GoogleSheetUseCase(sheetsReaderPort, memberRepositoryPort);
    }

    @Test
    void shouldReadMemberSheetSuccessfully() {
        MemberFromSheetDto dto = new MemberFromSheetDto(LocalDateTime.of(2026, 6, 28, 14, 30, 15), "jane@example.com", "Jane Doe", LocalDate.of(1990, 5, 15));
        when(sheetsReaderPort.readMemberSheet()).thenReturn(List.of(dto));

        List<MemberFromSheetDto> result = googleSheetUseCase.readMemberSheet();

        assertThat(result).hasSize(1);
        MemberFromSheetDto resultDto = result.getFirst();
        assertThat(resultDto.member_name()).isEqualTo("Jane Doe");
        assertThat(resultDto.email()).isEqualTo("jane@example.com");
        assertThat(resultDto.birthday()).isEqualTo(LocalDate.of(1990, 5, 15));
    }

    @Test
    void shouldPropagateExceptionWhenReadThrowsException() {
        when(sheetsReaderPort.readMemberSheet()).thenThrow(new RuntimeException("Connection failed"));

        assertThatThrownBy(() -> googleSheetUseCase.readMemberSheet())
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Connection failed");
    }

    @Test
    void shouldSyncSheetSuccessfully() {
        MemberFromSheetDto dto = new MemberFromSheetDto(LocalDateTime.of(2026, 6, 28, 14, 30, 15), "jane@example.com", "Jane Doe", LocalDate.of(1990, 5, 15));
        when(sheetsReaderPort.readMemberSheet()).thenReturn(List.of(dto));

        Member savedMember = new Member(UUID.randomUUID(), "Jane Doe", "jane@example.com", LocalDate.of(1990, 5, 15), LocalDateTime.of(2026, 6, 28, 14, 30, 15));
        when(memberRepositoryPort.saveAll(anyList())).thenReturn(List.of(savedMember));

        List<Member> syncedMembers = googleSheetUseCase.syncSheet();

        assertThat(syncedMembers).hasSize(1);
        assertThat(syncedMembers.getFirst().getMemberName()).isEqualTo("Jane Doe");

        verify(memberRepositoryPort).deleteAll();
        verify(memberRepositoryPort).saveAll(anyList());
    }
}
