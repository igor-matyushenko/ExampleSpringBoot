package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping
    public String greeting(@RequestParam(name = "name",required = false, defaultValue = "user")String name, Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }



    @GetMapping("/main")
    public String main(@RequestParam(name = "filter", required = false, defaultValue = "") String filter, Model model){
        Iterable<Message> messages;
        if(filter != null && filter.isEmpty()){
            messages = messageRepository.findAll();
        } else {
            messages = messageRepository.findByTag(filter);
        }
        model.addAttribute("messages",messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "text") String text,
            @RequestParam(name = "tag") String tag,
            Model model){
        Message message = new Message(text,tag,user);
        messageRepository.save(message);
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages",messages);
        model.addAttribute("filter", "");
        return "main";
    }




}
