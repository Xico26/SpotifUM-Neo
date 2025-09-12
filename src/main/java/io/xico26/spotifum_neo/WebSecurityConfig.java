//package io.xico26.spotifum_neo;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(authorizeHttp -> {
//                    authorizeHttp.requestMatchers("/").permitAll();
//                    authorizeHttp.anyRequest().authenticated();
//                })
//                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/"))
//                .logout(logout -> logout.logoutUrl("/logout").permitAll())
//                .build();
//    }
//}
