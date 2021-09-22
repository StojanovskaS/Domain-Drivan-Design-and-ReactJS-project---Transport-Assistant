package com.demo.emt.carscatalog.xport.client;

import com.demo.emt.carscatalog.domain.valueobjects.SiteUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

@Service
public class SiteUserClient {
    //sakav da probam da naucam kako da rabotam i so resttemplate
    private final RestTemplate restTemplate;
    private final String serverUrl;

    public SiteUserClient(@Value("${app.share-ride-catalog.url}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.restTemplate = new RestTemplate();
        var requestFactory = new SimpleClientHttpRequestFactory();
        this.restTemplate.setRequestFactory(requestFactory);
    }

    private UriComponentsBuilder uri() {
        return UriComponentsBuilder.fromUriString(this.serverUrl);
    }

    public List<SiteUser> findAll() {
        try {
            return restTemplate.exchange(uri().path("/login/users").build().toUri(), HttpMethod.GET,null, new ParameterizedTypeReference<List<SiteUser>>() {
            }).getBody();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
