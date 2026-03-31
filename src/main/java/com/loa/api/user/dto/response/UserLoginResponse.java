package com.loa.api.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 성공 응답 DTO")
public class UserLoginResponse {

    @Schema(description = "JWT 인증 토큰")
    private String token;
}
