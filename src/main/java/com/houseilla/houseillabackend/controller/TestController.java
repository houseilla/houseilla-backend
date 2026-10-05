package com.houseilla.houseillabackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String TestHouseilla() {
        return "Wellcome Houseilla Working";
    }
}
