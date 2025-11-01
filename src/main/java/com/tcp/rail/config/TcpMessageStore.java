package com.tcp.rail.config;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class TcpMessageStore {
    private final List<String> messages = Collections.synchronizedList(new LinkedList<>());

    public void addMessage(String message) {
        messages.add(0, message); // add newest on top
        if (messages.size() > 100) { // keep memory safe
            messages.remove(messages.size() - 1);
        }
    }

    public List<String> getMessages() {
        return messages;
    }
}
