package domain.entities;

import java.util.List;

public class Dish {
  private String name;
  private List<String> isSomething;

  public Dish(String name, List<String> isSomething) {
    this.name = name;
    this.isSomething = isSomething;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getIsSomething() {
    return isSomething;
  }

  public void setIsSomething(List<String> isSomething) {
    this.isSomething = isSomething;
  }
}
