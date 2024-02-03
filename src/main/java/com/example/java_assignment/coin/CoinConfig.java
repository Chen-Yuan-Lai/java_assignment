package com.example.java_assignment.coin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoinConfig {

  @Bean
  CommandLineRunner commandLineRunner(CoinRepository repository) {
    return args -> {
      Coin gbp = new Coin("GBP", "新台幣");
      Coin usd = new Coin("USD", "美元");
      Coin eur = new Coin("EUR", "歐元");

      repository.save(gbp);
      repository.save(usd);
      repository.save(eur);
    };
  }
}
