package com.petrolal.ahun.ahunmembersservice.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record MemberFromSheetDto(
        LocalDateTime createdAt,
        String email,
        String member_name,
        LocalDate birthday
) {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy H:mm:ss");

    public static MemberFromSheetDto fromRow(java.util.List<Object> row) {
        return new MemberFromSheetDto(
                LocalDateTime.parse(row.get(0).toString(), TIMESTAMP_FORMATTER),
                row.size() > 1 ? row.get(1).toString() : null,
                row.get(2).toString(),
                LocalDate.parse(row.get(3).toString(), DATE_FORMATTER)
        );
    }
}