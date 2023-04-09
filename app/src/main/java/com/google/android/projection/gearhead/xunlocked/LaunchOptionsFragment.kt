package com.google.android.projection.gearhead.xunlocked

//region Imports

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.projection.gearhead.xunlocked.databinding.FragmentLaunchOptionsBinding

//endregion Imports

/** The main [Fragment] subclass of Android Auto - XLauncher Unlocked */
class LaunchOptionsFragment : Fragment() {

    //region Initialization

    // Initialize fragment bindings
    private var _binding: FragmentLaunchOptionsBinding? = null
    private val binding get() = _binding!!

    // Initialize static helpers
    private val logTag: String = "AndroidAuto-LauncherUnlocked"
    private val aaPackageName: String = "com.google.android.projection.gearhead"
    private val aaMaterial3Settings: String = ".companion.settings.Material3SettingsActivity"

    // Initialize settings
    private var defaultActivityName: String? = ""
    private var useDefault: Boolean = false
    private lateinit var settings: SharedPreferences

    @Suppress("RedundantNullableReturnType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLaunchOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /** Constructor for the main view fragment */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load the stored settings if existing
        settings = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        useDefault = settings.getBoolean("use_default", false)
        defaultActivityName = settings.getString("default_activity_name", "")

        // Setup the default checkbox
        binding.checkboxDefault.isChecked = useDefault
        binding.checkboxDefault.setOnClickListener { toggleUseDefault() }

        // Setup all the launcher options
        binding.buttonClassic.setOnClickListener { launchAndroidAutoSettings(true) }
        binding.buttonMaterial3.setOnClickListener { launchAndroidAutoSettings() }
        binding.buttonInteractiveTroubleshooter.setOnClickListener { launchInteractiveTroubleshooter() }
        binding.buttonTroubleshooter.setOnClickListener { launchTroubleshooter() }
        binding.buttonDeveloperSettings.setOnClickListener { launchDeveloperSettings() }
        binding.buttonEngineerSettings.setOnClickListener { launchEngineerSettings() }
        binding.buttonAppQualityTester.setOnClickListener { launchAppQualityTester() }
        binding.buttonFeedbackReports.setOnClickListener { launchFeedbackReports() }
        binding.buttonPrimesEvents.setOnClickListener { launchPrimesEvents() }
        binding.buttonAssistantShortcut.setOnClickListener { launchAssistantShortcuts() }
        binding.buttonAppLauncher.setOnClickListener { launchAppLauncher() }

        // Launch the default launcher option if used
        if (useDefault && defaultActivityName != "") { launchDefault() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //endregion Initialization

    // region Launcher Options

    /** Launch the configured default activity */
    private fun launchDefault() {
        if (defaultActivityName.equals("classic", false)) { launchClassicSettings() }
        else { Su.launchAndroidAutoActivity(defaultActivityName) }
    }

    /** Launch the Material3 or Classic Android Auto settings */
    private fun launchAndroidAutoSettings(classic: Boolean = false) {
        val type = if (classic) "Classic" else "Material3"
        Log.i(logTag, "Attempting to launch AA $type settings...")

        try {
            if (classic) launchClassicSettings() else launchMaterial3Settings()
            Log.i(logTag, "Launched AA $type settings!")
        } catch (ex: Throwable) {
            Log.e(logTag, "Could not launch AA $type settings, activity not found...")
            ex.message?.let { Log.e(logTag, it) }
            Log.e(logTag, ex.stackTrace.contentDeepToString())
        }
    }

    /** Launch the Classic Android Auto settings */
    private fun launchClassicSettings() {
        updateDefaultActivityName("classic")
        val intent = Intent()
            .setComponent(ComponentName(
                aaPackageName,
                "com.google.android.gearhead.vanagon.VnLaunchPadInternalActivity"))
            .putExtra(
                "com.google.android.gearhead.projection.phonescreen.SHORTCUT",
                true)

        startActivity(intent)
    }

    /** Launch an Android Auto activity by activity name + update the default if used */
    private fun launchSuAndroidAutoActivity(activityName: String) {
        updateDefaultActivityName(activityName)
        Su.launchAndroidAutoActivity(activityName)
    }

    /** Launch the Android Auto Material3 settings activity + update the default if used */
    private fun launchMaterial3Settings() {
        launchSuAndroidAutoActivity(aaPackageName + aaMaterial3Settings)
    }

    /** Launch the Android Auto Interactive Troubleshooter activity + update the default if used */
    private fun launchInteractiveTroubleshooter() {
        launchSuAndroidAutoActivity(
            "com.google.android.apps.auto" +
                    ".components.settings.interactivetroubleshooter.InteractiveTroubleshooterActivity")
    }

    /** Launch the Android Auto Troubleshooter activity + update the default if used */
    private fun launchTroubleshooter() {
        launchSuAndroidAutoActivity(
            "com.google.android.apps.auto" +
                    ".components.settings.troubleshooter.TroubleshooterActivity")
    }

    /** Launch the Android Auto Developer settings activity + update the default if used */
    private fun launchDeveloperSettings() {
        launchSuAndroidAutoActivity(
            "$aaPackageName.companion.devsettings.DeveloperSettingsActivity")
    }

    /** Launch the Android Auto Engineer settings activity + update the default if used */
    private fun launchEngineerSettings() {
        launchSuAndroidAutoActivity(
            "$aaPackageName.companion.settings.EngineerSettingsActivity")
    }

    /** Launch the Android Auto App Quality Tester activity + update the default if used */
    private fun launchAppQualityTester() {
        launchSuAndroidAutoActivity(
            "$aaPackageName.companion.devsettings.AppQualityTesterActivity")
    }

    /** Launch the Android Auto Feedback Reports activity + update the default if used */
    private fun launchFeedbackReports() {
        launchSuAndroidAutoActivity(
            "$aaPackageName.companion.feedback.FeedbackListActivity")
    }

    /** Launch the Android Auto PRIMES Events activity + update the default if used */
    private fun launchPrimesEvents() {
        launchSuAndroidAutoActivity(
            "com.google.android.libraries.performance.primes.debug.PrimesEventActivity")
    }

    /** Launch the Android Auto Assistant Shortcuts activity + update the default if used */
    private fun launchAssistantShortcuts() {
        launchSuAndroidAutoActivity(
            "$aaPackageName.companion.settings.AddAssistantShortcutActivity")
    }

    /** Launch the Android Auto App Launcher activity + update the default if used */
    private fun launchAppLauncher() {
        launchSuAndroidAutoActivity(
            "$aaPackageName.companion.settings.LauncherAppSettingsActivity")
    }

    // endregion Launcher Options

    //region Helpers

    /** Toggle the use default launch option setting */
    private fun toggleUseDefault() {
        useDefault = !useDefault
        settings.edit().putBoolean("use_default", useDefault).apply()
        binding.checkboxDefault.isChecked = useDefault

        // Clear the default activity name if not using a default launch option
        updateDefaultActivityName()
    }

    /** Update the default activity name, clear if not using a default */
    private fun updateDefaultActivityName(activityName: String = "") {
        defaultActivityName = if (useDefault) activityName else ""
        settings.edit().putString("default_activity_name", defaultActivityName).apply()
    }

    //endregion Helpers
}
