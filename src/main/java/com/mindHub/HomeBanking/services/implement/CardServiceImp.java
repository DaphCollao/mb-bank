package com.mindHub.HomeBanking.services.implement;

import com.mindHub.HomeBanking.dtos.CardDTO;
import com.mindHub.HomeBanking.models.Card;
import com.mindHub.HomeBanking.repositories.CardRepository;
import com.mindHub.HomeBanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImp implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Override
    public List<CardDTO> cardsDTO() {
        return cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }

    @Override
    public CardDTO cardDTO(Long id) {
        return cardRepository.findById(id).map(card -> new CardDTO(card)).orElse(null);
    }

    @Override
    public Card cardFindByNumber(String number) {
        return cardRepository.findByNumber(number);
    }

    @Override
    public Card cardFindById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }
}
