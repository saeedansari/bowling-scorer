package bowlingscorer.util

import bowlingscorer.model.Bowling
import bowlingscorer.model.BowlingConstants
import spock.lang.Specification

class LineParserSpecification extends Specification {

    LineParser lineParser


    def setup() {
        lineParser = new LineParser()
    }

    def "it should parse a frame with miss and strike"() {
        given:
            def frameStr = "-/"
        when:
            def frame = lineParser.toFrame(frameStr)
        then:
            frame.firstBall == 0
            frame.secondBall == BowlingConstants.STRIKE_SCORE
    }

    def "it should parse a strike frame "() {
        given:
            def frameStr = "X"
        when:
            def frame = lineParser.toFrame(frameStr)
        then:
            frame.firstBall == BowlingConstants.STRIKE_SCORE
            frame.secondBall == BowlingConstants.MISS_SCORE
    }

    def "it should parse a none strike/spare frame "() {
        given:
            def frameStr = "27"
        when:
            def frame = lineParser.toFrame(frameStr)
        then:
            frame.firstBall == 2
            frame.secondBall == 7
    }

    def "it should parse a frame with a miss of first ball "() {
        given:
            def frameStr = "-7"
        when:
            def frame = lineParser.toFrame(frameStr)
        then:
            frame.firstBall == BowlingConstants.MISS_SCORE
            frame.secondBall == 7
    }

    def "it should parse a frame with a miss of second ball "() {
        given:
            def frameStr = "8-"
        when:
            def frame = lineParser.toFrame(frameStr)
        then:
            frame.firstBall == 8
            frame.secondBall == BowlingConstants.MISS_SCORE
    }

    def "it should parse bonus with strike "() {
        given:
            def bonusStr = "X"
        when:
            def bonus = lineParser.toBonus(bonusStr)
        then:
            bonus.firstBall == BowlingConstants.STRIKE_SCORE
            bonus.secondBall == BowlingConstants.MISS_SCORE
    }

    def "it should parse bonus with second ball strike "() {
        given:
            def bonusStr = "-X"
        when:
            def bonus = lineParser.toBonus(bonusStr)
        then:
            bonus.firstBall == BowlingConstants.MISS_SCORE
            bonus.secondBall == BowlingConstants.STRIKE_SCORE
    }

    def "it should parse bonus none strike/spare "() {
        given:
            def bonusStr = "3"
        when:
            def bonus = lineParser.toBonus(bonusStr)
        then:
            bonus.firstBall == 3
            bonus.secondBall == BowlingConstants.MISS_SCORE
    }

    def "it should parse two bonus none strike/spare"() {
        given:
            def bonusStr = "63"
        when:
            def bonus = lineParser.toBonus(bonusStr)
        then:
            bonus.firstBall == 6
            bonus.secondBall == 3
    }


    def "it should parse all 10 frames with 1 bonus"() {
        given:
            def line = "X|X|X|X|X|X|X|X|X|X||XX"
        when:
            Bowling bowling = lineParser.parseLine(line)
        then:
            bowling.frames.size() == 11
            bowling.frames.each { it.firstBall == BowlingConstants.STRIKE_SCORE}
            bowling.frames.each { it.secondBall == BowlingConstants.MISS_SCORE}
    }

    def "it should parse all 10 frames without bonus"() {
        given:
            def line = "X|X|X|X|X|X|X|X|X|23||"
        when:
            Bowling bowling = lineParser.parseLine(line)
        then:
            bowling.frames.size() == 10
            bowling.frames.last().firstBall == 2
            bowling.frames.last().secondBall == 3
    }

    def "it should parse spare at frame 5"() {
        given:
            def line = "X|X|X|X|2/|X|X|X|X|23||"
        when:
            Bowling bowling = lineParser.parseLine(line)
        then:
            bowling.frames.size() == 10
            def fifthFrame = bowling.frames.get(4)
            fifthFrame.isSpare()
            fifthFrame.firstBall == 2
            fifthFrame.secondBall == BowlingConstants.SPARE_SCORE
    }

    def "it should parse miss at frame 10"() {
        given:
            def line = "X|X|X|X|2/|X|X|X|X|4-||"
        when:
            Bowling bowling = lineParser.parseLine(line)
        then:
            bowling.frames.size() == 10
            def lastFrame = bowling.frames.last()
            !lastFrame.isSpare()
            !lastFrame.isStrike()
            lastFrame.firstBall == 4
            lastFrame.secondBall == BowlingConstants.MISS_SCORE
    }

    def "it should parse spare at last frame"() {
        given:
        def line = "X|X|X|X|2/|X|X|X|X|4/||X"
        when:
        Bowling bowling = lineParser.parseLine(line)
        then:
        bowling.frames.size() == 11
        def lastFrame = bowling.frames.get(9)
        lastFrame.isSpare()
        !lastFrame.isStrike()
        lastFrame.firstBall == 4
        lastFrame.secondBall == BowlingConstants.SPARE_SCORE
        bowling.frames.last().firstBall == BowlingConstants.STRIKE_SCORE
        bowling.frames.last().secondBall == BowlingConstants.MISS_SCORE
    }

    def "it should parse line with misses"() {
        given:
            def line = "-4|7-|X|--|2/|X|X|X|X|4/||X"
        when:
            Bowling bowling = lineParser.parseLine(line)
        then:
            bowling.frames.size() == 11
            def firstFrame = bowling.frames.first()
            firstFrame.firstBall == 0
            firstFrame.secondBall == 4
    }



}
