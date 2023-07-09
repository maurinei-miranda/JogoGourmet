package domain.usecases;

import domain.entities.Dish;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_OPTION;

public class InitGame {

  public static final String GAME_TITLE = "Jogo Gourmet";
  public static final String SURRENDER_TITLE = "Desisto";
  public static final String CONFIRM_TITLE = "Confirm";
  public static final String THINK_ABOUT_MESSAGE = "Pense em um prato que gosta";
  public static final String I_GOT_MESSAGE = "Acertei de novo!";
  public static final String WHICH_DISH_MESSAGE = "Qual prato você pensou?";
  public static final String COMPARE_MESSAGE = "%s é ______ mas %s não.";
  public static final String COMPLETE_TITLE = "Complete";
  public static final String DISH_QUESTION = "O prato que você pensou é %s?";

  public static void startGame() {
    Dish lasagnaDish = new Dish("Lasanha", List.of("massa"));
    Dish chocoCakeDish = new Dish("Bolo de Chocolate", new ArrayList<>());
    List<Dish> initalDish = new ArrayList<>(List.of(lasagnaDish, chocoCakeDish));

    chooseOneCharacteristic(initalDish);
  }

  public static void chooseOneCharacteristic(List<Dish> dishes) {
    JOptionPane.showMessageDialog(null,
            THINK_ABOUT_MESSAGE,
            GAME_TITLE,
            JOptionPane.PLAIN_MESSAGE);


    List<String> bruteList = new ArrayList<>();
    dishes.forEach(dish -> bruteList.addAll(dish.getIsSomething()));
    List<String> isSomethingList = bruteList.stream().distinct().toList();
    List<String> chosenCharacteristics = new ArrayList<>();

    for (int i = 0; i < isSomethingList.size(); i++) {
      String characteristic = isSomethingList.get(i);
      String dishCharacteristicQuestion = String.format(DISH_QUESTION, characteristic);

      int dishCharacteristicAnswer = JOptionPane.showConfirmDialog(null,
              dishCharacteristicQuestion,
              CONFIRM_TITLE,
              JOptionPane.YES_NO_OPTION);

      List<Dish> dishList = dishes;

      if (dishCharacteristicAnswer == YES_OPTION) {
        chosenCharacteristics.add(characteristic);
        dishList = dishList.stream().filter(dish -> dish.getIsSomething().contains(characteristic)).toList();
      } else {
        dishList = dishList.stream().filter(dish -> !dish.getIsSomething().contains(characteristic)).toList();
      }

      Dish lastDish = dishList.get(dishList.size() - 1);

      if (dishList.size() == 1) {
        String questionString = String.format(DISH_QUESTION, lastDish.getName());
        int isDishQuestion = JOptionPane.showConfirmDialog(null,
                questionString,
                CONFIRM_TITLE,
                JOptionPane.YES_NO_OPTION);

        if (isDishQuestion == YES_OPTION) {
          GotIt();
          chooseOneCharacteristic(dishes);
        } else {
          Dish dish = createNewDishPanel(lastDish, chosenCharacteristics);
          dishes.add(dish);
          chooseOneCharacteristic(dishes);

        }
      } else if (i == isSomethingList.size() - 1) {
        Dish newDish = createNewDishPanel(lastDish, chosenCharacteristics);
        dishes.add(newDish);
        chooseOneCharacteristic(dishes);
      }
    }


  }

  //Metodo para falar que venceu
  public static void GotIt() {
    JOptionPane.showMessageDialog(null, I_GOT_MESSAGE, GAME_TITLE, JOptionPane.PLAIN_MESSAGE);
  }

  public static Dish createNewDishPanel(Dish lastDish, List<String> chosenCharacteristics) {
    String newDishName = JOptionPane.showInputDialog(null,
            WHICH_DISH_MESSAGE,
            SURRENDER_TITLE,
            QUESTION_MESSAGE);

    String compareString = String.format(COMPARE_MESSAGE, newDishName, lastDish.getName());

    String newCharacteristic = JOptionPane.showInputDialog(null,
            compareString,
            COMPLETE_TITLE,
            QUESTION_MESSAGE);

    chosenCharacteristics.add(newCharacteristic);

    return new Dish(newDishName, chosenCharacteristics);
  }

}
