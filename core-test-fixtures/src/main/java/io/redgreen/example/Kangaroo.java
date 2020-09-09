package io.redgreen.example;

import kotlin.jvm.functions.Function0;

public class Kangaroo {
  private String name;

  public void setDefaultName() {
    //noinspection TrivialFunctionalExpressionUsage
    new Function0<Void>() {
      @Override
      public Void invoke() {
        name = "Jumper";
        return null;
      }
    }.invoke();
  }

  public String getName() {
    return name;
  }
}
