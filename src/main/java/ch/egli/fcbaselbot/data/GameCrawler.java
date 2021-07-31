package ch.egli.fcbaselbot.data;

import ch.egli.fcbaselbot.Game;
import ch.egli.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.DtStart;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.Summary;

public class GameCrawler {

  private Calendar games;

  public GameCrawler() {

    try {
      init();
    } catch (IOException | ParserException e) {
      e.printStackTrace();
    }
  }

  public void init() throws IOException, ParserException {
    InputStream is = new URL(Properties.CALENDAR_URL).openStream();

    try {
      this.games = new CalendarBuilder().build(is);
    } finally {
      is.close();
    }

  }

  public List<Game> toObject() {
    return this.games.getComponents().stream()
      .filter(c -> c.getName().equals("VEVENT"))
      .map(GameCrawler::icsToGame)
      .sorted(Comparator.comparing(Game::getStart))
      .collect(Collectors.toList());
  }


  public static Game icsToGame(CalendarComponent component){
    var game = new Game();
    Summary summary = component.getProperty("summary");
    DtStart start = component.getProperty("dtstart");
    Location loc = component.getProperty("location");
    Description desc = component.getProperty("description");

    var parts = parseTeamName(summary.getValue());

    game.setHome(parts[0]);
    game.setAway(parts[1]);
    game.setLocation(loc == null ? "" : loc.getValue());
    game.setDescription(desc == null ? "" : desc.getValue());
    game.setStart(start.getDate());
    return game;
  }

  private static String[] parseTeamName(String input) {
    var parts = new String[2];

    boolean fcb = input.endsWith("- FCB");
    boolean fcbasel = input.endsWith("- FC Basel");
    int chars = input.contains("FC Basel") ? 11 : 6;

    if (fcb || fcbasel) {
      parts[0] = input.substring(0, input.length() - chars);
      parts[1] = "FC Basel";
    } else {
      parts[0] = "FC Basel";
      parts[1] = input.substring(chars);
    }

    return parts;
  }
}
