package com.icebear2n2.reservation.user.controller;

import com.icebear2n2.reservation.domain.request.CheckAuthCodeRequest;
import com.icebear2n2.reservation.domain.request.PhoneRequest;
import com.icebear2n2.reservation.user.service.AuthCodeService;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Balance;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verification")
public class AuthCodeController {

    private final AuthCodeService authCodeService;

    @PostMapping
    public ResponseEntity<String> sendAuthCode(@RequestBody PhoneRequest phoneRequest) {
        authCodeService.sendAuthCode(phoneRequest);
        return new ResponseEntity<>("SEND AUTH CODE SUCCESSFULLY.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<String> checkAuthCode(@RequestBody CheckAuthCodeRequest checkAuthCodeRequest) {
        authCodeService.checkAuthCode(checkAuthCodeRequest);
        return new ResponseEntity<>("CHECK AUTH CODE SUCCESSFULLY.", HttpStatus.OK);
    }

    @GetMapping("/balance")
    public ResponseEntity<Balance> getBalance() {
        Balance balance = authCodeService.getBalance();
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
