package com.example.contactsystem;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ContactSystemApplication {

    private record Status(
            String name,
            String url,
            String author,
            String email
    ) {
    }

    @GetMapping("/status")
    Status StatusCheck() {
        return new Status(
                "Contact System",
                "https://google.com",
                "Faisal Issaka",
                "phaisalsey6@gmail.com"
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(ContactSystemApplication.class, args);
    }
}
