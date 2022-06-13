package pl.pdec.city.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${server.servlet.session.cookie.name:JSESSIONID}")
    private String sessionCookie = null;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/logout/**").and()
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .mvcMatchers("/", "/logout", "/ag/**").permitAll()
                        .mvcMatchers("/actuator/health", "/favicon.ico").permitAll()
                        .mvcMatchers("/testget", "/api/**").permitAll()
                        .mvcMatchers("/testSaveUser").permitAll()
                        .anyRequest().authenticated())
                .httpBasic().and()
//                .formLogin().loginPage("/login").loginProcessingUrl("/api/ag_login").permitAll().and()
                .logout().logoutUrl("/api/logout").logoutSuccessUrl("/ag/")
                .deleteCookies(sessionCookie).invalidateHttpSession(true);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
