import ch.egli.fcbaselbot.Bot;
import ch.egli.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

  public static void main(String[] args) {

    Dotenv dotenv = Dotenv.load();
    new Properties(
      dotenv.get("DISCORD_TOKEN"),
      dotenv.get("CALENDAR_URL")
    );
    new Bot();
  }
}
