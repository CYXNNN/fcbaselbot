package ch.egli.fcbaselbot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Game {

  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

  private String home;
  private String away;
  private String location;
  private String description;

  private Date start;

  private int scoreHome;
  private int scoreAway;

  List<String> scorer;

  public String title() {
    var t = "[❤️💙] " + DATE_FORMAT.format(start) + ": " + home + " vs. " + away;

    if (location.length() > 0) {
      t += " (" + location + ")";
    }

    return t;
  }

  public String pretty() {
    return title() + "\n" + description;
  }

}
