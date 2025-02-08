package com.terraludyca.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/application.properties")
public class ConfigManager {

    private AnnotationConfigApplicationContext context;

    @Value("${google.auth.folder}")
    private String pathSecret;

    @Value("${google.auth.file}")
    private String fileSecret;

    public ConfigManager() {
    }

    public String getPathSecret() {
        return pathSecret;
    }

    public void setPathSecret(String pathSecret) {
        this.pathSecret = pathSecret;
    }

    public String getFileSecret() {
        return fileSecret;
    }

    public void setFileSecret(String fileSecret) {
        this.fileSecret = fileSecret;
    }

}
