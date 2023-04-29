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

package io.blamer.bot.configuration;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.util.retry.Retry;

import java.time.Duration;

/**
 * RSocket config.
 */
@Slf4j
@Data
@Configuration
@ConfigurationProperties("hub")
public class RSocketConfiguration {

  /**
   * Number of reconnection attempts.
   */
  private static final int RECONNECT_ATTEMPTS = 3;

  /**
   * Connection timeout time.
   */
  private static final int CONNECT_TIMEOUT = 1;

  /**
   * Hub host.
   */
  private String host;

  /**
   * Hub port.
   */
  private int port;

  /**
   * Configure rsocket to Hub connection.
   *
   * @param builder Requester builder
   * @return Configured builder
   */
  @Bean
  public RSocketRequester rSocketRequester(
    final RSocketRequester.Builder builder
  ) {
    RSocketConfiguration.log.info(
      "Configuring connection to Hub[{}:{}]",
      this.host,
      this.port
    );
    return builder
      .rsocketConnector(
        connector ->
          connector.reconnect(
            Retry.fixedDelay(
              RSocketConfiguration.RECONNECT_ATTEMPTS,
              Duration.ofSeconds(RSocketConfiguration.CONNECT_TIMEOUT)
            )
          )
      )
      .dataMimeType(MediaType.APPLICATION_CBOR)
      .tcp(this.host, this.port);
  }
}
