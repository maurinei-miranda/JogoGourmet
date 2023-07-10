package domain.usecases;

import domain.entities.Dish;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_OPTION;

public class InitGame {
  public InitGame() {
  }

  public static final String GAME_TITLE = "Jogo Gourmet";
  public static final String SURRENDER_TITLE = "Desisto";
  public static final String CONFIRM_TITLE = "Confirm";
  public static final String THINK_ABOUT_MESSAGE = "Pense em um prato que gosta";
  public static final String I_GOT_MESSAGE = "Acertei de novo!";
  public static final String WHICH_DISH_MESSAGE = "Qual prato você pensou?";
  public static final String COMPARE_MESSAGE = "%s é ______ mas %s não.";
  public static final String COMPLETE_TITLE = "Complete";
  public static final String DISH_QUESTION = "O prato que você pensou é %s?";

  public void startGame(List<Dish> initialDish) {
    chooseOneCharacteristic(initialDish);
  }

  private void chooseOneCharacteristic(List<Dish> dishes) {
    JOptionPane.showMessageDialog(null,
            THINK_ABOUT_MESSAGE,
            GAME_TITLE,
            JOptionPane.PLAIN_MESSAGE);

    List<String> onlyDistinctCharacteristic = new ArrayList<>(dishes)
            .stream().flatMap(dish -> dish.getIsSomething().stream()).distinct().toList();
    List<String> chosenCharacteristics = new ArrayList<>();
    List<String> notContainsCharacteristics = new ArrayList<>();

    for (int i = 0; i < onlyDistinctCharacteristic.size(); i++) {
      String characteristic = onlyDistinctCharacteristic.get(i);
      int dishCharacteristicAnswer = panelDishOrCharacteristicQuestion(characteristic);

      if (dishCharacteristicAnswer == YES_OPTION) {
        chosenCharacteristics.add(characteristic);
      } else {
        notContainsCharacteristics.add(characteristic);
      }

      List<Dish> filteredList = new ArrayList<>(filterDishList(dishes, chosenCharacteristics, notContainsCharacteristics));
      Dish lastDish = filteredList.get(filteredList.size() - 1);
      List<Dish> updatedDishList = dishes.stream().filter(dish1 -> !filteredList.contains(dish1)).toList();

      if (filteredList.size() == 1) {
        int isDishAnswer = panelDishOrCharacteristicQuestion(lastDish.getName());

        if (isDishAnswer == YES_OPTION) {
          GotIt();
          filteredList.addAll(updatedDishList);
          chooseOneCharacteristic(filteredList);
        } else {
          Dish dish = panelCreatesNewDish(lastDish, chosenCharacteristics);
          filteredList.add(dish);
          filteredList.addAll(updatedDishList);
          chooseOneCharacteristic(filteredList);
        }
      }
    }
  }

  private void GotIt() {
    JOptionPane.showMessageDialog(null,
            I_GOT_MESSAGE,
            GAME_TITLE,
            JOptionPane.INFORMATION_MESSAGE);
  }

  private List<Dish> filterDishList(List<Dish> dishes, List<String> containsCharacteristic, List<String> notContainsCharacteristic) {
    List<Dish> dishList = new ArrayList<>(dishes);
    if (containsCharacteristic.size() >= 1) {
      dishList = dishes.stream().filter(dish -> dish.getIsSomething().containsAll(containsCharacteristic)).toList();
    }
    if (notContainsCharacteristic.size() >= 1) {
      dishList = dishList.stream().filter(dish -> !dish.getIsSomething().containsAll(notContainsCharacteristic)).toList();
    }
    return dishList;
  }

  private int panelDishOrCharacteristicQuestion(String characteristicOrDish) {
    String dishCharacteristicQuestion = String.format(DISH_QUESTION, characteristicOrDish);

    int dishCharacteristicAnswer = JOptionPane.showConfirmDialog(null,
            dishCharacteristicQuestion,
            CONFIRM_TITLE,
            JOptionPane.YES_NO_OPTION);

    return dishCharacteristicAnswer;
  }

  private Dish panelCreatesNewDish(Dish lastDish, List<String> chosenCharacteristics) {
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
