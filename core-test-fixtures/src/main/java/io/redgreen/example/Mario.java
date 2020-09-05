package io.redgreen.example;

import java.util.Random;

public class Mario {
  void move() {
    if (!isBlocked()) {
      walk();
    } else {
      jump();
    }
  }

  private boolean isBlocked() {
    Random random = new Random();
    return random.nextInt(100 + 1) % 10 == 0;
  }

  private void walk() {
    // Easy peasy!
  }

  private void jump() {
    // Woo hoo! Up in the air!
  }
}
