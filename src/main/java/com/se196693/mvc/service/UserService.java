package com.se196693.mvc.service;

import com.se196693.mvc.dto.request.*;
import com.se196693.mvc.dto.response.PageResponse;
import com.se196693.mvc.dto.response.UserResponse;
import com.se196693.mvc.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    User findByUsername(String username);

    <T extends UserRequest> UserResponse createUser(T register);

    PageResponse<UserResponse> listUsers(UserFilterRequest filter, Pageable pageable);

    UserResponse getUser(Long id);

    UserResponse viewMyProfile(String username);

    <T extends BaseUpdateUserRequest> UserResponse updateUser(Long id,T request);
    <T extends BaseUpdateUserRequest> UserResponse updateUser(String username, T request);

    User processGoogleUser(OAuth2User oauth2User);

    User getCurrentUser();
}
