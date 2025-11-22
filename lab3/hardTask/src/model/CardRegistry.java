package model;

import java.util.HashMap;
import java.util.Map;

public class CardRegistry {

    private Map<String, TransportCard> cards = new HashMap<>();

    public void addCard(TransportCard card) {
        cards.put(card.getId(), card);
    }

    public TransportCard getCard(String id) {
        return cards.get(id);
    }
}
