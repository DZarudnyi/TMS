package layron.tms.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dropbox.auth")
public class DropboxAuthProperties {
    private String token = "";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
