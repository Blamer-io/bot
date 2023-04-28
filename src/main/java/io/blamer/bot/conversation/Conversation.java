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

package io.blamer.bot.conversation;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

/**
 * This interface created for solution of infinity switch-case.
 * <pre>{@code
 * @Component("/example")
 * public class Example implements Conversation {
 *      @Override
 *      public SendMessage messageFromUpdate(final Update update) {
 *          final SendMessage sendMessage = new SendMessage();
 *          sendMessage.setChatId(update.getMessage().getChatId());
 *          sendMessage.setText("Text for message");
 *          return sendMessage;
 *      }
 *
 *      @Override
 *      public BotCommand messageAsBotCommand() {
 *          return new BotCommand("/example", "Description for example command")
 *      }
 * }
 * }</pre>
 * implNote All implementations of this Interface <b>have to</b>
 * be annotated with <code>@Component("/command")</code> annotation.
 * Otherwise, it's just won't add a new command handling.
 */
public interface Conversation {

    /**
     * Creates message to handle with specific command.
     *
     * @param update The update to handle
     * @return SendMessage event
     */
    SendMessage messageFromUpdate(Update update);

    /**
     * @return Description of this command.
     */
    BotCommand messageAsBotCommand();
}
