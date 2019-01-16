package bowlingscorer.util;


import bowlingscorer.model.BowlingConstants;
import bowlingscorer.model.Bonus;
import bowlingscorer.model.Bowling;
import bowlingscorer.model.Frame;
import bowlingscorer.model.Spare;
import bowlingscorer.model.Strike;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LineParser {

  private final String FRAME_SEPARATOR = "\\|";
  private final String BONUS_SEPARATOR = "\\|\\|";
  private final int SECOND_BALL = 1;
  private final int FIRST_BALL = 0;


  public Bowling parseLine(String lineString) {
    final String[] line = lineString.split(this.BONUS_SEPARATOR);
    final String[] frameIndicators = line[0].split(this.FRAME_SEPARATOR);
    final List<Frame> frames = Arrays.stream(frameIndicators).map(frame -> toFrame(frame))
        .collect(Collectors.toList());

    if (line.length > 1) {
      frames.add(toBonus(line[1]));
    }

    return new Bowling(frames);

  }

  private Frame toFrame(String frameStr) {
    if (frameStr.equals(String.valueOf(BowlingConstants.STRIKE_INDICATOR))) { //STRIKE
      return new Strike(BowlingConstants.STRIKE_SCORE, BowlingConstants.MISS_SCORE);
    }
    else if (frameStr.charAt(SECOND_BALL) == BowlingConstants.SPARE_INDICATOR) { //SPARE

      int firstBallScore;
      if (isMiss(frameStr.charAt(FIRST_BALL))) {
        firstBallScore = BowlingConstants.MISS_SCORE;
      } else {
        firstBallScore = scoreBall(frameStr.charAt(FIRST_BALL));
      }

      return new Spare(firstBallScore, BowlingConstants.SPARE_SCORE);
    }

    //none strike/spare frame
    int firstBall =
        isMiss(frameStr.charAt(FIRST_BALL)) ? BowlingConstants.MISS_SCORE : scoreBall(frameStr.charAt(FIRST_BALL));

    int secondBall =
        isMiss(frameStr.charAt(SECOND_BALL)) ? BowlingConstants.MISS_SCORE : scoreBall(frameStr.charAt(SECOND_BALL));

    return new Frame(firstBall, secondBall);
  }

  private Bonus toBonus(String bonusStr) {
    int firstBonus = scoreBall(bonusStr.charAt(FIRST_BALL));
    int secondBonus = 0;
    if (bonusStr.length() > 1) {
      secondBonus = scoreBall(bonusStr.charAt(SECOND_BALL));
    }
    return new Bonus(firstBonus, secondBonus);
  }

  private int scoreBall(char indicator) {
    switch (indicator) {

      case BowlingConstants.STRIKE_INDICATOR:
        return BowlingConstants.STRIKE_SCORE;

      case BowlingConstants.SPARE_INDICATOR:
        return BowlingConstants.SPARE_SCORE;

      case BowlingConstants.MISS_INDICATOR:
        return BowlingConstants.MISS_SCORE;

      default:
        return Character.getNumericValue(indicator);
    }

  }

  private boolean isMiss(char indicator) {
    return indicator == BowlingConstants.MISS_INDICATOR;
  }


}
