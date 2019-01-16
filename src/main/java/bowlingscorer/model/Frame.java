package bowlingscorer.model;

public class Frame {

  private int firstBall;
  private int secondBall;

  public Frame() {}

  public Frame(int firstBall, int secondBall) {
    this.firstBall = firstBall;
    this.secondBall = secondBall;
  }

  public int getTotalKnockedPins() {
    return firstBall + secondBall;
  }

  public boolean isStrike() {
    return false;
  }

  public boolean isSpare() {
    return false;
  }

  public boolean isBonus() {
    return false;
  }

  public int getFirstBall() {
    return this.firstBall;
  }

  public int getSecondBall() {
    return this.secondBall;
  }


}
