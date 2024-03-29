package project.shimozukuri.pastebin.configs;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("https://192.168.0.147:9000")
                .credentials("xN6cvCgfzDNyv0QgIGbk", "P87pe41ciWWXsqU5K0nfTwOrZlOnNsjFpOIRcByK")
                .build();
    }
}
