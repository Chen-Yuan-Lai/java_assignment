package com.example.java_assignment.coin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoinPrice {
  private String code;
  private String rate;
  private String chinese;
}
