package com.petrolal.ahun.ahunmembersservice.domain.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    void shouldCreateMemberWhenValidData() {
        UUID uuid = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDate birthday = LocalDate.of(1990, 5, 15);

        Member member = new Member(uuid, "John Doe", "john@example.com", birthday, createdAt);

        assertThat(member.getUuid()).isEqualTo(uuid);
        assertThat(member.getMemberName()).isEqualTo("John Doe");
        assertThat(member.getEmail()).isEqualTo("john@example.com");
        assertThat(member.getBirthday()).isEqualTo(birthday);
        assertThat(member.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void shouldThrowExceptionWhenMemberNameIsEmpty() {
        UUID uuid = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDate birthday = LocalDate.of(1990, 5, 15);

        assertThatThrownBy(() -> new Member(uuid, "", "john@example.com", birthday, createdAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("memberName cannot be empty.");
    }

    @Test
    void shouldThrowExceptionWhenMemberNameIsNull() {
        UUID uuid = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDate birthday = LocalDate.of(1990, 5, 15);

        assertThatThrownBy(() -> new Member(uuid, null, "john@example.com", birthday, createdAt))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("memberName cannot be empty.");
    }
}
