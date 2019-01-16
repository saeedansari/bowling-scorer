package bowlingscorer.model;

public class Strike extends Frame {

  public Strike(int firstBall, int secondBall) {
    super(firstBall, secondBall);
  }


  @Override
  public boolean isStrike() {
    return true;
  }

}
