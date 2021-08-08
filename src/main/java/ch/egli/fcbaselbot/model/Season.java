package ch.egli.fcbaselbot.model;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "season")
public class Season extends Crawled {
  private League league;

  public List<Player> findPlayers() {
    return this.getLeague().getTeams().getTeam().get(0).getPlayers().getActive().getPlayer();
  }

  public List<Team> getTeams() {
    return getLeague().getTeams().getTeam();
  }

  public Team getBasel() {
    return getLeague().getTeams().getTeam().stream().findFirst().orElse(null);
  }

  public Match getBaselCurrentGame() {
    return getLeague().getMatches().getMatch().stream()
      .filter(m -> m.getTeams().getHome().getName().equals("FC Basel 1893") || m.getTeams().getGuest().getName().equals("FC Basel 1893"))
      .findFirst()
      .orElse(null);
  }
}
