package bowlingscorer.model;

public class Bonus extends Frame {

  public Bonus(int firstShot, int secondShot) {
    super(firstShot, secondShot);
  }

  @Override
  public boolean isBonus() {
    return true;
  }
}
