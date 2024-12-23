package com.example.demo.controller;

import com.example.demo.dom.Input;
import com.example.demo.service.GeoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class GuiController {

    @Autowired
    private WeatherController weatherController;

    @Autowired
    private GeoService geoService;

    private static void setTokenInfo(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
            // Regex to find the value of "iat"
            Pattern patternIat = Pattern.compile("iat=([^, }]+)");
            Matcher matcherIat = patternIat.matcher(principal.toString());

            if (matcherIat.find()) {
                String iat = matcherIat.group(1); // Extracts the captured value
                model.addAttribute("created", iat);
            } else {
                System.out.println("No exp value found.");
            }

            // Regex to find the value of "exp"
            Pattern patternExp = Pattern.compile("exp=([^, }]+)");
            Matcher matcherExp = patternExp.matcher(principal.toString());

            if (matcherExp.find()) {
                String expValue = matcherExp.group(1); // Extracts the captured value
                model.addAttribute("expires", expValue);
            } else {
                System.out.println("No exp value found.");
            }
        }
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    public String page(@ModelAttribute Input input, Model model, Principal principal) throws JsonProcessingException {
        ModelAndView view = new ModelAndView("input");
        view.addObject("input", input);
        model.addAttribute("score", weatherController.score(input.getContent()));
        model.addAttribute("location", geoService.search(input.getContent()));
        setTokenInfo(model, principal);
        return "form";
    }
}
