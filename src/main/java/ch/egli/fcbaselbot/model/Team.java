package ch.egli.fcbaselbot.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Team {
  @XmlAttribute( name = "xid")
  private int id;
  @XmlAttribute( name = "rank")
  private int rank;
  @XmlAttribute( name = "name")
  private String name;
  @XmlAttribute( name = "games_played")
  private int games;
  @XmlAttribute( name = "wins")
  private int wins;
  @XmlAttribute( name = "draws")
  private int draws;
  @XmlAttribute( name = "loss")
  private int loss;
  @XmlAttribute( name = "goals_shot")
  private int goals_shot;
  @XmlAttribute( name = "goals_obtained")
  private int goals_obtained;
  @XmlAttribute( name = "goal_difference")
  private int goal_difference;
  @XmlAttribute( name = "points")
  private int points;

  private Players players;


}
