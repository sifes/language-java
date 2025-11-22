package model;

import model.enums.CardCategory;
import model.enums.CardType;

import java.time.LocalDate;

public abstract class TransportCard {

    protected String id;
    protected CardCategory category;
    protected CardType cardType;
    protected boolean blocked;

    public TransportCard(String id, CardCategory category, CardType type) {
        this.id = id;
        this.category = category;
        this.cardType = type;
        this.blocked = false;
    }

    public String getId() {
        return id;
    }

    public CardCategory getCategory() {
        return category;
    }

    public CardType getCardType() {
        return cardType;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void block() {
        this.blocked = true;
    }

    public abstract boolean canPass(LocalDate today);

    public abstract void onPass();

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", category=" + category +
                ", type=" + cardType +
                ", blocked=" + blocked +
                '}';
    }
}
