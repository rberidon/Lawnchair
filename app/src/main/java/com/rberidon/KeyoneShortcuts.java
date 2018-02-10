package com.rberidon;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.Build;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import ch.deletescape.lawnchair.Utilities;

public class KeyoneShortcuts {
  @SuppressLint("StaticFieldLeak") public static final KeyoneShortcuts INSTANCE =
      new KeyoneShortcuts();
  private boolean active = false;
  private Context context;
  private Toast toast;
  private LauncherApps launcherApps;

  public void init(Context context) {
    this.context = context;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
      launcherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
      active = launcherApps != null;
    }
  }

  @TargetApi(Build.VERSION_CODES.N_MR1) public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (!active) return false;
    switch (event.getKeyCode()) {
      case KeyEvent.KEYCODE_E:
        return startShortcut(AppShortcuts.CHROME);
      default:
        debugShowKeyToast(event);
        return true;
    }
  }

  @TargetApi(Build.VERSION_CODES.N_MR1) private boolean startShortcut(ShortcutData shortcutData) {
    try {
      UserHandle user = Utilities.myUserHandle();

      if (launcherApps.isPackageEnabled(shortcutData.packageName, user)) {
        launcherApps.startShortcut(shortcutData.packageName, shortcutData.shortcutId, null, null,
            user);
        return true;
      } else {
        return false;
      }
    } catch (Throwable e) {
      Log.e("DeepShortcutManager", "Failed to start shortcut", e);
      return false;
    }
  }

  private void debugShowKeyToast(KeyEvent event) {
    long duration = event.getEventTime() - event.getDownTime();
    String log = "Key " //
        + KeyEvent.keyCodeToString(event.getKeyCode()) //
        + " down for " //
        + duration //
        + " ms.";
    showToast(log);
  }

  private void showToast(String text) {
    System.out.println(text);

    if (toast != null) {
      toast.cancel();
    }
    toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
    toast.show();
  }
}
