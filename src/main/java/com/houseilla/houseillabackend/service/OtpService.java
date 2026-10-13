package com.houseilla.houseillabackend.service;

public interface OtpService {
    /**
     * Entry method configuration for generating and handling verification tokens.
     *
     * @param email The target communication destination address.
     * @param phone The target destination mobile contact string.
     */
    void processOtpRequest(String email, String phone);

    /**
     * Entry method configuration for validating session verification codes.
     */
    boolean validateOtpCode(String email, String submittedCode);
}
