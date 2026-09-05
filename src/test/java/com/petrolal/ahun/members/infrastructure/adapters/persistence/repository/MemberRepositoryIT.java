package com.petrolal.ahun.members.infrastructure.adapters.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.petrolal.ahun.members.domain.model.Member;
import com.petrolal.ahun.members.infrastructure.adapters.persistence.entity.MemberEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryIT {

  @Autowired private MemberSpringRepository springRepository;

  private MemberRepository memberRepository;

  @BeforeEach
  void setUp() {
    memberRepository = new MemberRepository(springRepository);
  }

  @Test
  void shouldFindAllMembers() {
    MemberEntity entity =
        new MemberEntity(
            "john@example.com", "John Doe", LocalDate.of(1990, 6, 15), LocalDateTime.now());
    springRepository.save(entity);

    List<Member> members = memberRepository.findall();

    assertThat(members).hasSize(1);
    assertThat(members.getFirst().getMemberName()).isEqualTo("John Doe");
  }

  @Test
  void shouldDeleteAll() {
    MemberEntity entity =
        new MemberEntity(
            "john@example.com", "John Doe", LocalDate.of(1990, 6, 15), LocalDateTime.now());
    springRepository.save(entity);

    memberRepository.deleteAll();

    assertThat(springRepository.findAll()).isEmpty();
  }

  @Test
  void shouldSaveAll() {
    Member member =
        new Member(
            null, "John Doe", "john@example.com", LocalDate.of(1990, 6, 15), LocalDateTime.now());

    memberRepository.saveAll(List.of(member));

    assertThat(springRepository.findAll()).hasSize(1);
  }
}
