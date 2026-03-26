package com.loa.api.user.service;

import com.loa.api.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("중복된 이메일로 가입을 시도하면 예외가 터져야 한다.")
    void validateDuplicateEmailTest() {
        // given : 가짜 DB에게 cccv_@naver.com이라는 이메일이 있냐고 물어본다.
        String email = "cccv_@naver.com";
        given(userRepository.existsByEmail(email)).willReturn(true);

        // when & then : UserService가 중복 검사를 했을 때, IllegalArgumentException 에러가 터져야 성공이다.
        assertThrows(IllegalArgumentException.class, () -> {
            userService.validateDuplicateEmail(email);
        });
    }

    @Test
    @DisplayName("중복된 닉네임으로 가입을 시도하면 예외가 터져야 한다.")
    void validateDuplicateNicknameTest() {
        // given : 가짜 DB에게 홍길동이라는 이메일이 있냐고 물어본다.
        String nickname = "홍길동";
        given(userRepository.existsByNickname(nickname)).willReturn(true);

        // when & then : UserService가 중복 검사를 했을 때, IllegalArgumentException 에러가 터져야 성공이다.
        assertThrows(IllegalArgumentException.class, () -> {
            userService.validateDuplicateNickname(nickname);
        });
    }
}