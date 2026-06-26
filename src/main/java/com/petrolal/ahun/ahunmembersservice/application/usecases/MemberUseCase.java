package com.petrolal.ahun.ahunmembersservice.application.usecases;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;

import java.time.LocalDate;
import java.util.List;

public class MemberUseCase implements MemberPort {

    private final MemberRepositoryPort memberRepositoryPort;

    public MemberUseCase(MemberRepositoryPort memberRepositoryPort) {
        this.memberRepositoryPort = memberRepositoryPort;
    }

    @Override
    public List<Member> getMembers() {
        return memberRepositoryPort.findall();
    }

    @Override
    public List<Member> getMembersByCurrentMonth() {
        int monthValue = LocalDate.now().getMonthValue();
        return memberRepositoryPort.findByMonth(monthValue);
    }

    @Override
    public List<Member> getBirthdaysByMonthAndDate() {
        int dayValue = LocalDate.now().getDayOfMonth();
        int  monthValue = LocalDate.now().getMonthValue();
        return memberRepositoryPort.findByMonthAndDay(monthValue, dayValue);
    }
}
