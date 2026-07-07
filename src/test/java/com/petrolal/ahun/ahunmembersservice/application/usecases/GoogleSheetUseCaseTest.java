package com.petrolal.ahun.ahunmembersservice.application.usecases;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunmembersservice.domain.dto.MemberFromSheetDto;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.entity.MemberEntity;
import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.externalapis.GoogleSheetsAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GoogleSheetUseCaseTest {

    @Mock
    private GoogleSheetsAdapter googleSheetsAdapter;

    @Mock
    private MemberRepositoryPort memberRepositoryPort;

    @Mock
    private Sheets sheetsService;

    @Mock
    private Sheets.Spreadsheets spreadsheets;

    @Mock
    private Sheets.Spreadsheets.Values values;

    @Mock
    private Sheets.Spreadsheets.Values.Get get;

    private GoogleSheetUseCase googleSheetUseCase;

    @BeforeEach
    void setUp() {
        googleSheetUseCase = new GoogleSheetUseCase(googleSheetsAdapter, memberRepositoryPort);
    }

    @Test
    void shouldReadMemberSheetSuccessfully() throws GeneralSecurityException, IOException {
        ValueRange valueRange = new ValueRange();
        List<List<Object>> valuesList = new ArrayList<>();
        valuesList.add(List.of("Timestamp", "Email", "Name", "Birthday"));
        valuesList.add(List.of("6/28/2026 14:30:15", "jane@example.com", "Jane Doe", "5/15/1990"));
        valueRange.setValues(valuesList);

        when(googleSheetsAdapter.getSheetsService()).thenReturn(sheetsService);
        when(sheetsService.spreadsheets()).thenReturn(spreadsheets);
        when(spreadsheets.values()).thenReturn(values);
        when(values.get(anyString(), anyString())).thenReturn(get);
        when(get.execute()).thenReturn(valueRange);

        List<MemberFromSheetDto> result = googleSheetUseCase.readMemberSheet();

        assertThat(result).hasSize(1);
        MemberFromSheetDto dto = result.getFirst();
        assertThat(dto.member_name()).isEqualTo("Jane Doe");
        assertThat(dto.email()).isEqualTo("jane@example.com");
        assertThat(dto.birthday()).isEqualTo(LocalDate.of(1990, 5, 15));
    }

    @Test
    void shouldThrowRuntimeExceptionWhenReadThrowsException() throws GeneralSecurityException, IOException {
        when(googleSheetsAdapter.getSheetsService()).thenThrow(new IOException("Connection failed"));

        assertThatThrownBy(() -> googleSheetUseCase.readMemberSheet())
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(IOException.class);
    }

    @Test
    void shouldSyncSheetSuccessfully() throws GeneralSecurityException, IOException {
        ValueRange valueRange = new ValueRange();
        List<List<Object>> valuesList = new ArrayList<>();
        valuesList.add(List.of("Timestamp", "Email", "Name", "Birthday"));
        valuesList.add(List.of("6/28/2026 14:30:15", "jane@example.com", "Jane Doe", "5/15/1990"));
        valueRange.setValues(valuesList);

        when(googleSheetsAdapter.getSheetsService()).thenReturn(sheetsService);
        when(sheetsService.spreadsheets()).thenReturn(spreadsheets);
        when(spreadsheets.values()).thenReturn(values);
        when(values.get(anyString(), anyString())).thenReturn(get);
        when(get.execute()).thenReturn(valueRange);

        MemberEntity savedEntity = new MemberEntity("jane@example.com", "Jane Doe", LocalDate.of(1990, 5, 15), LocalDateTime.of(2026, 6, 28, 14, 30, 15));
        savedEntity.setUuid(UUID.randomUUID());

        when(memberRepositoryPort.saveAll(anyList())).thenReturn(List.of(savedEntity));

        List<Member> syncedMembers = googleSheetUseCase.syncSheet();

        assertThat(syncedMembers).hasSize(1);
        assertThat(syncedMembers.getFirst().getMemberName()).isEqualTo("Jane Doe");

        verify(memberRepositoryPort).deleteAll();
        verify(memberRepositoryPort).saveAll(anyList());
    }
}
