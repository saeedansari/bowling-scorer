package bowlingscorer.model;

public class Spare extends Frame {


  public Spare(int firstBall, int secondBall) {
    super(firstBall, secondBall);
  }

  @Override
  public boolean isSpare() {
    return true;
  }
}
