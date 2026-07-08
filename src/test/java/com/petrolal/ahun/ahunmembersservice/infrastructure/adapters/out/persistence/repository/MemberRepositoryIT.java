package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.repository;

import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.entity.MemberEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryIT {

    @Autowired
    private MemberSpringRepository springRepository;

    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository = new MemberRepository(springRepository);
    }

    @Test
    void shouldFindAllMembers() {
        MemberEntity entity = new MemberEntity("john@example.com", "John Doe", LocalDate.of(1990, 6, 15), LocalDateTime.now());
        springRepository.save(entity);

        List<Member> members = memberRepository.findall();

        assertThat(members).hasSize(1);
        assertThat(members.getFirst().getMemberName()).isEqualTo("John Doe");
    }

    @Test
    void shouldDeleteAll() {
        MemberEntity entity = new MemberEntity("john@example.com", "John Doe", LocalDate.of(1990, 6, 15), LocalDateTime.now());
        springRepository.save(entity);

        memberRepository.deleteAll();

        assertThat(springRepository.findAll()).isEmpty();
    }

    @Test
    void shouldSaveAll() {
        Member member = new Member(null, "John Doe", "john@example.com", LocalDate.of(1990, 6, 15), LocalDateTime.now());

        memberRepository.saveAll(List.of(member));

        assertThat(springRepository.findAll()).hasSize(1);
    }
}
