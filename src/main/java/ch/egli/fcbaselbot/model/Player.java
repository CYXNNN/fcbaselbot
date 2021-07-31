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
public class Player {

  @XmlAttribute(name = "shirt_nr")
  private int number;
  @XmlAttribute(name = "first_name")
  private String firstname;
  @XmlAttribute(name = "middle_name")
  private String middlename;
  @XmlAttribute(name = "last_name")
  private String lastname;
  @XmlAttribute(name = "nickname")
  private String nickname;
  @XmlAttribute(name = "position")
  private int position;
  @XmlAttribute(name = "formatted_date_of_birth")
  private String dateOfBirth;

  @XmlAttribute(name = "nationality")
  private String nationality;
  @XmlAttribute(name = "matches_played")
  private int matchesPlayed;
  @XmlAttribute(name = "goals")
  private int goals;
  @XmlAttribute(name = "assists")
  private int assists;
  @XmlAttribute(name = "yellow_cards")
  private int yellow;
  @XmlAttribute(name = "red_cards")
  private int red;
  @XmlAttribute(name = "held_shots")
  private int heldShots;
  @XmlAttribute(name = "shots")
  private int shots;
  @XmlAttribute(name = "minutesPlayed")
  private int minutesPlayed;
  @XmlAttribute(name = "substitutions")
  private int substitutions;
  @XmlAttribute(name = "substitutings")
  private int substitutings;

  public String getTitle() {
    return "[" + number + "] " + firstname + " " + lastname;
  }

  public String getBody() {
    return "Geburtsdatum: " + dateOfBirth + "\n" +
      "Nationalität: " + nationality + "\n" +
      "Gespielte Spiele (SL): " + matchesPlayed + "\n" +
      "Tore (SL): " + goals + "\n" +
      "Assists (SL): " + assists + "\n" +
      "Gelbe Karten (SL): " + yellow + "\n" +
      "Rote Karten (SL): " + red + "\n" +
      "Gespielte Minuten (SL): " + minutesPlayed + "\n";
  }
}