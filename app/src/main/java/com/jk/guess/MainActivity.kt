package com.jk.guess

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "secret: ${secretNumber.secret}")

        button_ok.setOnClickListener {
            val n = ed_number.text.toString().toInt()
            val diff = secretNumber.validate(n)
            var message = "Yes, you got it"
            Log.d(TAG, n.toString())

            if (diff < 0) {
                message = "Bigger"
            } else if (diff > 0) {
                message = "Smaller"
            }

//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this)
                .setTitle("Message")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show()
        }
    }
}