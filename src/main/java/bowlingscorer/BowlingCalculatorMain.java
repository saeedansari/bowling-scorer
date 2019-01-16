package bowlingscorer;

import bowlingscorer.model.Bowling;
import bowlingscorer.util.LineParser;
import bowlingscorer.util.ScoreCalculator;
import java.util.Scanner;

public class BowlingCalculatorMain {


  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    final String lineString = sc.next();
    LineParser parser = new LineParser();
    final Bowling bowling = parser.parseLine(lineString);
    ScoreCalculator scoreCalculator = new ScoreCalculator();
    int score  = scoreCalculator.scoreBowling(bowling);
    System.out.println("Score is " + score);
  }



}
