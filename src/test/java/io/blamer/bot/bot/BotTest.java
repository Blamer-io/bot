package io.blamer.bot.bot;

import io.blamer.bot.configuration.BotConfiguration;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BotTest {

  @Test
  void createsBotWithConfig(@Mock final BotConfiguration config) {
    final String token = "dummy token";
    Mockito.when(config.getToken()).thenReturn(token);
    final Bot bot = new Bot(config, null);
    MatcherAssert.assertThat(
      "Token in right format",
      bot.getBotToken(),
      Matchers.equalTo(token)
    );
  }
}