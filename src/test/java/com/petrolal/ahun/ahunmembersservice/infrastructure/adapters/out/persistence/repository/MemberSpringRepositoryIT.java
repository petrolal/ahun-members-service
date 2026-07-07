package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.repository;

import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.entity.MemberEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class MemberSpringRepositoryIT {

    @Autowired
    private MemberSpringRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void shouldFindByMonthBirthday() {
        UUID uuid1 = UUID.randomUUID();
        jdbcTemplate.execute(String.format(
                "INSERT INTO members (uuid, member_name, email, birthday, created_at, month_birthday) " +
                "VALUES ('%s', 'John Doe', 'john@example.com', '1990-06-15', CURRENT_TIMESTAMP, 6)", uuid1
        ));

        UUID uuid2 = UUID.randomUUID();
        jdbcTemplate.execute(String.format(
                "INSERT INTO members (uuid, member_name, email, birthday, created_at, month_birthday) " +
                "VALUES ('%s', 'Alice Smith', 'alice@example.com', '1995-07-20', CURRENT_TIMESTAMP, 7)", uuid2
        ));

        List<MemberEntity> result = repository.findByMonthBirthday(6);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getMemberName()).isEqualTo("John Doe");
    }

    @Test
    void shouldFindByDayAndMonth() {
        MemberEntity member1 = new MemberEntity("john@example.com", "John Doe", LocalDate.of(1990, 6, 15), java.time.LocalDateTime.now());
        MemberEntity member2 = new MemberEntity("alice@example.com", "Alice Smith", LocalDate.of(1995, 7, 20), java.time.LocalDateTime.now());

        repository.save(member1);
        repository.save(member2);

        List<MemberEntity> result = repository.findByDayAndMonth(6, 15);

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getMemberName()).isEqualTo("John Doe");
    }
}
