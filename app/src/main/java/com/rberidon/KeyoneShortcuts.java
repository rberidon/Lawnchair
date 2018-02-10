package com.rberidon;

import android.content.Context;
import android.view.KeyEvent;
import android.widget.Toast;

public class KeyoneShortcuts {
  private static Toast toast;

  public static void onKeyUp(Context context, int keyCode, KeyEvent event) {
    handleKey(context, event);
  }

  private static void handleKey(Context context, KeyEvent event) {
    switch (event.getKeyCode()) {
      default:
        debugShowKeyToast(context, event);
        break;
    }
  }

  private static void debugShowKeyToast(Context context, KeyEvent event) {
    long duration = event.getEventTime() - event.getDownTime();
    String log = "Key "
        + KeyEvent.keyCodeToString(event.getKeyCode())
        + " down for "
        + duration
        + " ms.";
    showToast(context, log);
  }

  private static void showToast(Context context, String text) {
    System.out.println(text);

    if (toast != null) {
      toast.cancel();
    }
    toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
    toast.show();
  }
}
