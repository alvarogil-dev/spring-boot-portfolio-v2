package dev.alvarogil.portfolio.adapters.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value = "/test")
    public ResponseEntity<Void> test() {
        return ResponseEntity.noContent().build();
    }
}
