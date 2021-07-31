package ch.egli.fcbaselbot.data;

import ch.egli.fcbaselbot.model.Crawled;
import ch.egli.fcbaselbot.model.Season;
import ch.egli.fcbaselbot.model.Team;
import ch.egli.util.Crawling;
import ch.egli.util.Properties;
import java.util.stream.Collectors;

public class TableCrawler extends XmlCrawler implements Crawling {

  private final static String URL = Properties.TABLE_URL;

  @Override
  public Crawled get() {
    return crawl(URL, Season.class);
  }

  @Override
  public String pretty() {
    var season = (Season) get();
    var teams = season.getTeams();
    return teams.stream()
      .map(TableCrawler::toRankString)
      .collect(Collectors.joining("\n"));
  }

  private static String toRankString(Team team){
    return team.getRank() + ". " + team.getName();
  }
}
