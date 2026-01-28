package com.terraludyca.ludoteca.integration.google;

import java.io.IOException;
import java.util.List;

/**
 * Client abstraction for reading data from Google Sheets.
 */
public interface GoogleSheetsClient {

    /**
     * Reads a range from a Google Sheet.
     */
    List<List<Object>> readRange(String spreadsheetId, String range) throws IOException;
}
