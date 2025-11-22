package model;

import model.enums.CardCategory;
import model.enums.CardType;

import java.time.LocalDate;

public class LimitedTripsCard extends TransportCard {

    private int remainingTrips;

    public LimitedTripsCard(String id, CardCategory category, int trips) {
        super(id, category, CardType.LIMITED_TRIPS);
        this.remainingTrips = trips;
    }

    @Override
    public boolean canPass(LocalDate today) {
        return !blocked && remainingTrips > 0;
    }

    @Override
    public void onPass() {
        remainingTrips--;
    }

    @Override
    public String toString() {
        return super.toString() + ", trips=" + remainingTrips;
    }
}
