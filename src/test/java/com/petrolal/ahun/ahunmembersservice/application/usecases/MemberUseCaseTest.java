package com.petrolal.ahun.ahunmembersservice.application.usecases;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberUseCaseTest {

    @Mock
    private MemberRepositoryPort memberRepositoryPort;

    private MemberUseCase memberUseCase;

    @BeforeEach
    void setUp() {
        memberUseCase = new MemberUseCase(memberRepositoryPort);
    }

    @Test
    void shouldGetMembers() {
        Member member1 = new Member(UUID.randomUUID(), "Alice", "alice@example.com", LocalDate.of(1995, 2, 10), LocalDateTime.now());
        Member member2 = new Member(UUID.randomUUID(), "Bob", "bob@example.com", LocalDate.of(1990, 8, 20), LocalDateTime.now());
        when(memberRepositoryPort.findall()).thenReturn(List.of(member1, member2));

        List<Member> result = memberUseCase.getMembers();

        assertThat(result).containsExactly(member1, member2);
        verify(memberRepositoryPort).findall();
    }

    @Test
    void shouldGetMembersByCurrentMonth() {
        Member member = new Member(UUID.randomUUID(), "Alice", "alice@example.com", LocalDate.of(1995, LocalDate.now().getMonthValue(), 10), LocalDateTime.now());
        int currentMonth = LocalDate.now().getMonthValue();
        when(memberRepositoryPort.findByMonth(currentMonth)).thenReturn(List.of(member));

        List<Member> result = memberUseCase.getMembersByCurrentMonth();

        assertThat(result).containsExactly(member);
        verify(memberRepositoryPort).findByMonth(currentMonth);
    }

    @Test
    void shouldGetBirthdaysByMonthAndDate() {
        LocalDate today = LocalDate.now();
        Member member = new Member(UUID.randomUUID(), "Alice", "alice@example.com", LocalDate.of(1995, today.getMonthValue(), today.getDayOfMonth()), LocalDateTime.now());
        when(memberRepositoryPort.findByMonthAndDay(today.getMonthValue(), today.getDayOfMonth())).thenReturn(List.of(member));

        List<Member> result = memberUseCase.getBirthdaysByMonthAndDate();

        assertThat(result).containsExactly(member);
        verify(memberRepositoryPort).findByMonthAndDay(today.getMonthValue(), today.getDayOfMonth());
    }
}
