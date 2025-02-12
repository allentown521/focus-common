/*
 * Copyright (c) 2019 Hemanth Savarala.
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by
 *  the Free Software Foundation either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package allen.town.focus_common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import code.name.monkey.appthemehelper.util.TintHelper;

public class RetroUtil {

  private static final int[] TEMP_ARRAY = new int[1];

  private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";

  public static int calculateNoOfColumns(@NonNull Context context) {
    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
    float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
    return (int) (dpWidth / 180);
  }

  @NonNull
  public static Bitmap createBitmap(@NonNull Drawable drawable, float sizeMultiplier) {
    Bitmap bitmap =
            Bitmap.createBitmap(
                    (int) (drawable.getIntrinsicWidth() * sizeMultiplier),
                    (int) (drawable.getIntrinsicHeight() * sizeMultiplier),
                    Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(bitmap);
    drawable.setBounds(0, 0, c.getWidth(), c.getHeight());
    drawable.draw(c);
    return bitmap;
  }

  public static String formatValue(float value) {
    String[] arr = {"", "K", "M", "B", "T", "P", "E"};
    int index = 0;
    while ((value / 1000) >= 1) {
      value = value / 1000;
      index++;
    }
    DecimalFormat decimalFormat = new DecimalFormat("#.##");
    return String.format("%s %s", decimalFormat.format(value), arr[index]);
  }

  public static float frequencyCount(int frequency) {
    return (float) (frequency / 1000.0);
  }

  public static Point getScreenSize(@NonNull Context c) {
    Display display = null;
    if (c.getSystemService(Context.WINDOW_SERVICE) != null) {
      display = ((WindowManager) c.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }
    Point size = new Point();
    if (display != null) {
      display.getSize(size);
    }
    return size;
  }

  public static int getStatusBarHeight(Context context) {
    int result = 0;
    int resourceId =
            context
                    .getResources()
                    .getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  public static int getNavigationBarHeight(Context context) {
    int result = 0;
    int resourceId =
            context
                    .getResources()
                    .getIdentifier("navigation_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = context.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  @NonNull
  public static Drawable getTintedVectorDrawable(
          @NonNull Context context, @DrawableRes int id, @ColorInt int color) {
    return TintHelper.createTintedDrawable(
            getVectorDrawable(context.getResources(), id, context.getTheme()), color);
  }

  @NonNull
  public static Drawable getTintedVectorDrawable(
          @NonNull Resources res,
          @DrawableRes int resId,
          @Nullable Resources.Theme theme,
          @ColorInt int color) {
    return TintHelper.createTintedDrawable(getVectorDrawable(res, resId, theme), color);
  }

  @Nullable
  public static Drawable getVectorDrawable(
          @NonNull Resources res, @DrawableRes int resId, @Nullable Resources.Theme theme) {
    return ResourcesCompat.getDrawable(res, resId, theme);
  }

  public static void hideSoftKeyboard(@Nullable Activity activity) {
    if (activity != null) {
      View currentFocus = activity.getCurrentFocus();
      if (currentFocus != null) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
          inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
      }
    }
  }


  public static boolean isLandscape(Context context) {
    return context.getResources().getConfiguration().orientation
            == Configuration.ORIENTATION_LANDSCAPE;
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static boolean isRTL(@NonNull Context context) {
    Configuration config = context.getResources().getConfiguration();
    return config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
  }

  public static boolean isTablet(Context context) {
    return context.getResources().getConfiguration().smallestScreenWidthDp
            >= 600;
  }

  public static void openUrl(@NonNull Activity context, @NonNull String str) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.setData(Uri.parse(str));
    intent.setFlags(268435456);
    context.startActivity(intent);
  }

  public static void setAllowDrawUnderNavigationBar(Window window) {
    window.setNavigationBarColor(Color.TRANSPARENT);
    window
            .getDecorView()
            .setSystemUiVisibility(
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                            ? View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            : View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
  }

  public static void setAllowDrawUnderStatusBar(@NonNull Window window) {
    window
            .getDecorView()
            .setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    window.setStatusBarColor(Color.TRANSPARENT);
  }

  public static String getIpAddress(boolean useIPv4) {
    try {
      List<NetworkInterface> interfaces =
              Collections.list(NetworkInterface.getNetworkInterfaces());
      for (NetworkInterface intf : interfaces) {
        List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
        for (InetAddress addr : addrs) {
          if (!addr.isLoopbackAddress()) {
            String sAddr = addr.getHostAddress();
            //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
            boolean isIPv4 = sAddr.indexOf(':') < 0;
            if (useIPv4) {
              if (isIPv4) return sAddr;
            } else {
              if (!isIPv4) {
                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                if (delim < 0) {
                  return sAddr.toUpperCase();
                } else {
                  return sAddr.substring(
                          0,
                          delim
                  ).toUpperCase();
                }
              }
            }
          }
        }

      }
    } catch (Exception ignored) {
    }
    return "";
  }
}
