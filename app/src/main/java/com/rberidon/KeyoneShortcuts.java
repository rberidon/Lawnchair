package com.rberidon;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import ch.deletescape.lawnchair.Utilities;

public class KeyoneShortcuts {
  private static Toast toast;
  private static LauncherApps launcherApps;

  public static void onKeyUp(Context context, int keyCode, KeyEvent event) {
    handleKey(context, event);
  }

  private static void handleKey(Context context, KeyEvent event) {
    switch (event.getKeyCode()) {
      case KeyEvent.KEYCODE_E:
        startShortcut(context, AppShortcuts.CHROME);
        break;
      default:
        debugShowKeyToast(context, event);
        break;
    }
  }

  private static void startShortcut(Context context, ShortcutData shortcutData) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
      if (launcherApps == null) {
        launcherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
      }

      try {
        launcherApps.startShortcut(shortcutData.packageName, shortcutData.shortcutId, null, null,
            Utilities.myUserHandle());
      } catch (Throwable e) {
        Log.e("DeepShortcutManager", "Failed to start shortcut", e);
      }
    }
  }

  private static void debugShowKeyToast(Context context, KeyEvent event) {
    long duration = event.getEventTime() - event.getDownTime();
    String log = "Key " //
        + KeyEvent.keyCodeToString(event.getKeyCode()) //
        + " down for " //
        + duration //
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
