package com.mindHub.HomeBanking.services;

import com.mindHub.HomeBanking.dtos.CardDTO;
import com.mindHub.HomeBanking.models.Card;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CardService {
    List<CardDTO> cardsDTO();
    CardDTO cardDTO(Long id);
    Card cardFindByNumber(String number);
    Card cardFindById(Long id);
    void saveCard(Card card);

}
