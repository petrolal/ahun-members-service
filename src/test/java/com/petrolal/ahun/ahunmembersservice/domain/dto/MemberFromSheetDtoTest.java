package com.petrolal.ahun.ahunmembersservice.domain.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemberFromSheetDtoTest {

    @Test
    void shouldParseFromRowWithAllFields() {
        List<Object> row = List.of("6/28/2026 14:30:15", "jane@example.com", "Jane Doe", "5/15/1990");

        MemberFromSheetDto dto = MemberFromSheetDto.fromRow(row);

        assertThat(dto.createdAt()).isEqualTo(LocalDateTime.of(2026, 6, 28, 14, 30, 15));
        assertThat(dto.email()).isEqualTo("jane@example.com");
        assertThat(dto.member_name()).isEqualTo("Jane Doe");
        assertThat(dto.birthday()).isEqualTo(LocalDate.of(1990, 5, 15));
    }

    @Test
    void shouldParseFromRowWithEmptyEmail() {
        List<Object> row = List.of("6/28/2026 14:30:15", "", "Jane Doe", "5/15/1990");

        MemberFromSheetDto dto = MemberFromSheetDto.fromRow(row);

        assertThat(dto.createdAt()).isEqualTo(LocalDateTime.of(2026, 6, 28, 14, 30, 15));
        assertThat(dto.email()).isEmpty();
        assertThat(dto.member_name()).isEqualTo("Jane Doe");
        assertThat(dto.birthday()).isEqualTo(LocalDate.of(1990, 5, 15));
    }
}
