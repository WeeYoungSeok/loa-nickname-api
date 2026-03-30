package com.loa.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("로스트아크 닉네임 검색 서비스 API 명세서")
                .description("회원가입, 로그인 및 닉네임 검색 기능을 제공하는 API 문서입니다.")
                .version("1.0.0"));
    }
}
