package ch.egli.fcbaselbot;

import ch.egli.util.Properties;
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
    });

    gateway.onDisconnect().block();
  }

  private void answer(Message message, String answer) {
    final MessageChannel channel = message.getChannel().block();
    channel.createMessage(answer).block();
  }

  private void answerWithPing(Message message, Game answer) {
    final MessageChannel channel = message.getChannel().block();

    channel.createMessage( msg ->
      msg.setContent("@everyone")
        .addEmbed(spec ->
          spec.setColor(Color.RED)
            .setTitle(answer.title())
            .setDescription(answer.getDescription())
        )
    ).block();
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
