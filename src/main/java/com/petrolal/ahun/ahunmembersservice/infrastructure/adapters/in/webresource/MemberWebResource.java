package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.in.webresource;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberPort;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Members")
@RestController
@RequestMapping("/api/members")
public class MemberWebResource {

    private final MemberPort memberPort;

    public MemberWebResource(MemberPort memberPort) {
        this.memberPort = memberPort;
    }

    @GetMapping
    public List<Member> getMembers() {
        return memberPort.getMembers();
    }

    @GetMapping("current")
    public List<Member> getCurrentMonthBirthdays() {
        return memberPort.getMembersByCurrentMonth();
    }

}
