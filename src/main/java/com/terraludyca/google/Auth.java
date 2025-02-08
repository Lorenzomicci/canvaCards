package com.terraludyca.google;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface Auth {

    /**
     * Load pre-authorized user credetials
     */
    ByteArrayOutputStream getCredentials(String realFileId) throws IOException;

}
