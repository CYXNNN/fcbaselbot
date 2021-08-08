package ch.egli.fcbaselbot.data;

import ch.egli.fcbaselbot.model.Crawled;
import ch.egli.fcbaselbot.model.Season;
import ch.egli.util.Crawling;
import ch.egli.util.Properties;
import java.util.stream.Collectors;

public class PlayerCrawler extends XmlCrawler implements Crawling {

  private final static String URL = Properties.PLAYER_URL;

  @Override
  public Crawled get() {
    return crawl(URL, Season.class);
  }

  @Override
  public String pretty() {
    var season = (Season) get();
    var basel = season.getBasel();

    if(basel == null) {
      return "Ich habe keine Daten darüber :(";
    }

    return basel.getPlayers().getActive().getPlayer().stream()
      .map(p -> "**" +p.getNumber() + "** " + p.getFirstname() + " " + p.getLastname())
      .collect(Collectors.joining("\n"));
  }
}
