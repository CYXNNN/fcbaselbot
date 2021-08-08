package ch.egli.util;

public enum States {

  ZERO("Game not started yet"),
  SIX("Halftime"),
  HUNDRED("Game is over");

  private String action;

  States(String action) {
    this.action = action;
  }

}
