package ch.egli.fcbaselbot;

import ch.egli.fcbaselbot.data.LiveCrawler;
import ch.egli.fcbaselbot.model.Season;
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

  public void tick(Game game) throws InterruptedException {
    var current = (Season) new LiveCrawler().get();
    var baselGame = current.getBaselCurrentGame();

    if (baselGame == null) {
      return;
    }

    var gameRunning = true;

    // aslong as the game is not finished
    while (gameRunning) {

      var status = baselGame.getStatus();
      var home = baselGame.getHomeScore();
      var guest = baselGame.getAwayScore();

      baselGame = ((Season) new LiveCrawler().get()).getBaselCurrentGame();

      if (status != baselGame.getStatus()) {
        switch (baselGame.getStatus()) {
          case 1:
            bot.answerWithPing("@everyone", "Game started!", "Referee is "+baselGame.getReferee().getFirst_name()+ " " +baselGame.getReferee().getLast_name());
            break;

          case 2:
            bot.answerWithPing("@everyone", "Second half started!", "");
            break;

          case 6:
            bot.answerWithPing("@everyone", "First half is over!", "");
            break;
          case 100:
            gameRunning = false;
            break;

          default: break;
        }
      }

      if (home < baselGame.getHomeScore()) {
        if (baselGame.isBaselHome()){
          bot.answerWithPing("@everyone", "FC BASEL SCORED!!!", "");
        } else {
          bot.answerWithPing("@everyone", "The other fags scored :(", "");
        }
      }

      if (guest < baselGame.getAwayScore()) {
        if (!baselGame.isBaselHome()){
          bot.answerWithPing("@everyone", "FC BASEL SCORED!!!", "");
        } else {
          bot.answerWithPing("@everyone", "The other fags scored :(", "");
        }
      }

      if (!gameRunning) {
        bot.answerWithPing("@everyone", "Game is over!", "");
      }

      Thread.sleep(30000); // Wait 10 seconds
    }
  }

  public void schedule() {
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(4);

    matchCenter.upcoming().stream()
      .forEach(g -> {
        var delay = (g.getStart().getTime() - 1800 * 1000) - System.currentTimeMillis();
        var delayTick = (g.getStart().getTime() - 60 * 1000) - System.currentTimeMillis();
        ses.schedule( () -> bot.answerWithPing(g), delay, TimeUnit.MILLISECONDS);
        ses.schedule( () -> {
          try {
            tick(g);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }, delayTick, TimeUnit.MILLISECONDS);
      });

    ses.shutdown();
  }
}
