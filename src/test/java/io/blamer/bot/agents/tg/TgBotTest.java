package io.blamer.bot.agents.tg;

import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@ExtendWith(MockitoExtension.class)
class TgBotTest {

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

  @Test
  void reactsOnUpdateReceived(@Mock final Update update,
                              @Mock final ExtTg ext) {
    final TgBot bot = new TgBot(ext, Collections.emptyMap());
    Assertions.assertDoesNotThrow(() -> bot.onUpdateReceived(update));
  }

  @Test
  void reactsOnUpdateWithMessage(@Mock final ExtTg ext) {
    final TgBot bot = new TgBot(ext, Collections.emptyMap());
    Update update = new Update();
    final Message message = new Message();
    message.setText("text");
    update.setMessage(message);
    Assertions.assertDoesNotThrow(() -> bot.onUpdateReceived(update));
  }
}