package com.terraludyca.ludoteca.service;

import com.terraludyca.ludoteca.model.Member;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Placeholder renderer that simulates a BIRT report PDF.
 */
@Component
public class BirtReportRenderer implements ReportRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] renderPdf(Member member) {
        String content = "%%PDF-1.4\n" +
                "1 0 obj<<>>endobj\n" +
                "2 0 obj<<>>endobj\n" +
                "3 0 obj<< /Type /Page /Parent 2 0 R /Resources <<>> /MediaBox [0 0 200 200] /Contents 4 0 R>>endobj\n" +
                "4 0 obj<< /Length 44 >>stream\n" +
                "BT /F1 12 Tf 10 100 Td (Report per " + member.getName() + ") Tj ET\n" +
                "endstream endobj\n" +
                "xref\n0 5\n0000000000 65535 f \n" +
                "trailer<< /Root 1 0 R /Size 5 >>\nstartxref\n0\n%%EOF";
        return content.getBytes(StandardCharsets.UTF_8);
    }
}
