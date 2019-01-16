package bowlingscorer.util;

import bowlingscorer.model.Bowling;
import bowlingscorer.model.BowlingConstants;
import bowlingscorer.model.Frame;
import java.util.List;

public class ScoreCalculator {

  public ScoreCalculator() {
  }

  public int scoreBowling(Bowling bowling) {
    final List<Frame> frames = bowling.getFrames();
    final Frame[] allFrames = frames.toArray(new Frame[frames.size()]);
    int totalScore = 0;
    for (int i = 0; i < allFrames.length; i++) {
      totalScore += scoreFrame(allFrames, i);
    }
    return totalScore;
  }

  public int scoreNextTwoBalls(Frame[] frames, int currentFrame) {
    final Frame firstFrame = frames[currentFrame + 1];
    if (firstFrame.isStrike()) {
      return BowlingConstants.STRIKE_SCORE + frames[currentFrame + 2].getFirstBall();
    } else if (firstFrame.isSpare()) {
      return BowlingConstants.SPARE_SCORE;
    }
    return firstFrame.getTotalKnockedPins();
  }


  private int scoreFrame(Frame[] frames, int currentFrame) {
    if (frames[currentFrame].isBonus()) {
      return BowlingConstants.MISS_SCORE;
    }
    if (frames[currentFrame].isStrike()) {
      return BowlingConstants.STRIKE_SCORE + scoreNextTwoBalls(frames, currentFrame);
    } else if (frames[currentFrame].isSpare()) {
      return BowlingConstants.SPARE_SCORE + frames[currentFrame + 1].getFirstBall();
    } else {
      return frames[currentFrame].getTotalKnockedPins();
    }
  }


}
