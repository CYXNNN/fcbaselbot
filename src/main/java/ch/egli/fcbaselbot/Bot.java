package ch.egli.fcbaselbot;

import ch.egli.fcbaselbot.data.TableCrawler;
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

      if ("!siuve".equals(message.getContent())) {
        answer(message, "Der Mann aus dem Wald ist da..");
      }
    });

    gateway.onDisconnect().block();
  }

  private void answer(Message message, String answer) {
    final MessageChannel channel = message.getChannel().block();
    channel.createMessage(answer).block();
  }

  public void answerWithPing(Game answer) {

    TemmieWebhook hook = new TemmieWebhook(Properties.REMINDER_HOOK);
    DiscordEmbed de = new DiscordEmbed(answer.title(), answer.getDescription());
    de.setColor(5);
    DiscordMessage dm = new DiscordMessage();
    dm.setContent("@everyone a game is about to begin");
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
}
