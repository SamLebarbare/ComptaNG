/*
 * Copyright (c) 2013, Samuel Loup. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package controllers;

import play.*;
import play.mvc.*;
import play.libs.*;
import play.libs.F.*;
import play.mvc.Http.*;
import models.*;

import static play.libs.F.*;
import static play.libs.F.Matcher.*;
import static play.mvc.Http.WebSocketEvent.*;

public class ApplicationWS extends WebSocketController {

    public static void join(String user) 
    {

        ChatRoom room = ChatRoom.get();

        // Socket connected, join the chat room
        EventStream<ChatRoom.Event> roomMessagesStream = room.join(user);

        // Loop while the socket is open
        while (inbound.isOpen()) {

            // Wait for an event (either something coming on the inbound socket channel, or ChatRoom messages)
            Either<WebSocketEvent, ChatRoom.Event> e = await(Promise.waitEither(
                    inbound.nextEvent(),
                    roomMessagesStream.nextEvent()));

            // Case: User typed 'quit'
            for (String userMessage : TextFrame.and(Equals("quit")).match(e._1)) {
                room.leave(user);
                outbound.send("quit:ok");
                disconnect();
            }

            // Case: TextEvent received on the socket
            for (String userMessage : TextFrame.match(e._1)) {
                room.say(user, userMessage);
            }

            // Case: Someone joined the room
            for (ChatRoom.Join joined : ClassOf(ChatRoom.Join.class).match(e._2)) {
                outbound.send("join:%s", joined.user);
            }

            // Case: New message on the chat room
            for (ChatRoom.Message message : ClassOf(ChatRoom.Message.class).match(e._2)) {
                outbound.send("message:%s:%s", message.user, message.text);
            }

            // Case: Someone left the room
            for (ChatRoom.Leave left : ClassOf(ChatRoom.Leave.class).match(e._2)) {
                outbound.send("leave:%s", left.user);
            }

            // Case: The socket has been closed
            for (WebSocketClose closed : SocketClosed.match(e._1)) {
                room.leave(user);
                disconnect();
            }

        }

    }
}
