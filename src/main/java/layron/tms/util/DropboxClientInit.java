package layron.tms.util;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DropboxAuthProperties.class)
@RequiredArgsConstructor
public class DropboxClientInit {
    private final DropboxAuthProperties authProperties;

    @Bean
    public DbxClientV2 initDropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("TMS/1.0").build();
        return new DbxClientV2(config, authProperties.getToken());
    }
}
