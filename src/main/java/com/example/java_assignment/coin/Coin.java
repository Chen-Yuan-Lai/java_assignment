package com.example.java_assignment.coin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "coin")
public class Coin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "type", nullable = false)
  private String type;
  
  @Column(name = "chinese", nullable = false)
  private String chinese;
  
  public Coin(String type, String chinese) {
    this.type = type;
    this.chinese = chinese;
  }

}
