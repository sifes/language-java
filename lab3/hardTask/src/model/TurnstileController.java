package controller;

import model.TransportCard;

import java.time.LocalDate;

public class TurnstileController {

    private int successCount = 0;
    private int deniedCount = 0;

    public boolean tryPass(TransportCard card) {
        LocalDate today = LocalDate.now();

        if (card == null) {
            deniedCount++;
            return false;
        }

        if (!card.canPass(today)) {
            deniedCount++;
            return false;
        }

        card.onPass();
        successCount++;
        return true;
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getDeniedCount() {
        return deniedCount;
    }
}
