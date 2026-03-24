package com.loa.api.user.service;

import com.loa.api.user.domain.User;
import com.loa.api.user.dto.request.UserSignupRequest;
import com.loa.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void signup(UserSignupRequest request) {
        // 이메일 중복 검사
        validateDuplicateEmail(request.getEmail());

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
}
