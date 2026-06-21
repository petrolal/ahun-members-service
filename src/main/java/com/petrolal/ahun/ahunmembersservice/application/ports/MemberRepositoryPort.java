package com.petrolal.ahun.ahunmembersservice.application.ports;

import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.entity.MemberEntity;

import java.util.List;

public interface MemberRepositoryPort {

    List<Member> findall();

    List<Member> findByMonth(int monthValue);

    void deleteAll();
    List<MemberEntity> saveAll(List<MemberEntity> members);

}
