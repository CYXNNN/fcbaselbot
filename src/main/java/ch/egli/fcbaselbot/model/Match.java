package ch.egli.fcbaselbot.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Match {

  @XmlAttribute( name = "matchday")
  private int matchday;
  @XmlAttribute( name = "status")
  private int status;
  @XmlElement(name="teams")
  private Teams teams;
  @XmlElement(name="arena")
  private Arena arena;
  @XmlElement(name="referee")
  private Referee referee;

  @XmlTransient
  public int getHomeScore() {
    return getTeams().getHome().getGoals().getTotal();
  }

  @XmlTransient
  public int getAwayScore() {
    return getTeams().getGuest().getGoals().getTotal();
  }


  @XmlTransient
  public boolean isBaselHome() {
    return getTeams().getHome().getName().equals("FC Basel 1893");
  }

}
