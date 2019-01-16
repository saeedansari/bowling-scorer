package bowlingscorer.model;

import java.util.List;

public class Bowling {

  private List<Frame> frames;

  public Bowling(final List<Frame> frames) {
    this.frames = frames;
  }

  public List<Frame> getFrames() {
    return frames;
  }
}
