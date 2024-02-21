package com.icebear2n2.reservation.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRecoveryRequest {
    private Long userId;
    private String code;
    private String newPassword;
    private String confirmNewPassword;
}