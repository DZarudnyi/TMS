package layron.tms.util;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DropboxClientInit {
    @Bean
    public DbxClientV2 initDropboxClient(@Value("${dropbox.auth}") String authToken) {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("TMS/1.0").build();
        return new DbxClientV2(config, authToken);
    }
}
