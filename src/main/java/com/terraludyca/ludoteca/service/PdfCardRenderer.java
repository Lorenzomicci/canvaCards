package com.terraludyca.ludoteca.service;

import com.terraludyca.ludoteca.model.Member;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Renders a simple PDF card using PDFBox.
 */
@Component
@Primary
public class PdfCardRenderer implements ReportRenderer {

    /**
     * {@inheritDoc}
     */
    @Override
    public byte[] renderPdf(Member member) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(new PDRectangle(320, 200));
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, 170);
                contentStream.showText("Ludoteca - Tessera Socio");
                contentStream.endText();

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, 140);
                contentStream.showText("Nome: " + safeValue(member.getName()));
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(20, 120);
                contentStream.showText("Email: " + safeValue(member.getEmail()));
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(20, 100);
                contentStream.showText("Anno iscrizione: " + member.getSubscriptionYear());
                contentStream.endText();
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        } catch (IOException ex) {
            throw new IllegalStateException("Impossibile generare la tessera PDF", ex);
        }
    }

    /**
     * Returns a safe printable value for the PDF card.
     */
    private String safeValue(String value) {
        return value == null || value.isBlank() ? "N/D" : value;
    }
}
