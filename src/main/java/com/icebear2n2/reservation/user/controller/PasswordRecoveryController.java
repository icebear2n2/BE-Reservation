package com.icebear2n2.reservation.user.controller;

import com.icebear2n2.reservation.domain.request.PasswordRecoveryRequest;
import com.icebear2n2.reservation.user.service.PasswordRecoveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password/recovery")
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> requestCode(@PathVariable Long userId
    ) {
        passwordRecoveryService.requestPasswordRecovery(userId);
        return new ResponseEntity<>("SEND AUTH CODE SUCCESSFULLY.", HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<String> resetPassword(
            @RequestBody
            PasswordRecoveryRequest passwordRecoveryRequest) {
        passwordRecoveryService.verifyAuthCodeAndResetPassword(passwordRecoveryRequest);
        return new ResponseEntity<>("Password reset was successful.", HttpStatus.OK);
    }
}