package me.bytebeats.activitylauncher

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import me.bytebeats.launcher.ActivityLauncher

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
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
        return when (item.itemId) {
            R.id.action_settings -> {
                ActivityLauncher.with(this)
                    .startActivityForResult(
                        Intent(this, SecondActivity::class.java),
                        object : ActivityLauncher.Callback {
                            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                                Toast.makeText(
                                    this@MainActivity,
                                    data?.getIntExtra("result", -1)?.toString() ?: "no",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}