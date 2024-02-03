package com.example.java_assignment.coin;

import java.util.List;

public class NewCoinDesk {
  public List<BitcoinPrice> prices;
  public String updatedTime;

  public NewCoinDesk(List<BitcoinPrice> prices, String updatedTime) {
    this.prices = prices;
    this.updatedTime = updatedTime;
  }
}
