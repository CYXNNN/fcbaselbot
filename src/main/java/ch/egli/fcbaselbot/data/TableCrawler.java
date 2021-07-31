package ch.egli.fcbaselbot.data;

import ch.egli.fcbaselbot.model.Season;
import ch.egli.fcbaselbot.model.Team;
import ch.egli.util.Crawling;
import ch.egli.util.Properties;
import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class TableCrawler implements Crawling {

  @Override
  public Object get() {
    String url = Properties.TABLE_URL;
    Season season = new Season();

    JAXBContext jaxbContext;
    try {
      jaxbContext = JAXBContext.newInstance(Season.class);
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
      var stream = new URL(url).openStream();

      season = (Season) jaxbUnmarshaller.unmarshal(stream);
    } catch (JAXBException | IOException e) {
      e.printStackTrace();
    }

    return season;
  }

  @Override
  public String pretty() {
    var season = (Season) get();

    return season.getLeague().getTeams().getTeam().stream().map(TableCrawler::toRankString).collect(Collectors.joining("\n"));
  }

  private static String toRankString(Team team){
    return team.getRank() + ". " + team.getName();
  }
}
