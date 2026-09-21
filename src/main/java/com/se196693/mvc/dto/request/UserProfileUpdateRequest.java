package com.se196693.mvc.dto.request;

import com.se196693.mvc.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateRequest implements  BaseUpdateUserRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Override
    public void applyUpdateTo(User user) {
        if (this.fullName != null)
            user.setFullName(this.fullName);
    }
}
