package com.example.java_assignment;

import com.example.java_assignment.coin.Coin;
import com.example.java_assignment.coin.CoinController;
import com.example.java_assignment.coin.CoinDeskController;
import com.example.java_assignment.coin.CoinService;
import com.example.java_assignment.coin.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CoinController.class)
class CoinControllerTests {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private CoinController coinController;
  @Autowired
  private ObjectMapper objectMapper = new ObjectMapper();
  @MockBean
  private CoinService coinService;
  private List<Coin> mockCoins;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.coinController)
        .setControllerAdvice(new GlobalExceptionHandler()).build();
    mockCoins = Arrays.asList(
        new Coin(1L, "USD", "美元"),
        new Coin(2L, "GBP", "新台幣"),
        new Coin(3L, "EUR", "歐元"));
  }

  @Test
  public void getCoinTypesTest() throws Exception {
    Mockito.when(coinService.getCoin()).thenReturn(mockCoins);

    mockMvc
        .perform(get("/api/v1/coinTypes").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(mockCoins.size()))
        .andExpect(jsonPath("$[0].type").value(mockCoins.get(0).getType()))
        .andExpect(jsonPath("$[1].type").value(mockCoins.get(1).getType()))
        .andExpect(jsonPath("$[2].type").value(mockCoins.get(2).getType()))
        .andDo(result -> System.out.println(result.getResponse().getContentAsString()));
  }

  @Test
  public void addCoinTypeTest() throws Exception {
    Coin newCoinType = new Coin(4L, "VND", "越南盾");
    String requestBody = objectMapper.writeValueAsString(newCoinType);
    Mockito.when(coinService.addCoin(Mockito.any(Coin.class))).thenReturn(newCoinType);

    mockMvc.perform(post("/api/v1/coinTypes").contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isCreated());
  }

  @Test
  public void updateCoinTypeTest() throws Exception {
    Coin updateCoinType = new Coin(1L, "VND", "越南盾");
    String requestBody = objectMapper.writeValueAsString(updateCoinType);
    Mockito.when(coinService.updateCoin(Mockito.eq(1L), Mockito.eq("VND"), Mockito.eq("越南盾")))
        .thenReturn(updateCoinType);

    mockMvc
        .perform(put("/api/v1/coinTypes/1").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.type").value(updateCoinType.getType()))
        .andExpect(jsonPath("$.chinese").value(updateCoinType.getChinese()))
        .andDo(result -> System.out.println(result.getResponse().getContentAsString()));

  }

  @Test
  public void deleteCoinTypeTest() throws Exception {
    Mockito.doNothing().when(coinService).deleteCoin(1L);

    mockMvc.perform(delete("/api/v1/coinTypes/1"))
        .andExpect(status().isNoContent());

  }
}

@WebMvcTest(CoinDeskController.class)
class CoinDeskControllerTests {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private CoinDeskController coinDeskController;
  @MockBean
  private CoinService coinService;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(this.coinDeskController)
        .setControllerAdvice(new GlobalExceptionHandler()).build();
  }

  @Test
  public void getCoinDeskTest() throws Exception {

  }

  @Test
  public void getNewCoinDeskTest() throws Exception {

  }
}