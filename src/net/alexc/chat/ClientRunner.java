package net.alexc.chat;

import net.alexc.chat.client.ChatClient;

import java.io.IOException;

public class ClientRunner {
  public static void main(String[] args) {
    try {
      ChatClient client = new ChatClient();
      client.start();
    } catch (IOException e) {
      System.out.println("Couldn't initialize the client socket.");
      e.printStackTrace();
    }
  }
}
