package net.alexc.chat.messages;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageHandler implements Runnable {
  private final BufferedReader reader;
  private boolean running;

  public MessageHandler(BufferedReader reader) {
    running = true;
    this.reader = reader;
  }

  public void terminate() {
    running = false;
  }

  @Override
  public void run() {
    while (running) {
      try {
        String inputLine = reader.readLine();
        if (inputLine != null) {
          System.out.println(inputLine);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}