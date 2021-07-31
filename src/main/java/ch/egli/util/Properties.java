package ch.egli.util;

public class Properties {
  public static String DISCORD_TOKEN;
  public static String CALENDAR_URL;
  public static String REMINDER_HOOK;
  public static String TABLE_URL;
  public static String PLAYER_URL;

  public Properties(String DISCORD_TOKEN,
    String CALENDAR_URL,
    String REMINDER_HOOK,
    String TABLE_URL,
    String PLAYER_URL) {

    this.DISCORD_TOKEN = DISCORD_TOKEN;
    this.CALENDAR_URL = CALENDAR_URL;
    this.REMINDER_HOOK = REMINDER_HOOK;
    this.TABLE_URL = TABLE_URL;
    this.PLAYER_URL = PLAYER_URL;
  }
}
