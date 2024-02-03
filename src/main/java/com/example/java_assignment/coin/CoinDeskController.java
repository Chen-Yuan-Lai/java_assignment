package com.example.java_assignment.coin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/coinDesk")
public class CoinDeskController {
  private final CoinService coinService;

  public CoinDeskController(CoinService coinService) {
    this.coinService = coinService;
  }

  @GetMapping
  public String coinDesk() {
    return coinService.getCoinDesk();
  }

  @GetMapping("/new")
  public String newCoinDesk() {
    return coinService.getNewCoinDesk();
  }
}
