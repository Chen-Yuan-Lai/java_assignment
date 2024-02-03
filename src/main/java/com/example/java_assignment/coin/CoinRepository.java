package com.example.java_assignment.coin;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long>{

  @Query("SELECT c FROM Coin c WHERE c.type = ?1")
  Optional<Coin> findCoinByType(String type);
}
