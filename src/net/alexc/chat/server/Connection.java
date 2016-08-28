package net.alexc.chat.server;

import net.alexc.chat.messages.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class Connection implements Runnable {
  private final Socket socket;
  private final ChatServer server;

  Connection(Socket socket, ChatServer server) {
    this.socket = socket;
    this.server = server;
  }

  @Override
  public void run() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
    ) {
      String username = null;
      while (true) {
        Message messageFromClient = new Message(reader.readLine());
        if (username == null && messageFromClient.isNameMessage()) {
          String usernameAttempt = messageFromClient.getUserName();
          if (server.addUser(usernameAttempt, writer)) {
            username = usernameAttempt;
            writer.println("Welcome, " + username + ".");
            server.distributeMessage(username, "joined the chat.");
          } else {
            writer.println("Duplicate name. Try again.");
          }
        } else if (messageFromClient.isExitMessage()) {
          server.removeUser(username);
          server.distributeMessage(username, "left the chat.");
          break;
        } else {
          server.distributeMessage(username, messageFromClient.getMessageString());
        }
      }
    } catch (IOException e) {
      System.out.println("Could not initialize server reader/writer.");
      e.printStackTrace();
    }
  }
}
