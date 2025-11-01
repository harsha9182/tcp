package com.tcp.rail.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class Hello {
    @GetMapping("/messages")
    public String showMessages() {
        return "messages"; // render templates/messages.html
    }
}
