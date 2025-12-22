package com.tp.controller;

import com.tp.common.dto.UserDTO;
import com.tp.common.entity.User;
import com.tp.common.result.Result;
import com.tp.controller.user.UserController;
import com.tp.service.UserService;
import com.tp.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void register_ShouldReturnSuccess_WhenUserRegistrationSuccessful() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");
        userDTO.setPassword("password123");
        userDTO.setEmail("test@example.com");

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        when(userService.register(any(User.class))).thenReturn(true);

        // When & Then
        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password123\",\"email\":\"test@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("OK"));
    }

    @Test
    void login_ShouldReturnUser_WhenCredentialsValid() throws Exception {
        // Given
        User inputUser = new User();
        inputUser.setUsername("testuser");
        inputUser.setPassword("password123");

        User loginUser = new User();
        loginUser.setId(1L);
        loginUser.setUsername("testuser");
        loginUser.setEmail("test@example.com");

        when(userService.login("testuser", "password123")).thenReturn(loginUser);
        when(jwtUtil.generateToken("testuser")).thenReturn("test_token");

        // When & Then
        mockMvc.perform(post("/api/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"testuser\",\"password\":\"password123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("testuser"))
                .andExpect(jsonPath("$.data.token").value("test_token"));
    }
}
