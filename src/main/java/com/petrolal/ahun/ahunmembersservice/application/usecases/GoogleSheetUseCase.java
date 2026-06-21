package com.petrolal.ahun.ahunmembersservice.application.usecases;

import com.google.api.services.sheets.v4.model.ValueRange;
import com.petrolal.ahun.ahunmembersservice.application.ports.GoogleSheetPort;
import com.petrolal.ahun.ahunmembersservice.application.ports.MemberRepositoryPort;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import com.petrolal.ahun.ahunmembersservice.domain.model.MemberFromSheet;
import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.entity.MemberEntity;
import com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.out.persistence.google.GoogleSheetsAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoogleSheetUseCase implements GoogleSheetPort {

    private final GoogleSheetsAdapter googleSheetsAdapter;
    private final MemberRepositoryPort memberRepositoryPort;

    public GoogleSheetUseCase(GoogleSheetsAdapter googleSheetsAdapter,
                              MemberRepositoryPort memberRepositoryPort) {
        this.googleSheetsAdapter = googleSheetsAdapter;
        this.memberRepositoryPort = memberRepositoryPort;
    }

    @Override
    public List<MemberFromSheet> readMemberSheet() {
        try {
            ValueRange response = googleSheetsAdapter.getSheetsService()
                    .spreadsheets()
                    .values()
                    .get("1iaqNClSz0DLH1xu7SDpe-WmpL6-aZcC_Edp3TSAPlEM", "Form Responses 1!A:D")
                    .execute();

            return response.getValues()
                    .stream()
                    .skip(1)
                    .map(MemberFromSheet::fromRow)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Member> syncSheet() {
        List<MemberFromSheet> response = this.readMemberSheet();
        List<MemberEntity> entity = new ArrayList<>();

        for (MemberFromSheet i : response) {
            entity.add(
                    new MemberEntity(
                            i.email(),
                            i.member_name(),
                            i.birthday(),
                            i.createdAt()
                    )
            );
        }

        memberRepositoryPort.deleteAll();
        return memberRepositoryPort.saveAll(entity)
                .stream()
                .map(MemberEntity::toDomain)
                .toList();
    }
}
