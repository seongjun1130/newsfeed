package com.sparta.newsfeedproject.domain.member.client;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CountryService {
    private static final String API_URL = "https://restcountries.com/v3.1/all";
    private final RestTemplate restTemplate;
    @Getter
    private List<String> countryNames;

    public CountryService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @PostConstruct
    public void initCountryNames() {
        Object[] countries = restTemplate.getForObject(API_URL, Object[].class);
        if (countries != null) {
            countryNames = Arrays.stream(countries)
                    .map(country -> (Map<String, Object>) country)
                    .map(countryMap -> (Map<String, String>) countryMap.get("name"))
                    .map(nameMap -> nameMap.get("common"))
                    .toList();
        }
    }
}
