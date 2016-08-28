package net.alexc.chat;

import net.alexc.chat.server.ChatServer;

import java.io.IOException;

public class ServerRunner {
  public static void main(String[] args) throws IOException {
    try {
      ChatServer server = new ChatServer();
      server.start();
    } catch (IOException e) {
      System.out.println("Could not initialize server.");
      e.printStackTrace();
    }
  }
}
