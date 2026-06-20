package com.petrolal.ahun.ahunmembersservice.infrastructure.adapters.in.rest;

import com.petrolal.ahun.ahunmembersservice.application.ports.MemberPort;
import com.petrolal.ahun.ahunmembersservice.domain.model.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberPort memberPort;

    public MemberController(MemberPort memberPort) {
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
