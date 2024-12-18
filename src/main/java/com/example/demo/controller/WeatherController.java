package com.example.demo.controller;

import com.example.demo.dom.Weer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
public class WeatherController {

    private final static String URL = "https://weerlive.nl/api/weerlive_api_v2.php?key=9498168f1b&locatie=";
    private static String previousJson = "";

    @GetMapping("/score")
    public Weer score(String location) throws JsonProcessingException {
        ResponseEntity<String> entity = restTemplate().getForEntity(URL + location, String.class);

        ObjectMapper mapper = new JsonMapper();
        JsonNode json = mapper.readTree(entity.getBody()).get("liveweer").get(0);

        if (json != null && (Objects.equals(previousJson, ""))) {
            previousJson = json.asText();

            Weer liveweer = new Weer();
            liveweer.setGevoelstemperatuur(json.get("gtemp").asDouble());
            liveweer.setTemperatuur(json.get("temp").asDouble());
            liveweer.setWind(json.get("windms").asDouble());
            liveweer.setBeschrijving(json.get("samenv").textValue());
            liveweer.setAdvies(calculate(liveweer));
            return liveweer;
        } else {
            return new Weer();
        }
    }

    // TODO:
    // windjack bij wind
    // regenjack bij regen
    // zonnebril bij zon, bij regen ongekleurde glazen

    private String calculate(Weer liveweer) {
        StringBuilder builder = new StringBuilder();

        if (liveweer.getGevoelstemperatuur() > 17) {
            builder.append("kort-kort");
        }
        if (liveweer.getGevoelstemperatuur() > 12 && liveweer.getGevoelstemperatuur() <= 17) {
            builder.append( "kort-lang");
        }
        if (liveweer.getGevoelstemperatuur() <= 12) {
            builder.append( "lang-lang");
        }

        if (liveweer.getGevoelstemperatuur() <= 8) {
            builder.append(" / thermo");
        }
        if (liveweer.getWind() > 5) {
            builder.append( " / windjack");
        }
        return builder.toString();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
