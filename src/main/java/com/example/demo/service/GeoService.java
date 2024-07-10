package com.example.demo.service;

import com.example.demo.dom.Locatie;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeoService {

    private final RestTemplate restTemplate = new RestTemplate();

    public JSONObject search(String query) {
        String url = "https://nominatim.openstreetmap.org/search?format=json&q=" + query;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        JSONArray jsonArray = new JSONArray(response.getBody());
        if (!jsonArray.isEmpty()) {
            Locatie locatie = new Locatie();
            JSONObject base = jsonArray.getJSONObject(0);

            locatie.setLon(Double.parseDouble((String) base.get("lon")));
            locatie.setLat(Double.parseDouble((String) base.get("lat")));
            locatie.setName((String) base.get("name"));
            locatie.setDisplayName((String) base.get("display_name"));
            return base;
        }
        return null;
    }
}
