package com.example.restTemplate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestController testController;

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void test(){
        //when
        String result = restTemplate.getForObject("http://localhost:" + port + "/api/v1/test", String.class);

        //then
        assertThat(result).contains("Hello, World");
    }
}