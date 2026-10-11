package com.edu.pe.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {

		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(passwordEncoder);
		return auth;
	}

	// ✅ CORS PARA ANGULAR
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(List.of("http://localhost:4200"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("*"));
		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			// ✅ IMPORTANTE PARA ANGULAR
			.cors(cors -> {})
			.csrf(csrf -> csrf.disable())

			.authorizeHttpRequests(auth -> auth

				// ✅ PERMITIR API PARA ANGULAR
				.requestMatchers("/api/**").permitAll()

				// LOGIN Y RECURSOS
				.requestMatchers(
					"/login",
					"/denegado",
					"/css/**",
					"/js/**",
					"/images/**"
				).permitAll()

				// LISTADOS WEB
				.requestMatchers(
					"/empleados",
					"/analistas",
					"/funcionales",
					"/ingenieros"
				).hasAnyAuthority(
					"ROLE_ADMIN", "ADMIN",
					"ROLE_ADMINISTRADOR", "ADMINISTRADOR",
					"ROLE_JEFE", "JEFE"
				)

				// ADMINISTRADOR
				.requestMatchers("/administrador/**")
				.hasAnyAuthority("ROLE_ADMINISTRADOR", "ADMINISTRADOR")

				// JEFE
				.requestMatchers("/jefes/**", "/jefe_proyectos")
				.hasAnyAuthority("ROLE_JEFE", "JEFE")

				// SOLO ADMIN MODIFICA
				.requestMatchers(
					"/empleados/**",
					"/analistas/**",
					"/funcionales/**",
					"/ingenieros/**",
					"/admin/**",
						"/usuarios/**"
				).hasAnyAuthority("ROLE_ADMIN", "ADMIN")

				.anyRequest().authenticated()
			)

			.formLogin(form -> form
				.loginPage("/login")
				.successHandler((request, response, authentication) -> {

					boolean esAdmin = authentication.getAuthorities()
						.stream()
						.anyMatch(a -> a.getAuthority().contains("ADMIN"));

					if (esAdmin) {
						response.sendRedirect("/empleados");
					} else {
						response.sendRedirect("/empleados");
					}
				})
				.permitAll()
			)

			.exceptionHandling(exception -> exception
				.accessDeniedPage("/denegado")
			)

			.logout(logout -> logout
				.logoutSuccessUrl("/login?logout")
				.permitAll()
			);

		return http.build();
	}
}
