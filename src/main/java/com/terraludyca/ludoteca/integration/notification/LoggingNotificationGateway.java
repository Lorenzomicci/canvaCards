package com.terraludyca.ludoteca.integration.notification;

import com.terraludyca.ludoteca.config.LudotecaProperties;
import com.terraludyca.ludoteca.model.Member;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Notification gateway that stores the PDFs locally and logs the action.
 */
@Component
public class LoggingNotificationGateway implements NotificationGateway {

    private final LudotecaProperties properties;

    /**
     * Builds the gateway with configuration properties.
     */
    public LoggingNotificationGateway(LudotecaProperties properties) {
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Member member, byte[] pdfContent) {
        try {
            Path outputDir = Path.of(properties.getReportOutputDir());
            Files.createDirectories(outputDir);
            String safeName = member.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path outputFile = outputDir.resolve("report_" + safeName + "_" + timestamp + ".pdf");
            Files.write(outputFile, pdfContent);
            System.out.println("PDF salvato per " + member.getName() + ": " + outputFile);
        } catch (IOException ex) {
            throw new IllegalStateException("Impossibile salvare il PDF per " + member.getName(), ex);
        }
    }
}
