package com.example.restTemplate.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinuteCandleRequest {
    private String market;
    private int count;
}
