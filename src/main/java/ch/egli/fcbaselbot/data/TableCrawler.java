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
    var res = "Rang. Team - Punkte (Spiele/Win/Draw/Loss) GD\n";
    res += "------------------------------------------\n";

    res += teams.stream()
      .map(TableCrawler::toRankString)
      .collect(Collectors.joining("\n"));

    return res;
  }

  private static String toRankString(Team team){

    var row = "";
    var isBasel = team.getName().equals("FC Basel 1893");
    var posGoal = team.getGoal_difference() > 0 ? "+" : "";

    if(isBasel) {
      row += "**";
    }

    row += team.getRank() + ". " + team.getName() + " - " + team.getPoints() +
      " Punkte (" + team.getGames() + "/" + team.getWins() + "/" + team.getDraws() +
      "/" + team.getLoss() + ") " + posGoal + team.getGoal_difference();

    if(isBasel) {
      row += "**";
    }

    return row;
  }
}
