package com.tcp.rail.controller;

import com.tcp.rail.config.TcpMessageStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TcpMessageController {

    private final TcpMessageStore messageStore;

    public TcpMessageController(TcpMessageStore messageStore) {
        this.messageStore = messageStore;
    }

    @GetMapping("/messages")
    public String showMessages(Model model) {
        model.addAttribute("messages", messageStore.getMessages());
        return "messages"; // render templates/messages.html
    }
}
