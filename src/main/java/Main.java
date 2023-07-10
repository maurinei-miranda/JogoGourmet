import domain.entities.Dish;
import domain.usecases.InitGame;

import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    InitGame game = new InitGame();
    Dish lasagnaDish = new Dish("Lasanha", List.of("massa"));
    Dish chocoCakeDish = new Dish("Bolo de Chocolate", new ArrayList<>());
    List<Dish> initialDish = new ArrayList<>(List.of(lasagnaDish, chocoCakeDish));

    game.startGame(initialDish);
  }
}