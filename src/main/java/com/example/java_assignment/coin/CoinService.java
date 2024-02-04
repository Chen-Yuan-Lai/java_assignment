package com.example.java_assignment.coin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CoinService {
  private final CoinRepository coinRepository;
  private final String json;

  public CoinService(CoinRepository coinRepository) {
    WebClient.Builder builder = WebClient.builder();
    String url = "https://api.coindesk.com/v1/bpi/currentprice.json";
    this.json = builder.build().get().uri(url).retrieve().bodyToMono(String.class).block();
    this.coinRepository = coinRepository;
  }

  public List<Coin> getCoin() {
    return coinRepository.findAll();
  }

  public Coin addCoin(Coin coin) {
    Optional<Coin> coinOptional = coinRepository.findCoinByType(coin.getType());
    if (coinOptional.isPresent()) {
      throw new IllegalStateException("The type has existed");
    }
    System.out.println(coin);
    coinRepository.save(coin);
    return coin;
  }

  public void deleteCoin(Long coinId) {
    boolean isExists = coinRepository.existsById(coinId);

    if (!isExists) {
      throw new IllegalStateException("coin with id" + coinId + "does not exists");
    }

    coinRepository.deleteById(coinId);
  }

  @Transactional
  public Coin updateCoin(Long coinId, String type, String chinese) {
    Coin coin = coinRepository
        .findById(coinId)
        .orElseThrow(
            () -> new IllegalStateException("coin with id" + coinId + "does not exists"));

    if (type != null && type.length() > 0 && !Objects.equals(coin.getType(), type)) {
      coin.setType(type);
    }

    if (chinese != null && chinese.length() > 0 && !Objects.equals(coin.getChinese(), chinese)) {
      coin.setChinese(chinese);
    }
    return coin;
  }

  public String getCoinDesk() {
    return this.json;
  }

  public String getNewCoinDesk() {
    ObjectMapper objectMapper = new ObjectMapper();
    List<BitcoinPrice> prices = new ArrayList<>();
    try {
      JsonNode rootNode = objectMapper.readTree(this.json);
      JsonNode bpiNode = rootNode.path("bpi");
      String time = rootNode.path("time").path("updatedISO").asText();
      if (bpiNode.isObject()) {
        Iterator<Map.Entry<String, JsonNode>> fields = bpiNode.fields();
        while (fields.hasNext()) {
          Map.Entry<String, JsonNode> field = fields.next();
          ObjectNode fieldValue = (ObjectNode) field.getValue();

          Optional<Coin> coinOptional = coinRepository.findCoinByType(fieldValue.get("code").asText());
          if (!coinOptional.isPresent()) {
            throw new IllegalStateException("The type not existed");
          }
          Coin coin = coinOptional.get();
          fieldValue.put("chinese", coin.getChinese());
          BitcoinPrice price = objectMapper.treeToValue(fieldValue, BitcoinPrice.class);
          prices.add(price);
        }
      }

      String output = objectMapper.writeValueAsString(new NewCoinDesk(prices, time));

      return output;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
