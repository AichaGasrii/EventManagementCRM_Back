package com.example.gestionauth.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class OAuth2Config {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new MyClientRegistrationRepository();
    }

    private static class MyClientRegistrationRepository implements ClientRegistrationRepository {

        @Override
        public ClientRegistration findByRegistrationId(String registrationId) {
            if ("google".equals(registrationId)) {
                return googleClientRegistration();
            }
            return null;
        }

        private ClientRegistration googleClientRegistration() {
            return ClientRegistration.withRegistrationId("google")
                    .clientId("784115350991-mncvmts6s27mv6k1p1fhfdiup98583tj.apps.googleusercontent.com")
                    .clientSecret("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")
                    .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                    .tokenUri("https://accounts.google.com/o/oauth2/token")
                    .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                    .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                    .scope("openid", "profile", "email")
                    .clientName("Google")
                    .build();
        }
    }
}
