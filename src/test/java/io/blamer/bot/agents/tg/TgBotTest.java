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

import annotation.TestWithSpringContext;
import io.blamer.bot.conversation.Conversation;
import io.blamer.bot.conversation.routes.Registry;
import io.blamer.bot.conversation.routes.Start;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@ExtendWith(MockitoExtension.class)
@TestWithSpringContext
class TgBotTest {

  @Autowired
  private Start start;
  @Autowired
  private ExtTg ext;

  /*
   * @todo #73:90min\DEV Refactor with custom extension
   *   All data in createsBotWithConfig
   *   should be replaced with custom ParameterResolver.
   */
  @Test
  void createsBotWithConfig(@Mock final ExtTg config) {
    final String token = "dummy token";
    final String name = "blamer";
    Mockito.when(config.getToken()).thenReturn(token);
    Mockito.when(config.getName()).thenReturn(name);
    final TgBot bot = new TgBot(config, Collections.emptyMap());
    MatcherAssert.assertThat(
      "Bot token in right format",
      bot.getBotToken(),
      Matchers.equalTo(token)
    );
    MatcherAssert.assertThat(
      "Bot name in right format",
      bot.getBotUsername(),
      Matchers.equalTo(name)
    );
  }

  /*
   * @todo #73:90min\DEV Refactor with custom extension
   *   All data in reactsOnUpdateReceived
   *   should be replaced with custom ParameterResolver.
   */
  @Test
  void reactsOnUpdateReceived(
    @Mock final Update update,
    @Mock final ExtTg ext
  ) {
    final TgBot bot = new TgBot(ext, Collections.emptyMap());
    Assertions.assertDoesNotThrow(() -> bot.onUpdateReceived(update));
  }

  /*
   * @todo #73:90min\DEV Refactor with custom extension
   *   All data in reactsOnUpdateWithMessage
   *   should be replaced with custom ParameterResolver.
   */
  @Test
  void reactsOnUpdateWithMessage(@Mock final ExtTg ext) {
    final TgBot bot = new TgBot(ext, Collections.emptyMap());
    final Update update = new Update();
    final Message message = new Message();
    message.setText("text");
    update.setMessage(message);
    Assertions.assertDoesNotThrow(() -> bot.onUpdateReceived(update));
  }

  /*
   * @todo #73:90min\DEV Refactor with custom extension
   *   All data in reactsOnUpdateWithConversations
   *   should be replaced with custom ParameterResolver.
   */
  @Test
  @Disabled
  void reactsOnUpdateWithConversations() {
    final Map<String, Conversation> conversations = new HashMap<>(1);
    conversations.put("/start", this.start);
    final TgBot bot = new TgBot(this.ext, conversations);
    final Update update = new Update();
    final Message message = new Message();
    final Chat chat = new Chat();
    final long id = 1L;
    chat.setId(id);
    message.setChat(chat);
    message.setMessageId(1);
    message.setText("/start");
    update.setMessage(message);
    Assertions.assertThrows(
      TelegramApiRequestException.class,
      () -> bot.onUpdateReceived(update),
      "Chat with id %s is not found"
        .formatted(id)
    );
  }
}