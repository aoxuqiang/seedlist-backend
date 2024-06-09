package com.example.seedlist.service;

import com.example.seedlist.entity.Token;
import com.example.seedlist.repository.TokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService extends BaseService<TokenRepository, Token, Integer> {

    protected TokenService(TokenRepository repository) {
        super(repository);
    }
}
