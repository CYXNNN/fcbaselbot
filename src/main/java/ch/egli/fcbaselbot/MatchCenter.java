package ch.egli.fcbaselbot;

import ch.egli.fcbaselbot.data.GameCrawler;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

public class MatchCenter {

  @Getter
  @Setter
  private List<Game> games;

  public MatchCenter() {
    this.games = new GameCrawler().toObject();
  }

  public Game next() {
    return games.stream()
      .filter(g -> g.getStart().after(new Date()))
      .findFirst()
      .orElse(null);
  }

  public Game last() {
    return games.stream()
      .filter(g -> g.getStart().before(new Date()))
      .reduce((first, second) -> second)
      .orElse(null);
  }

  public List<Game> upcoming() {
    return games.stream()
      .filter(g -> g.getStart().after(new Date()))
      .collect(Collectors.toList());
  }

  public String all() {
    return games.stream()
      .map(Game::title)
      .collect(Collectors.joining("\n\r"));
  }

}
