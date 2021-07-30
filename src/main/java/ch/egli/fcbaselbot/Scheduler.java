package ch.egli.fcbaselbot;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

  private Bot bot;
  private MatchCenter matchCenter;

  public Scheduler(Bot bot, MatchCenter matchCenter) {
    this.bot = bot;
    this.matchCenter = matchCenter;
    schedule();
  }

  public void schedule() {
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

    matchCenter.upcoming().stream()
      .forEach(g -> {
        var delay = (g.getStart().getTime() - 3600 * 100) - System.currentTimeMillis();
        ses.schedule( () -> bot.answerWithPing(g), delay, TimeUnit.SECONDS);
      });

    ses.shutdown();
  }
}
