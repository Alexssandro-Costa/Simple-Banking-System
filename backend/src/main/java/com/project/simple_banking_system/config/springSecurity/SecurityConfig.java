package com.project.simple_banking_system.config.springSecurity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.DispatcherType;

/**
 * Classe principal de configuração do Spring Security para a aplicação.
 * <p>
 * Habilita a segurança web e define a cadeia de filtros de segurança (Security
 * Filter Chain),
 * as regras de autorização de rotas (quais são públicas e quais são
 * protegidas), a política de sessão
 * e os beans de criptografia e gerenciamento de autenticação.
 * </p>
 *
 * @author Alexssandro
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Filtro customizado que intercepta as requisições para validar e autenticar o
     * token JWT.
     */
    @Autowired
    SecurityFilter securityFilter;

    /**
     * Configura a cadeia de filtros de segurança (SecurityFilterChain) da
     * aplicação.
     * <p>
     * Define o comportamento para CSRF, CORS, gerência de sessão e as regras de
     * controle de acesso
     * baseado nas rotas HTTP. Também injeta o filtro de segurança JWT antes do
     * filtro padrão do Spring.
     * </p>
     *
     * @param http O objeto {@link HttpSecurity} para configurar a segurança web.
     * @return A instância configurada de {@link SecurityFilterChain}.
     * @throws Exception Caso ocorra algum erro durante a construção da cadeia de
     *                   filtros.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http

                // Desabilita CSRF (Cross-Site Request Forgery) pois a API é STATELESS e usa
                // tokens JWT
                .csrf(csrf -> csrf.disable())

                // Reutiliza as configurações de CORS defini das no escopo do projeto
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Define a política de sessão como STATELESS (não armazena estado no servidor)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Define o controle de permissões por requisição HTTP
                .authorizeHttpRequests(authorize -> authorize

                        // Permite requisições de erro internas do container do servlet
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()

                        // Liberação TOTAL para as rotas de documentação do Swagger/OpenAPI
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**")
                        .permitAll()

                        // Endpoints públicos de autenticação e registro de usuários
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()

                        // Endpoint temporário para testes de conectividade
                        .requestMatchers(HttpMethod.GET, "/api/auth/test").permitAll()

                        // Qualquer outra requisição não listada acima exige autenticação prévia
                        .anyRequest().authenticated())

                // Adiciona o filtro JWT customizado ANTES do filtro padrão de autenticação por
                // usuário/senha
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Cria a configuração de CORS utilizada pelo Spring Security.
     *
     * <p>
     * Define as origens, os métodos HTTP e os cabeçalhos permitidos
     * para as requisições recebidas pela aplicação.
     * </p>
     *
     * @return fonte de configuração de CORS aplicada aos endpoints da aplicação
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Define as origens autorizadas a realizar requisições à API.
        config.setAllowedOrigins(List.of(
                "http://localhost",
                "https://sistema-bancario-simplificado.onrender.com",
            "https://symbank.onrender.com"));

        // Define os métodos HTTP permitidos nas requisições.
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE"));

        // Permite todos os cabeçalhos HTTP nas requisições.
        config.setAllowedHeaders(List.of("*"));

        // Associa a configuração de CORS a todos os endpoints da aplicação.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    /**
     * Define o Bean do {@link AuthenticationManager}, responsável por processar os
     * pedidos de autenticação.
     * 
     * @param authenticationConfiguration A configuração de autenticação fornecida
     *                                    pelo Spring.
     * @return O gerenciador de autenticação configurado.
     * @throws Exception Caso ocorra algum erro ao obter o gerenciador.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();

    }

    /**
     * Define o codificador de senhas da aplicação utilizando o algoritmo BCrypt.
     * <p>
     * O BCrypt aplica uma função de hash forte com salting aleatório para garantir
     * o armazenamento
     * seguro das senhas dos clientes no banco de dados.
     * </p>
     *
     * @return Uma instância de {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
