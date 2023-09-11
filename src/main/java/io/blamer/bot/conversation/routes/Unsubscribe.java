package io.blamer.bot.conversation.routes;

import io.blamer.bot.conversation.Conversation;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import reactor.core.publisher.Mono;

import javax.naming.OperationNotSupportedException;

/*
 * @todo #73:45min\DEV Create tests for ChatId
 */
/**
 * Chat id command.
 */
@Component("/chatid")
public class Unsubscribe implements Conversation {

  /**
   * The command.
   */
  @Value("${answers.unsubscribe.command}")
  private String command;

  /**
   * Command description.
   */
  @Value("${answers.unsubscribe.description}")
  private String description;

  @Override
  public Mono<SendMessage> messageOf(final Update update) {
    return Mono.just(update)
      .map(upd -> upd.getMessage().getChatId())
      .doOnNext(Unsubscribe::forgetChat)
      .map(
        chat ->
          new SendMessage(
            String.valueOf(chat),
            "Saving your chat-id..."
          )
      );
  }

  @Override
  public BotCommand asBotCommand() {
    return new BotCommand(this.command, this.description);
  }

  /*
  * @todo #73:60min\DEV Removes chat
  *   When db logic will be implemented, chat-id should be removed from db
  * */
  /**
   * Stub.
   *
   * @param chat Chat to forgetChat
   */
  @SneakyThrows
  private static void forgetChat(final Long chat) {
    throw new OperationNotSupportedException("#forgetChat not implemented yet");
  }
}
