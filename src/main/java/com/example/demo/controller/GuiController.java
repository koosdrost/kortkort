package com.example.demo.controller;

import com.example.demo.dom.Input;
import com.example.demo.handler.KeycloakLogoutHandler;
import com.example.demo.service.GeoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class GuiController {

    @Autowired
    private WeatherController weatherController;

    @Autowired
    private GeoService geoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("input", new Input());
        return "form";
    }

    @PostMapping("/")
    public String page(@ModelAttribute Input input, Model model, Principal principal) throws JsonProcessingException {
        ModelAndView view = new ModelAndView("input");
        view.addObject("input", input);
        model.addAttribute("score", weatherController.score(input.getContent()));
        model.addAttribute("location", geoService.search(input.getContent()));
        model.addAttribute("username", principal.getName());
        return "form";
    }
}
