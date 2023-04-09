package com.google.android.projection.gearhead.xunlocked;

import java.io.DataOutputStream;
import java.io.IOException;

public class Su {

    /**
     * Run a command as SuperUser
     *
     * @param cmd Command to run
     */
    public static void runCommand(String cmd) {
        try {
            DataOutputStream outputStream;
            Process su = Runtime.getRuntime().exec("su");
            outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(cmd + "\n");
            outputStream.flush();
            outputStream.writeBytes("exit\n");
            outputStream.flush();

            try { su.waitFor(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Launch a package activity as SuperUser
     *
     * @param packageName Name of the package
     * @param activityName Name of the activity
     */
    public static void launchActivity(String packageName, String activityName) {
        runCommand("am start -n " + packageName + "/" + activityName);
    }

    /**
     * Launch an Android Auto activity as SuperUser
     * @param activityName Name of the activity
     */
    public static void launchAndroidAutoActivity(String activityName) {
        launchActivity("com.google.android.projection.gearhead", activityName);
    }
}
