package model;

import model.enums.CardCategory;
import model.enums.CardType;

import java.time.LocalDate;

public class AccumulativeCard extends TransportCard {

    private double balance;
    private final double PRICE_PER_RIDE = 15.0;

    public AccumulativeCard(String id, double balance) {
        super(id, CardCategory.REGULAR, CardType.ACCUMULATIVE);
        this.balance = balance;
    }

    @Override
    public boolean canPass(LocalDate today) {
        return !blocked && balance >= PRICE_PER_RIDE;
    }

    @Override
    public void onPass() {
        balance -= PRICE_PER_RIDE;
    }

    @Override
    public String toString() {
        return super.toString() + ", balance=" + balance;
    }
}
