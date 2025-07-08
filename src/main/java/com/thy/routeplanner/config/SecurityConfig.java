package com.thy.routeplanner.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS yapılandırmasını Spring Security'ye entegre ediyoruz
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // CSRF korumasını devre dışı bırakıyoruz. API'ler için genellikle gereklidir,
                // ancak üretimde dikkatli olun ve CSRF token yönetimini düşünün.
                .csrf(csrf -> csrf.disable())
                // Yetkilendirme kuralları:
                .authorizeHttpRequests(authorize -> authorize
                        // Swagger UI ve API dokümantasyon endpoint'lerine herkesin erişmesine izin ver
                        .requestMatchers(
                                "/v3/api-docs/**",           // OpenAPI 3 dokümantasyon JSON/YAML
                                "/swagger-ui/**",             // Swagger UI arayüzü dosyaları
                                "/swagger-ui.html",           // Swagger UI ana sayfası
                                "/webjars/**",                // Swagger UI'ın kullandığı statik kaynaklar
                                "/swagger-resources/**",
                                "/swagger-resources",
                                "/api/**",                    // Mevcut API endpoint'leri
                                "/auth/**"                    // Kimlik doğrulama endpoint'leri
                        ).permitAll()
                        // Diğer tüm isteklere kimlik doğrulaması gerektir
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // İzin verilen kaynaklar (ön yüz uygulamanızın çalıştığı adres)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Bu kısmı kontrol edin ve doğru olduğundan emin olun!
        // İzin verilen HTTP metotları
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // İzin verilen HTTP başlıkları (Authorization başlığı da dahil)
        configuration.setAllowedHeaders(Arrays.asList("*"));
        // Kimlik bilgilerinin (cookie'ler, Authorization başlıkları) gönderilmesine izin ver
        configuration.setAllowCredentials(true);
        // CORS pre-flight (ön kontrol) isteğinin önbellekte ne kadar süre kalacağını belirtir (saniye cinsinden)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Bu CORS yapılandırmasını tüm yollara (/**) uygula
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
