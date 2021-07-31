package ch.egli.fcbaselbot;

import ch.egli.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

  public static void main(String[] args) {

    Dotenv dotenv = Dotenv.load();
    new Properties(
      dotenv.get("DISCORD_TOKEN"),
      dotenv.get("CALENDAR_URL"),
      dotenv.get("REMINDER_HOOK"),
      dotenv.get("TABLE_URL")
    );
    new Bot();
  }
}
