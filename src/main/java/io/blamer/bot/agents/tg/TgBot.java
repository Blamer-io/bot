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

package io.blamer.bot.agents.tg;

import io.blamer.bot.conversation.Conversation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

/*
 * @todo #4 Find way to test this
 *   Need to find a proper way to test telegram bot logic.
 * */

/**
 * Telegram Bot.
 *
 * @author Ivan Ivanchuk (l3r8y@duck.com)
 * @author Aliaksei Bialiauski (abialiausi.dev@gmail.com)
 * @since 0.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TgBot extends TelegramLongPollingBot {

  /**
   * Configuration.
   */
  private final ExtTg configuration;

  /**
   * Conversations is a bean map, the key is the bean id,
   * the value is the Conversation bean implementation.
   * All beans that implement the Conversation interface
   * have a bean id equal to the text command it handles.
   * <p>
   * Spring automatically injects these fields,
   * allowing us to avoid gigantic <i>if/else</i>
   * or <i>switch/case</i> constructs.
   */
  private final Map<String, Conversation> conversations;

  /**
   * Initialize bot with set of commands.
   *
   * @throws TelegramApiException if fails.
   */
  @PostConstruct
  void initWithCommands() throws TelegramApiException {
    this.execute(
      new SetMyCommands(
        this.conversations.values()
          .stream()
          .map(Conversation::asBotCommand)
          .peek(cmd -> TgBot.log.info("defined {}", cmd))
          .toList(),
        new BotCommandScopeDefault(),
        "en"
      )
    );
  }

  @SneakyThrows
  @Override
  public void onUpdateReceived(final Update update) {
    if (update.hasMessage()) {
      final Conversation conversation =
        this.conversations.get(update.getMessage().getText().split(" ")[0]);
      if (null == conversation) {
        return;
      }
      final SendMessage message = conversation.messageFromUpdate(update);
      this.execute(message);
    }
  }

  @Override
  public String getBotUsername() {
    return this.configuration.getName();
  }

  @Override
  public String getBotToken() {
    return this.configuration.getToken();
  }
}
