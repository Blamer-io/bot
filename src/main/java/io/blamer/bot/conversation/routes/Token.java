/*
 * MIT License
 *
 * Copyright (c) 2023 Blamer.io
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
import io.blamer.bot.response.RegistryResponse;
import io.blamer.bot.request.RegistryRequest;
import io.blamer.bot.text.TokenOf;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import reactor.core.publisher.Mono;

/**
 * Registry conversation.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @since 0.0.0
 */
@Slf4j
@Component("/token")
@RequiredArgsConstructor
@PropertySource("classpath:answers.properties")
public class Token implements Conversation {

  /**
   * The command.
   */
  @Value("${answers.token.command}")
  private String command;

  /**
   * Command description.
   */
  @Value("${answers.token.description}")
  private String description;

  /*
  * @todo #73:45min\DEV Replace RSocket
  *   We should replace rsocket with webclient/feign
  * */
  /**
   * The requester.
   */
  private final RSocketRequester requester;

  @Override
  public Mono<SendMessage> messageOf(final Update update) {
    final String chat = String.valueOf(update.getMessage().getChatId());
    try {
      return this.requester
        .route("hub.auth")
        .data(
          new RegistryRequest(
            new TokenOf(update).asString(),
            chat
          )
        )
        .retrieveMono(RegistryResponse.class)
        .map(auth -> new SendMessage(auth.getChat(), auth.getText()));
    } catch (final RuntimeException ex) {
      final SendMessage error = new SendMessage(
        chat,
        "`%s`".formatted(ex.getMessage())
      );
      error.enableMarkdown(true);
      return Mono.just(error);
    }
  }

  @Override
  public BotCommand asBotCommand() {
    return new BotCommand(this.command, this.description);
  }
}
