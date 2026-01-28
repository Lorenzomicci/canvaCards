package com.terraludyca.ludoteca.repository;

import com.terraludyca.ludoteca.config.LudotecaProperties;
import com.terraludyca.ludoteca.integration.google.GoogleSheetsClient;
import com.terraludyca.ludoteca.model.Member;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Repository that maps Google Sheets rows into {@link Member} instances.
 */
@Repository
public class GoogleSheetMemberRepository implements MemberRepository {

    private final GoogleSheetsClient googleSheetsClient;
    private final LudotecaProperties properties;

    /**
     * Builds the repository with the Google Sheets client and configuration.
     */
    public GoogleSheetMemberRepository(GoogleSheetsClient googleSheetsClient, LudotecaProperties properties) {
        this.googleSheetsClient = googleSheetsClient;
        this.properties = properties;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Member> findAll() throws IOException {
        List<List<Object>> rows = googleSheetsClient.readRange(properties.getSheetId(), properties.getSheetRange());
        if (rows.isEmpty()) {
            return List.of();
        }
        SheetIndexMapping mapping = resolveMapping(rows.get(0));
        List<Member> members = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) {
            Member member = toMember(rows.get(i), mapping);
            if (member != null) {
                members.add(member);
            }
        }
        return members;
    }

    /**
     * Builds a mapping between column names and indices to keep the importer flexible.
     */
    private SheetIndexMapping resolveMapping(List<Object> headerRow) {
        SheetIndexMapping mapping = new SheetIndexMapping();
        for (int i = 0; i < headerRow.size(); i++) {
            String header = headerRow.get(i).toString().trim().toLowerCase(Locale.ROOT);
            if (header.contains("nome")) {
                mapping.nameIndex = i;
            }
            if (header.contains("mail") || header.contains("email")) {
                mapping.emailIndex = i;
            }
            if (header.contains("anno")) {
                mapping.yearIndex = i;
            }
        }
        return mapping;
    }

    /**
     * Converts a sheet row into a member instance.
     */
    private Member toMember(List<Object> row, SheetIndexMapping mapping) {
        String name = getValue(row, mapping.nameIndex);
        String email = getValue(row, mapping.emailIndex);
        String yearValue = getValue(row, mapping.yearIndex);
        if (name == null || name.isBlank()) {
            return null;
        }
        int year = parseYear(yearValue);
        return new Member(name, email, year);
    }

    /**
     * Returns the cell value if present or null.
     */
    private String getValue(List<Object> row, int index) {
        if (index < 0 || index >= row.size()) {
            return null;
        }
        return row.get(index) == null ? null : row.get(index).toString();
    }

    /**
     * Parses the year from the provided value, returning 0 if missing.
     */
    private int parseYear(String yearValue) {
        if (yearValue == null || yearValue.isBlank()) {
            return 0;
        }
        try {
            return Integer.parseInt(yearValue.trim());
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    /**
     * Holder for column indices.
     */
    private static class SheetIndexMapping {

        private int nameIndex = 0;
        private int emailIndex = 1;
        private int yearIndex = 2;
    }
}
