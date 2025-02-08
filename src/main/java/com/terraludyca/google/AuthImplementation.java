package com.terraludyca.google;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.*;
import java.util.Arrays;

public class AuthImplementation implements Auth{

    @Override
    public ByteArrayOutputStream getCredentials(String realFileId) throws IOException {
                /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
          guides on implementing OAuth2 for your application.*/
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault()
                .createScoped(Arrays.asList(DriveScopes.DRIVE_FILE));
        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(
                credentials);

        // Build a new authorized API client service.
        Drive service = new Drive.Builder(new NetHttpTransport(),
                GsonFactory.getDefaultInstance(),
                requestInitializer)
                .setApplicationName("tesserecanva")
                .build();

        try {
            OutputStream outputStream = new ByteArrayOutputStream();

            service.files().get(realFileId)
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
        FileInputStream inputStream = new FileInputStream(myFile);
        return null;
    }

    public static void main(String ... args) throws IOException {
        AuthImplementation authImplementation = new AuthImplementation();
        authImplementation.getCredentials("1aMqEgrDVsrDaCXhgAQAwZOJKxXSXv3KY4Stdzban4h8");
    }

}
