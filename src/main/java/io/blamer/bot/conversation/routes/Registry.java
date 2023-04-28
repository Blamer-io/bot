/*
 * MIT License
 *
 * Copyright (c) Copyright (c) 2023 Blamer.io
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.blamer.bot.conversation.routes;

import io.blamer.bot.conversation.Conversation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

/**
 * Registry conversation.
 */
@Slf4j
@Component("/registry")
@PropertySource("classpath:answers.properties")
public class Registry implements Conversation {

  /**
   * The command.
   */
  @Value("${answers.registry.command}")
  private String command;

  /**
   * Command description.
   */
  @Value("${answers.registry.description}")
  private String description;

  /*
   * @todo #19 After hub service will be created, redirect the token.
   *   Send auth message to hub, to process the data.
   * */
  @Override
  public SendMessage messageFromUpdate(final Update update) {
    SendMessage result;
    final String text = update.getMessage().getText();
    final String chat = String.valueOf(
      update.getMessage().getChatId()
    );
    try {
      final String token = text.split(" ")[1];
      result = resultFrom(chat, token);
    } catch (final ArrayIndexOutOfBoundsException ex) {
      result = resultWithError(text, chat);
    }
    return result;
  }

  @Override
  public BotCommand messageAsBotCommand() {
    return new BotCommand(this.command, this.description);
  }

  /**
   * The resultFrom function takes a chat and token as parameters,
   * then returns a SendMessage object with the chat ID and message text.
   *
   * @param chat  Send the message to a specific chat
   * @param token Pass the token to the resultfrom function
   * @return A SendMessage object
   */
  private static SendMessage resultFrom(
    final String chat,
    final String token
  ) {
    return new SendMessage(
      chat,
      "Registry token doesn't available yet, sorry. Your token: '%s'"
        .formatted(token)
    );
  }

  /**
   * The resultWithError function is a helper function that
   * returns a SendMessage object with the
   * text &quot;Token not found in %s&quot;.formatted(text)
   * and the chat ID of chat.
   * <p>
   *
   * @param text Provide the user with a message about what went wrong
   * @param chat Specify the chat id of the user that sent the message
   * @return A SendMessage object
   */
  private static SendMessage resultWithError(
    final String text,
    final String chat
  ) {
    final String notFound = "Token not found in %s".formatted(text);
    Registry.log.debug(notFound);
    return new SendMessage(chat, notFound);
  }
}
