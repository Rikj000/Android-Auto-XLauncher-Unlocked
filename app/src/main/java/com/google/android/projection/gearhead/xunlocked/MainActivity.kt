package com.google.android.projection.gearhead.xunlocked

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.projection.gearhead.xunlocked.databinding.ActivityMainBinding

/** Main Magic happens in [LaunchOptionsFragment] */
class MainActivity : AppCompatActivity() {

    private val repoUrl: String = "https://github.com/Rikj000/Android-Auto-XLauncher-Unlocked"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        return
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        val browserIntent = Intent(Intent.ACTION_VIEW)

        when (item.itemId) {
            R.id.action_source_code -> browserIntent.data = Uri.parse(repoUrl)
            R.id.action_updates -> browserIntent.data = Uri.parse("$repoUrl/releases")
            R.id.action_magisk ->
                browserIntent.data = Uri.parse("https://topjohnwu.github.io/Magisk/")
            R.id.action_lsposed -> browserIntent.data = Uri.parse("https://lsposed.org/")
        }

        startActivity(browserIntent)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
