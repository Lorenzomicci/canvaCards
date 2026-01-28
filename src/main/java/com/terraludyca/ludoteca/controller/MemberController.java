package com.terraludyca.ludoteca.controller;

import com.terraludyca.ludoteca.model.Member;
import com.terraludyca.ludoteca.service.CardService;
import com.terraludyca.ludoteca.service.EnrollmentNotificationService;
import com.terraludyca.ludoteca.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    private final CardService cardService;

    /**
     * Builds the controller with the required services.
     */
    public MemberController(MemberService memberService,
                            EnrollmentNotificationService notificationService,
                            CardService cardService) {
        this.memberService = memberService;
        this.notificationService = notificationService;
        this.cardService = cardService;
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

    /**
     * Generates and returns the PDF card for a member identified by its index.
     */
    @GetMapping("/{index}/card")
    public ResponseEntity<byte[]> downloadCard(@PathVariable("index") int index) throws IOException {
        Member member = memberService.getMemberByIndex(index);
        byte[] pdf = cardService.generateCard(member);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"tessera_" + member.getName().replaceAll(\"\\\\s+\", \"_\") + \".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
