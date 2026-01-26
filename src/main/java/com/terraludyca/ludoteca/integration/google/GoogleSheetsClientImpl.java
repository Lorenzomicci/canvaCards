package com.terraludyca.ludoteca.integration.google;

import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.terraludyca.configs.ConfigManager;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * Default implementation that reads Google Sheets using service account credentials.
 */
@Component
public class GoogleSheetsClientImpl implements GoogleSheetsClient {

    private final ConfigManager configManager;

    /**
     * Builds the client with the configured secret path.
     */
    public GoogleSheetsClientImpl(ConfigManager configManager) {
        this.configManager = configManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<List<Object>> readRange(String spreadsheetId, String range) throws IOException {
        Sheets service = new Sheets.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                buildRequestInitializer())
                .setApplicationName("LudotecaMembers")
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response.getValues() == null ? Collections.emptyList() : response.getValues();
    }

    /**
     * Builds the HTTP initializer using the service account credentials.
     */
    private HttpRequestInitializer buildRequestInitializer() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(loadCredentials())
                .createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS_READONLY));
        return new HttpCredentialsAdapter(credentials);
    }

    /**
     * Loads the credentials stream from the configured secret file path.
     */
    private InputStream loadCredentials() throws IOException {
        String fullPath = configManager.getPathSecret() + configManager.getFileSecret();
        return new FileInputStream(fullPath);
    }
}
