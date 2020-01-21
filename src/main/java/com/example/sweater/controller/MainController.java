package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Controller //контроллер - моуль программы, который слушает запросы и возвращает какие-то данные
public class MainController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(@RequestParam(name = "name",required = false, defaultValue = "World")String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages",messages);
        return "main";
    }

    @PostMapping("/add")
    public String add(@RequestParam(name = "text") String text, @RequestParam(name = "tag") String tag, Map<String, Object> model){
        Message message = new Message(text,tag);
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages",messages);
        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam(name = "filter") String text, Map<String, Object> model){
        Iterable<Message> messages;
        if(text != null && text.isEmpty()){
            messages = messageRepository.findAll();
        } else {
            messages = messageRepository.findByTag(text);
        }
        model.put("messages",messages);
        return "main";
    }


}
