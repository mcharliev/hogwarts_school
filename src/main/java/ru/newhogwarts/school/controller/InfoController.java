package ru.newhogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/getPort")
    public String getPort() {
        return serverPort;
    }

    @GetMapping("/findFastestTimeToCalculateSum")
    public Integer findFastestTimeToCalculateSum() {
        return Stream.iterate(1, a -> a + 1).limit(1_000_000)
                .mapToInt(value -> value).sum();
    }
}
