package com.example.java_assignment.coin;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/coinTypes")
public class CoinController {
  private final CoinService coinService;

  public CoinController(CoinService coinService) {
    this.coinService = coinService;
  }

  @GetMapping
  public ResponseEntity<List<Coin>> coin() {
    List<Coin> coinList = coinService.getCoin();
    return new ResponseEntity<>(coinList, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<Coin> saveNewCoinType(@RequestBody Coin coin) {
    Coin newCoin = coinService.addCoin(coin);
    return new ResponseEntity<>(newCoin, HttpStatus.CREATED);
  }

  @PutMapping(path = "/{coinId}")
  public ResponseEntity<Coin> updateCoinType(@PathVariable("coinId") Long coinId, @RequestBody Coin coin) {
    Coin updateCoin = coinService.updateCoin(coinId, coin.getType(), coin.getChinese());
    return new ResponseEntity<>(updateCoin, HttpStatus.OK);
  }

  @DeleteMapping(path = "/{coinId}")
  public ResponseEntity<Coin> deleteCoinType(@PathVariable("coinId") Long id) {
    coinService.deleteCoin(id);
    return ResponseEntity.noContent().build();
  }

}
