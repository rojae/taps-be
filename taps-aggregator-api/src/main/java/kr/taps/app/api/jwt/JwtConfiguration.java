package kr.taps.app.api.jwt;

import kr.taps.app.api.security.JwtAuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JwtConfiguration {

    private final JwtProps jwtProps;

    @Bean
    public JwtAuthTokenProvider jwtProvider() {
        return new JwtAuthTokenProvider(jwtProps.secret);
    }

}
