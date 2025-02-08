package com.terraludyca.google;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.terraludyca.configs.ConfigManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class AuthImplementation implements Auth{

    @Override
    public ByteArrayOutputStream getCredentials(String realFileId,ConfigManager config) throws IOException {
                /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
          guides on implementing OAuth2 for your application.*/
        GoogleCredentials credentials = GoogleCredentials.fromStream(Objects.requireNonNull(getCredFromJsonFile(config.getPathSecret() + config.getFileSecret())))
                .createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("ClientTessereCred")
                .build();

        try {
            OutputStream outputStream = new ByteArrayOutputStream();

            service.files().get(realFileId)
                    .setSupportsAllDrives(true)
                    .executeMediaAndDownloadTo(outputStream);

            return (ByteArrayOutputStream) outputStream;
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to move file: " + e.getDetails());
            throw e;
        }
    }

    private InputStream getCredFromJsonFile(String path) throws FileNotFoundException {
        File myFile = new File(path);
        return new FileInputStream(myFile);
    }

    public static void main(String ... args) throws IOException {
        // Avvia il contesto Spring
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigManager.class);

        // Ottieni il bean ConfigManager
        ConfigManager configs = context.getBean(ConfigManager.class);

        System.out.println("Path Secret: " + configs.getPathSecret());
        System.out.println("File Secret: " + configs.getFileSecret());

        // Usa i valori nel metodo
        AuthImplementation authImplementation = new AuthImplementation();
        // Chiudi il contesto Spring
        context.close();
        authImplementation.getCredentials("1aMqEgrDVsrDaCXhgAQAwZOJKxXSXv3KY4Stdzban4h8",configs);
    }

}
