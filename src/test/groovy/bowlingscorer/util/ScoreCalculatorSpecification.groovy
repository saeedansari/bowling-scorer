package bowlingscorer.util

import bowlingscorer.model.Frame
import spock.lang.Specification

class ScoreCalculatorSpecification extends Specification {

    ScoreCalculator scoreCalculator
    LineParser lineParser

    def setup() {
        scoreCalculator = new ScoreCalculator()
        lineParser = new LineParser()
    }

    def "it should score next two balls when they are strikes"() {
        given:
            def frame1 = new bowlingscorer.model.Strike(bowlingscorer.model.BowlingConstants.STRIKE_SCORE, bowlingscorer.model.BowlingConstants.MISS_SCORE)
            def frame2 = new bowlingscorer.model.Strike(bowlingscorer.model.BowlingConstants.STRIKE_SCORE, bowlingscorer.model.BowlingConstants.MISS_SCORE)
            Frame[] frames = [frame1, frame2]
        when:
            int score = scoreCalculator.scoreNextTwoBalls(frames, -1)
        then:
            score == 20
    }

    def "it should score next two balls when next frame is spare"() {
        given:
            def frame1 = new bowlingscorer.model.Spare(bowlingscorer.model.BowlingConstants.STRIKE_SCORE, bowlingscorer.model.BowlingConstants.MISS_SCORE)
            Frame[] frames = [frame1]
        when:
            int score = scoreCalculator.scoreNextTwoBalls(frames, -1)
        then:
            score == 10
    }

    def "it should score next two balls neither strike nor spare"() {
        given:
            def frame1 = new Frame(3, 5)
            Frame[] frames = [frame1]
        when:
            int score = scoreCalculator.scoreNextTwoBalls(frames, -1)
        then:
            score == 8
    }

    def "it should score next two balls with miss and spare"() {
        given:
            def frame1 = new bowlingscorer.model.Spare(bowlingscorer.model.BowlingConstants.MISS_SCORE, bowlingscorer.model.BowlingConstants.SPARE_SCORE)
            Frame[] frames = [frame1]
        when:
            int score = scoreCalculator.scoreNextTwoBalls(frames, -1)
        then:
            score == 10
    }

    def "it should score all spares"() {
        given:
            def line = "5/|5/|5/|5/|5/|5/|5/|5/|5/|5/||5"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 150
    }

    def "it should score spare after strike"() {
        given:
            def line = "X|7/|9-|X|-8|8/|-6|X|X|X||81"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 167
    }

    def "it should score all strike"() {
        given:
            def line = "X|X|X|X|X|X|X|X|X|X||XX"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 300
    }

    def "it should score all frames with misses"() {
        given:
            def line = "9-|9-|9-|9-|9-|9-|9-|9-|9-|9-||"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 90
    }

    def "it should score all frames correctly"() {
        given:
            def line = "8/|7/|34|X|2/|X|X|8-|X|8/||9"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 170
    }

    def "it should score all frames miss"() {
        given:
            def line = "--|--|--|--|--|--|--|--|--|--||"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 0
    }

    def "it should score all frames miss and spare"() {
        given:
            def line = "-/|-/|-/|-/|-/|-/|-/|-/|-/|-/||/"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 110
    }

    def "it should score all strike and misses bonus"() {
        given:
            def line = "X|X|X|X|X|X|X|X|X|X||--"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 270
    }

    def "it should score all strike and bonus"() {
        given:
            def line = "X|X|X|X|X|X|X|X|X|X||22"
            def bowling = lineParser.parseLine(line)
        when:
            def score = scoreCalculator.scoreBowling(bowling)
        then:
            score == 276
    }


}
