package account.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/api/auth/signup").permitAll()
                .mvcMatchers("/api/auth/changepass").authenticated()
                .mvcMatchers("/api/empl/**").hasAnyRole("USER", "ACCOUNTANT")
                .mvcMatchers("/api/acct/**").hasRole("ACCOUNTANT")
                .mvcMatchers("/api/admin/**").hasRole("ADMIN")
                //.anyRequest().authenticated()
                .and()
                .httpBasic();

        http
                .csrf().disable()
                .headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}
