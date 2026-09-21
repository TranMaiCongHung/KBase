package com.se196693.mvc.dto.request;

import com.se196693.mvc.entity.User;
import com.se196693.mvc.enums.Role;
import com.se196693.mvc.enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateUserRequest implements  BaseUpdateUserRequest {

    @NotNull(message = "Role is required")
    private Role role;

    @NotNull(message = "Status is required")
    private UserStatus status;

    @Override
    public void applyUpdateTo(User user) {
        if (this.role != null)
            user.setRole(this.role);
        if (this.status != null)
            user.setStatus(this.status);
    }
}
