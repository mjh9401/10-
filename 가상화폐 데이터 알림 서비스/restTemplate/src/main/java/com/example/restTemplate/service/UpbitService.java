package com.example.restTemplate.service;

import com.example.restTemplate.data.MinuteCandleRequest;
import com.example.restTemplate.data.MinuteCandle;
import com.example.restTemplate.http.HttpClient;
import com.example.restTemplate.http.UpbitFeignClient;
import com.example.restTemplate.http.UpbitMinuteCandle;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UpbitService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final UpbitFeignClient upbitFeignClient;

    public UpbitService(HttpClient httpClient, ObjectMapper objectMapper, UpbitFeignClient upbitFeignClient) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.upbitFeignClient = upbitFeignClient;
    }


    public List<MinuteCandle> getCandles(int unit, MinuteCandleRequest request) throws JsonProcessingException {
        // uri
        String uri = UriComponentsBuilder.fromUriString("https://api.upbit.com/")
                .path("v1/candles/seconds")
                .queryParam("market", request.getMarket())
                .queryParam("count", request.getCount())
                .build()
                .toString();

        // header
        Map<String,String> headers = new HashMap<>();
        headers.put("accept","application/json");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);

        // call
        // rest-template
        String result = httpClient.getData(uri, HttpMethod.GET, httpHeaders);

        // feign
        // 오류 발생해서 작동안됨
        String feingReulst = upbitFeignClient.getMinuteCandle(unit, request.getMarket(), request.getCount());

        List<UpbitMinuteCandle> upbitMinuteCandles = objectMapper.readValue(result, new TypeReference<List<UpbitMinuteCandle>>() {});

        return  upbitMinuteCandles.stream().map(it ->MinuteCandle.builder()
                .market(it.getMarket())
                .build()).collect(Collectors.toList());
    }
}
