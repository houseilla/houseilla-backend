package com.houseilla.houseillabackend.model;

import lombok.Data;

@Data
public class OtpRequest {

    private String phone;
    private String email;
    private String otpCode;
}
