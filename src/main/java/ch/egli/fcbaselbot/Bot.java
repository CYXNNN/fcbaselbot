package ch.egli.fcbaselbot;

import ch.egli.fcbaselbot.data.PlayerCrawler;
import ch.egli.fcbaselbot.data.TableCrawler;
import ch.egli.fcbaselbot.model.Player;
import ch.egli.fcbaselbot.model.Season;
import ch.egli.util.Properties;
import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;

public class Bot {

  private final MatchCenter center = new MatchCenter();
  private final DiscordClient client = DiscordClient.create(Properties.DISCORD_TOKEN);
  private final GatewayDiscordClient gateway = client.login().block();

  public Bot() {
    new Scheduler(this, center);

    gateway.on(MessageCreateEvent.class).subscribe(event -> {
      final var message = event.getMessage();

      if ("!next".equals(message.getContent())) {
        answer(message, center.next());
      }

      if ("!last".equals(message.getContent())) {
        answer(message, center.last());
      }

      if ("!all".equals(message.getContent())) {
        answer(message, center.all());
      }

      if ("!standings".equals(message.getContent())) {
        var table = new TableCrawler().pretty();
        answer(message, table);
      }

      if (message.getContent().contains("siuve")) {
        answer(message, "Der Mann aus dem Wald ist da...");
      }

      if ("!players".equals(message.getContent())) {
        var players = new PlayerCrawler().pretty();
        answer(message, players);
      }

      if (message.getContent().startsWith("!player ")) {

        var number = Integer.valueOf(message.getContent().replace("!player ", ""));
        var season = (Season) new PlayerCrawler().get();

        var players = season.findPlayers();

        var player = players.stream()
          .filter(p -> p.getNumber() == number)
          .findFirst()
          .orElse(null);

        if (player == null) {
          answer(message, "De Spilo hemmo nid du Banane!");
        } else {
          answer(message, player);
        }

      }

      if ("???".equals(message.getContent())) {
        var actions = "!next -> when is the next game?\n!last -> details on the last played game\n!all -> so far fixed game plan for the season\n!standings -> current standing for the Credit Suisse Super League\n!player -> which players are playing for FC Basel (you should know...)\n!player {number} -> gives u details about the player with the given number";
        answer(message, actions);
      }
    });

    gateway.onDisconnect().block();
  }

  private void answer(Message message, String answer) {
    final MessageChannel channel = message.getChannel().block();
    channel.createMessage(answer).block();
  }

  public void answerWithPing(Game answer) {
    answerWithPing(answer, "@everyone a game is about to begin");
  }

  public void answerWithPing(Game answer, String message) {

    TemmieWebhook hook = new TemmieWebhook(Properties.REMINDER_HOOK);
    DiscordEmbed de = new DiscordEmbed(answer.title(), answer.getDescription());
    de.setColor(5);
    DiscordMessage dm = new DiscordMessage();
    dm.setContent(message);
    dm.getEmbeds().add(de);

    hook.sendMessage(dm);
  }

  public void answerWithPing(String message, String title, String desc) {
    TemmieWebhook hook = new TemmieWebhook(Properties.REMINDER_HOOK);
    DiscordEmbed de = new DiscordEmbed(title, desc);
    de.setColor(5);
    DiscordMessage dm = new DiscordMessage();
    dm.setContent(message);
    dm.getEmbeds().add(de);

    hook.sendMessage(dm);
  }

  private void answer(Message message, Game answer) {
    final MessageChannel channel = message.getChannel().block();

    channel.createEmbed( spec ->
      spec.setColor(Color.GREEN)
        .setTitle(answer.title())
        .setDescription(answer.getDescription())
    ).block();
  }

  private void answer(Message message, Player player) {
    final MessageChannel channel = message.getChannel().block();

    channel.createEmbed( spec ->
      spec.setColor(Color.GREEN)
        .setTitle(player.getTitle())
        .setDescription(player.getBody())
    ).block();
  }
}
