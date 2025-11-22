import controller.TurnstileController;
import model.CardRegistry;
import view.MenuView;

public class Main {
    public static void main(String[] args) {
        CardRegistry registry = new CardRegistry();
        TurnstileController controller = new TurnstileController();
        MenuView view = new MenuView();

        view.start(registry, controller);
    }
}
