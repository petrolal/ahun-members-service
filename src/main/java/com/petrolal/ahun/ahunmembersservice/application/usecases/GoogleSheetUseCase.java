package com.petrolal.ahun.ahunmembersservice.application.usecases;

import com.petrolal.ahun.ahunmembersservice.application.ports.GoogleSheetPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.SheetsReaderPort;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.domain.dto.MemberFromSheetDto;

import java.util.List;

public class GoogleSheetUseCase implements GoogleSheetPort {

    private final SheetsReaderPort sheetsReaderPort;
    private final MemberRepositoryPort memberRepositoryPort;

    public GoogleSheetUseCase(SheetsReaderPort sheetsReaderPort,
                              MemberRepositoryPort memberRepositoryPort) {
        this.sheetsReaderPort = sheetsReaderPort;
        this.memberRepositoryPort = memberRepositoryPort;
    }

    @Override
    public List<MemberFromSheetDto> readMemberSheet() {
        return sheetsReaderPort.readMemberSheet();
    }

    @Override
    public List<Member> syncSheet() {
        List<MemberFromSheetDto> response = this.readMemberSheet();

        List<Member> members = response.stream()
                .map(i -> new Member(
                        null,
                        i.member_name(),
                        i.email(),
                        i.birthday(),
                        i.createdAt()
                ))
                .toList();

        memberRepositoryPort.deleteAll();
        return memberRepositoryPort.saveAll(members);
    }
}
