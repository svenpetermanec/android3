package hr.tvz.android.listapetermanec

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import hr.tvz.android.listapetermanec.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ListFragment.Callbacks {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext, BazaPodataka::class.java, "newTest")
            .allowMainThreadQueries().build()
        val exercDao = db.exercDao()
        if (exercDao.getAll().isEmpty()) {
            exercDao.insertOne(
                Exerc(
                    1,
                    "Bench press (barbell)",
                    "Lie supine on bench. Dismount barbell from rack over upper chest using wide oblique overhand grip. Lower weight to chest. Press bar upward until arms are extended."
                )
            )
            exercDao.insertOne(
                Exerc(
                    2,
                    "Squat (barbell)",
                    "Squat down by bending hips back while allowing knees to bend forward, keeping back straight and knees pointed same direction as feet. Descend until thighs are just past parallel to floor. Extend knees and hips until legs are straight."
                )
            )
            exercDao.insertOne(
                Exerc(
                    3,
                    "Seated row (cable)",
                    "Pull cable attachment to waist while straightening lower back. Pull shoulders back and push chest forward while arching back. Return until arms are extended, shoulders are stretched forward, and lower back is flexed forward."
                )
            )
        }


        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        val receiver = Receiver(baseContext)
        registerReceiver(receiver, filter)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FAIL", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            Log.d("TOKEN", token)
            Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })

        supportFragmentManager.beginTransaction().add(R.id.item_list_container, ListFragment())
            .commit()
        if (findViewById<FrameLayout>(R.id.item_detail_container) != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.item_detail_container, DetailFragment()).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onItemSelected(id: String?) {
        val arguments = Bundle()
        arguments.putString("id", id)
        val detailFragment = DetailFragment()
        detailFragment.arguments = arguments
        if (id != null) {
            if (findViewById<FrameLayout>(R.id.item_detail_container) != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.item_detail_container, detailFragment).commit()
            } else {
                Log.d("TESTHERE", id)
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra("exercise", Exercise(id.toInt() + 1))
                this.startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> showDialog()
            R.id.web -> startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://www.fitnotesapp.com")
                )
            )
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle(R.string.share_message)
            setMessage(R.string.share_message)
            setPositiveButton(
                R.string.yes
            ) { _, _ -> sendBroadcast(Intent().setAction("hr.tvz.android.broadcast")) }
            setNegativeButton(R.string.no) { _, _ -> }
            show()
        }
    }
}