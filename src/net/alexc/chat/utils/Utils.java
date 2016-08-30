package net.alexc.chat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {
  public static int readInteger(String message) {
    boolean found = false;
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    Integer port = null;

    while (!found) {
      System.out.printf(message);
      try {
        String line = stdIn.readLine();
        port = Integer.parseInt(line);
        found = true;
      } catch (IOException e) {
        System.out.println("Could not read line!");
        e.printStackTrace();
      } catch (NumberFormatException e) {
        System.out.println("Could not parse number!");
        e.printStackTrace();
      }
    }

    return port;
  }
}
