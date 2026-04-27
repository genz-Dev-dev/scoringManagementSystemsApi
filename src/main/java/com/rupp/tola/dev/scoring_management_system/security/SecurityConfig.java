package com.rupp.tola.dev.scoring_management_system.security;

import com.rupp.tola.dev.scoring_management_system.filter.JwtAuthenticationFilter;
import com.rupp.tola.dev.scoring_management_system.security.handler.CustomeAccessDeniedHandler;
import com.rupp.tola.dev.scoring_management_system.security.handler.CustomeAuthenticationEntryPoint;

import java.util.List;

import com.rupp.tola.dev.scoring_management_system.service.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AppUserDetailsService userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final CustomeAccessDeniedHandler accessDeniedHandler;
	private final CustomeAuthenticationEntryPoint authenticationEntryPoint;

	private static final String[] PUBLIC_URLS = { "/auth/**", "/oauth2/**" , "/css/**", "/js/**", "/images/**", "/swagger-ui.html",
			"/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**" , "/uploads/**" };

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(configurationSource()))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						registry -> registry.requestMatchers(PUBLIC_URLS).permitAll().anyRequest().authenticated())
				.authenticationProvider(authenticationProvider())
				.exceptionHandling(ex -> {
					ex.authenticationEntryPoint(authenticationEntryPoint);
					ex.accessDeniedHandler(accessDeniedHandler);
				})
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
		return config.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	@Bean
	public CorsConfigurationSource configurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:4200" , "http://localhost:5173","http://localhost:12000"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowedMethods(List.of("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource urlCorsConfig = new UrlBasedCorsConfigurationSource();
		urlCorsConfig.registerCorsConfiguration("/**" , config);
		return urlCorsConfig;
	}


}