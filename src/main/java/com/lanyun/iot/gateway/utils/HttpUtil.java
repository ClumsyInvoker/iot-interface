package com.lanyun.iot.gateway.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    public static <T> T postFromJson(String url, @Nullable Object object, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(object, headers);
        return restTemplate.postForObject(url, httpEntity, clazz);
    }

    public static <T> T postForFormUrlencoded(String url, MultiValueMap<String, Object> postParametersMap, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParametersMap, headers);
        return restTemplate.postForObject(url, httpEntity, clazz);
    }

    public static <T> T get(String url, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, clazz);
    }

    public static <T> T get(String url, Class<T> clazz, Map<String, String> uriVariables) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (uriVariables.size() > 0) {
            uriVariables.entrySet().stream().forEach(o -> builder.queryParam(o.getKey(),o.getValue()));
        }
        return restTemplate.getForObject(builder.build().encode().toString(), clazz);
    }
}
