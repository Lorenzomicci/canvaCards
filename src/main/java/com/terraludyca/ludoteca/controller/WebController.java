package com.terraludyca.ludoteca.controller;

import com.terraludyca.ludoteca.model.Member;
import com.terraludyca.ludoteca.service.EnrollmentNotificationService;
import com.terraludyca.ludoteca.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Web controller that renders the simple UI for the ludoteca.
 */
@Controller
public class WebController {

    private final MemberService memberService;
    private final EnrollmentNotificationService notificationService;

    /**
     * Builds the controller with the required services.
     */
    public WebController(MemberService memberService, EnrollmentNotificationService notificationService) {
        this.memberService = memberService;
        this.notificationService = notificationService;
    }

    /**
     * Renders the home page with the members list.
     */
    @GetMapping("/")
    public String home(Model model) throws IOException {
        List<Member> members;
        try {
            members = memberService.getMembers();
        } catch (IOException ex) {
            model.addAttribute("errorMessage", "Impossibile caricare gli iscritti dal foglio Google.");
            members = List.of();
        }
        List<MemberView> views = new ArrayList<>();
        for (int i = 0; i < members.size(); i++) {
            views.add(new MemberView(i, members.get(i)));
        }
        model.addAttribute("members", views);
        return "members";
    }

    /**
     * Triggers the automatic PDF delivery for the target year and reloads the page.
     */
    @PostMapping("/notify")
    public String notifyTargetYear(Model model) throws IOException {
        try {
            EnrollmentNotificationService.NotificationSummary summary = notificationService.notifyTargetYear();
            model.addAttribute("notificationSummary", summary);
        } catch (IOException ex) {
            model.addAttribute("errorMessage", "Impossibile inviare le tessere automatiche.");
        }
        return home(model);
    }

    /**
     * View model used by the template to expose index and member data together.
     */
    public record MemberView(int index, Member member) {
    }
}
