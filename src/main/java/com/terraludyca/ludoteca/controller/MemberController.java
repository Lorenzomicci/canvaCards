package com.terraludyca.ludoteca.controller;

import com.terraludyca.ludoteca.model.Member;
import com.terraludyca.ludoteca.service.EnrollmentNotificationService;
import com.terraludyca.ludoteca.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * REST endpoints for managing ludoteca members.
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final EnrollmentNotificationService notificationService;

    /**
     * Builds the controller with the required services.
     */
    public MemberController(MemberService memberService, EnrollmentNotificationService notificationService) {
        this.memberService = memberService;
        this.notificationService = notificationService;
    }

    /**
     * Returns all members or filters them by year.
     */
    @GetMapping
    public List<Member> listMembers(@RequestParam(value = "year", required = false) Integer year) throws IOException {
        if (year == null) {
            return memberService.getMembers();
        }
        return memberService.getMembersByYear(year);
    }

    /**
     * Triggers the automatic PDF delivery for the target year.
     */
    @PostMapping("/notify-2026")
    public EnrollmentNotificationService.NotificationSummary notifyYear() throws IOException {
        return notificationService.notifyTargetYear();
    }
}
