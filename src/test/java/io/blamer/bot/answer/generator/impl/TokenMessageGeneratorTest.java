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

package io.blamer.bot.answer.generator.impl;

import annotation.TestWithSpringContext;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

@TestWithSpringContext
@ExtendWith(MockitoExtension.class)
class TokenMessageGeneratorTest {

  @Autowired
  private TokenMessageGenerator tokenMessageGenerator;

  @Test
  void createsStartMessage(@Mock final Update update, @Mock final Message message) {
    Mockito.when(update.getMessage()).thenReturn(message);
    Assertions.assertNotNull(this.tokenMessageGenerator.messageFromUpdate(update));
    MatcherAssert.assertThat(
      "Response in context right text",
      this.tokenMessageGenerator.messageFromUpdate(update).getText(),
      Matchers.equalTo("test token message")
    );
  }

  @Test
  void createsDescription() {
    final BotCommand actual = this.tokenMessageGenerator.messageAsBotCommand();
    MatcherAssert.assertThat(actual.getCommand(), Matchers.equalTo("/token"));
    MatcherAssert.assertThat(
      actual.getDescription(),
      Matchers.equalTo("test token description")
    );
  }
}