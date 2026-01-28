package com.terraludyca.ludoteca.service;

import com.terraludyca.ludoteca.model.Member;
import org.springframework.stereotype.Service;

/**
 * Service responsible for generating member cards.
 */
@Service
public class CardService {

    private final ReportRenderer reportRenderer;

    /**
     * Builds the service with the configured report renderer.
     */
    public CardService(ReportRenderer reportRenderer) {
        this.reportRenderer = reportRenderer;
    }

    /**
     * Generates the PDF card for the specified member.
     */
    public byte[] generateCard(Member member) {
        return reportRenderer.renderPdf(member);
    }
}
