package kz.mb.project.mb_project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  protected final KeycloakConfiguration keycloakConfiguration;

  protected final String authorizationUrl;

  private final String[] IGNORE_URL = {
      "/api/users/public/**",
      "v3/**",
      "/swagger-ui/**"
  };

  @Autowired
  public WebSecurityConfig(KeycloakConfiguration keycloakConfiguration) {
    this.keycloakConfiguration = keycloakConfiguration;
    this.authorizationUrl = String.format(
        keycloakConfiguration.serverURL + keycloakConfiguration.authenticationURL,
        keycloakConfiguration.realm);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorize) -> authorize
            .anyRequest().authenticated()
        )
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt
                .jwkSetUri(
                    authorizationUrl)
            ));
    // @formatter:on
    return http.build();
  }



  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(
        IGNORE_URL
    );
  }
}
