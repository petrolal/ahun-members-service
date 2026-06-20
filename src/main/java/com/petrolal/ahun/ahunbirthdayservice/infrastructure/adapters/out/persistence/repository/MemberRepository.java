package com.petrolal.ahun.ahunbirthdayservice.infrastructure.adapters.out.persistence.repository;

import com.petrolal.ahun.ahunbirthdayservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunbirthdayservice.domain.model.Member;
import com.petrolal.ahun.ahunbirthdayservice.infrastructure.adapters.out.persistence.entity.MemberEntity;
import org.springframework.stereotype.Repository;

import java.time.Month;
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
}
