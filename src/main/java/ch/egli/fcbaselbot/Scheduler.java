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
      .map(this::createRunnable)
      .forEach(r -> {
        ses.schedule(r, 5, TimeUnit.SECONDS);
      });

    ses.shutdown();
  }

  public Runnable createRunnable(final Game game) {
    // TODO maybe this could be a good entry for live feed
    return () -> bot.answerWithPing(game);
  }
}
