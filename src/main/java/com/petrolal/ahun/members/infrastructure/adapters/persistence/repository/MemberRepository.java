package com.petrolal.ahun.members.infrastructure.adapters.persistence.repository;

import com.petrolal.ahun.members.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.members.domain.model.Member;
import com.petrolal.ahun.members.infrastructure.adapters.persistence.entity.MemberEntity;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository implements MemberRepositoryPort {

  private final MemberSpringRepository repository;

  public MemberRepository(MemberSpringRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<Member> findall() {
    return repository.findAll().stream().map(MemberEntity::toDomain).toList();
  }

  @Override
  public List<Member> findByMonth(int monthValue) {
    return repository.findByMonthBirthday(monthValue).stream().map(MemberEntity::toDomain).toList();
  }

  @Override
  public void deleteAll() {
    repository.deleteAllInBatch();
  }

  @Override
  public List<Member> saveAll(List<Member> members) {
    List<MemberEntity> entities =
        members.stream()
            .map(
                m ->
                    new MemberEntity(
                        m.getEmail(), m.getMemberName(), m.getBirthday(), m.getCreatedAt()))
            .toList();

    return repository.saveAll(entities).stream().map(MemberEntity::toDomain).toList();
  }

  @Override
  public List<Member> findByMonthAndDay(int month, int day) {
    return repository.findByDayAndMonth(month, day).stream().map(MemberEntity::toDomain).toList();
  }
}
