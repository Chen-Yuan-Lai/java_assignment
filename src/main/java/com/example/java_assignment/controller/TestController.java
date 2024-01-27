package com.example.java_assignment.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @GetMapping("/hello")
  public String Hello() {
    return "Hellooo";
  }

}
