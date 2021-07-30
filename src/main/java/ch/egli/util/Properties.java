package ch.egli.util;

public class Properties {
  public static String DISCORD_TOKEN;
  public static String CALENDAR_URL;
  public static String REMINDER_HOOK;

  public Properties(String DISCORD_TOKEN,
    String CALENDAR_URL,
    String REMINDER_HOOK) {

    this.DISCORD_TOKEN = DISCORD_TOKEN;
    this.CALENDAR_URL = CALENDAR_URL;
    this.REMINDER_HOOK = REMINDER_HOOK;
  }
}
