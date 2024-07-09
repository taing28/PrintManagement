package org.example.printmanagement.model.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordRequest {
    private String confirmCode;
    private String newPassword;
    private String confirmPassword;
}
