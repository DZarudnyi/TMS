package layron.tms.util;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//@Configuration
@Component
@RequiredArgsConstructor
@PropertySource("classpath:dropbox.properties")
@ConfigurationProperties(prefix = "dropbox.auth")
public class DropboxClientInit {
    @Value("${dropbox.auth.token}")
    private final String token = "";
    @Value("${dropbox.auth.app.key}")
    private final String appKey = "";

    //@Bean
    public DbxClientV2 initDropboxClient() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("TMS/1.0").build();
        return new DbxClientV2(config, token);
    }

//    @Bean
//    public String authorizeDropboxUser() {
//        String url = "https://api.dropboxapi.com/2/users/get_current_account";
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.add("Authorization", "Bearer " + token);
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        HttpEntity<String> response = restTemplate.exchange(
//                builder.toUriString(),
//                HttpMethod.POST,
//                entity,
//                String.class
//        );
//
//        return response.toString();
//    }
}
