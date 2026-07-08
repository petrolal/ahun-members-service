package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.persistence.repository;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.persistence.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository implements MemberRepositoryPort {

    private final MemberSpringRepository repository;

    public MemberRepository(MemberSpringRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Member> findall() {
        return repository.findAll()
                .stream()
                .map(MemberEntity::toDomain)
                .toList();
    }

    @Override
    public List<Member> findByMonth(int monthValue) {
        return repository.findByMonthBirthday(monthValue)
                .stream()
                .map(MemberEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteAll() {
        repository.deleteAllInBatch();
    }

    @Override
    public List<Member> saveAll(List<Member> members) {
        List<MemberEntity> entities = members.stream()
                .map(m -> new MemberEntity(
                        m.getEmail(),
                        m.getMemberName(),
                        m.getBirthday(),
                        m.getCreatedAt()
                ))
                .toList();

        return repository.saveAll(entities)
                .stream()
                .map(MemberEntity::toDomain)
                .toList();
    }

    @Override
    public List<Member> findByMonthAndDay(int month, int day) {
        return repository.findByDayAndMonth(month, day)
                .stream()
                .map(MemberEntity::toDomain)
                .toList();
    }

}
