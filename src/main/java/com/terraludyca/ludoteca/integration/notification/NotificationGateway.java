package com.terraludyca.ludoteca.integration.notification;

import com.terraludyca.ludoteca.model.Member;

/**
 * Port for sending PDF notifications to members.
 */
public interface NotificationGateway {

    /**
     * Sends the PDF report to the given member.
     */
    void send(Member member, byte[] pdfContent);
}
