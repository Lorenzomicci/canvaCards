package com.terraludyca.google;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.terraludyca.configs.ConfigManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface Auth {

    /**
     * Load pre-authorized user credetials
     */
    ByteArrayOutputStream getCredentials(String realFileId, ConfigManager config) throws IOException;

}
