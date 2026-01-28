package com.terraludyca.ludoteca.service;

import com.terraludyca.ludoteca.model.Member;

/**
 * Strategy for rendering the BIRT report for a member.
 */
public interface ReportRenderer {

    /**
     * Renders a PDF report for the given member.
     */
    byte[] renderPdf(Member member);
}
