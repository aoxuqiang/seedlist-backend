package com.example.seedlist.repository;

import com.example.seedlist.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

}
