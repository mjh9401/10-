package com.example.restTemplate.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinuteCandleRequest {
    private String market;
    private int count;
}
