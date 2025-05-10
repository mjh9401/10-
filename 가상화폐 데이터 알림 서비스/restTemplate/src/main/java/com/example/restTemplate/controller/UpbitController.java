package com.example.restTemplate.controller;

import com.example.restTemplate.data.MinuteCandle;
import com.example.restTemplate.data.MinuteCandleRequest;
import com.example.restTemplate.service.UpbitService;
import com.example.restTemplate.validation.MinuteValidater;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UpbitController {

    private final MinuteValidater minuteValidater;
    private final UpbitService upbitService;

    public UpbitController(MinuteValidater minuteValidater, UpbitService upbitService) {
        this.minuteValidater = minuteValidater;
        this.upbitService = upbitService;
    }

    @GetMapping("/api/v1/candle/minute/{unit}")
    public List<MinuteCandle> getMinuteCandles(@PathVariable int unit, MinuteCandleRequest request, BindingResult bindingResult) throws JsonProcessingException {
        minuteValidater.validate(unit,bindingResult);

        if(bindingResult.hasErrors()){
            return new ArrayList<>();
        }

        return upbitService.getCandles(unit,request);
    }
}
