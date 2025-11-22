package model;

import model.enums.CardCategory;
import model.enums.CardType;
import model.enums.DurationType;

import java.time.LocalDate;

public class TimeBasedCard extends TransportCard {

    private DurationType duration;
    private LocalDate expirationDate;

    public TimeBasedCard(String id, CardCategory category, DurationType duration, LocalDate expirationDate) {
        super(id, category, CardType.TIME_BASED);
        this.duration = duration;
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean canPass(LocalDate today) {
        return !blocked && !today.isAfter(expirationDate);
    }

    @Override
    public void onPass() {
        // Nothing to deduct
    }

    @Override
    public String toString() {
        return super.toString() + ", expires=" + expirationDate;
    }
}
