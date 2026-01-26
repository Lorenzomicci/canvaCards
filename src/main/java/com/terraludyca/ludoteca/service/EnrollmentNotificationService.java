package com.terraludyca.ludoteca.service;

import com.terraludyca.ludoteca.config.LudotecaProperties;
import com.terraludyca.ludoteca.integration.notification.NotificationGateway;
import com.terraludyca.ludoteca.model.Member;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Orchestrates the automatic PDF delivery for the target year.
 */
@Service
public class EnrollmentNotificationService {

    private final MemberService memberService;
    private final ReportRenderer reportRenderer;
    private final NotificationGateway notificationGateway;
    private final LudotecaProperties properties;

    /**
     * Builds the service with required collaborators.
     */
    public EnrollmentNotificationService(MemberService memberService,
                                         ReportRenderer reportRenderer,
                                         NotificationGateway notificationGateway,
                                         LudotecaProperties properties) {
        this.memberService = memberService;
        this.reportRenderer = reportRenderer;
        this.notificationGateway = notificationGateway;
        this.properties = properties;
    }

    /**
     * Sends the BIRT report PDF to every member of the configured target year.
     */
    public NotificationSummary notifyTargetYear() throws IOException {
        int year = properties.getTargetYear();
        List<Member> members = memberService.getMembersByYear(year);
        for (Member member : members) {
            byte[] pdf = reportRenderer.renderPdf(member);
            notificationGateway.send(member, pdf);
        }
        return new NotificationSummary(year, members.size());
    }

    /**
     * Represents the result of a notification run.
     */
    public record NotificationSummary(int year, int totalNotified) {
    }
}
