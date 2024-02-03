package com.example.java_assignment.coin;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/coinTypes")
public class CoinController {
  private final CoinService coinService;

  public CoinController(CoinService coinService) {
    this.coinService = coinService;
  }

  @GetMapping
  public List<Coin> coin() {
    return coinService.getCoin();
  }

  @PostMapping
  public void saveNewCoinType(@RequestBody Coin coin) {
    coinService.addCoin(coin);
  }

  @DeleteMapping(path = "/{coinId}")
  public void deleteCoinType(@PathVariable("coinId") Long id) {
    coinService.deleteCoin(id);
  }

  @PutMapping(path = "/{coinId}")
  public void updateCoinType(
      @PathVariable("coinId") Long coinId,
      @RequestParam(required = false) String type,
      @RequestParam(required = false) String chinese) {
    coinService.updateCoin(coinId, type, chinese);
  }
}
