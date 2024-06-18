package com.example.base.domain.user.service;

import com.example.base.domain.user.domain.User;
import com.example.base.domain.user.repository.UserRepository;
import com.example.base.global.util.convertor.TypeConvertor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("UserRepository.findOneById(), hexStringToByte()가 호출되어야 한다")
    void findOneByIDSpyOn (){
        // given
        String strId = "testasdf";
        byte[] id = strId.getBytes();
        User user = mock(User.class);
        when(userRepository.findOneById(id)).thenReturn(user);
        when(user.getId()).thenReturn(id);

        try (MockedStatic typeConvertor = mockStatic(TypeConvertor.class)){
            typeConvertor.when(()->TypeConvertor.hexStringToByte(Mockito.any())).thenReturn(id);

            User readUser = userService.findOneById(strId);

            //then
            assertThat(readUser.getId()).isEqualTo(user.getId());
            typeConvertor.verify(()-> TypeConvertor.hexStringToByte(strId), times(1));
            verify(userRepository, times(1)).findOneById(id);
        }
    }

}
