package com.loa.api.user.service;

import com.loa.api.common.JwtProvider;
import com.loa.api.user.domain.User;
import com.loa.api.user.dto.request.UserLoginRequest;
import com.loa.api.user.dto.request.UserSignupRequest;
import com.loa.api.user.dto.response.UserLoginResponse;
import com.loa.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signup(UserSignupRequest request) {
        // 이메일 중복 검사
        validateDuplicateEmail(request.getEmail());

        // 닉네임 중복 검사
        validateDuplicateNickname(request.getNickname());

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode((request.getPassword()));

        // Dto to Entity
        User user = request.toEntity(encodedPassword);

        // save
        userRepository.save(user);
    }

    public void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("중복된 이메일은 사용할 수 없습니다.");
        }
    }

    public void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("중복된 닉네임은 사용할 수 없습니다.");
        }
    }

    /**
     * 유저 로그인 로직
     */
    public UserLoginResponse login(UserLoginRequest request) {
        // 1. 이메일로 User 찾기
        User user = userRepository.findByEmail(request.getEmail())
                                  .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호를 확인해주세요."));

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호를 확인해주세요.");
        }

        // 3. jwt 토큰 발급
        String token = jwtProvider.createToken(user.getEmail());

        return new UserLoginResponse(token);
    }
}
