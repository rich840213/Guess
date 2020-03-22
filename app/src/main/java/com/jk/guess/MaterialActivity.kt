package com.jk.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_material.*
import kotlinx.android.synthetic.main.content_material.*

class MaterialActivity : AppCompatActivity() {

    private val REQUEST_RECORD = 100
    //TODO 測試用所以先不加private
    val TAG = MaterialActivity::class.java.simpleName
    val secretNumber = SecretNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/
            replay()
        }

        counter.text = secretNumber.count.toString()
        Log.d(TAG, "onCreate: ${secretNumber.secret}")

        val count = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getInt("REC_COUNTER", -1)
        val nick = getSharedPreferences("guess", Context.MODE_PRIVATE)
            .getString("REC_NICKNAME", null)
        Log.d(TAG, "data: $nick/$count")

        button_ok.setOnClickListener {
            if (ed_number.text.isEmpty()) return@setOnClickListener

            val n = ed_number.text.toString().toInt()
            val diff = secretNumber.validate(n)
            var message = getString(R.string.yes_you_got_it)
            Log.d(TAG, n.toString())

            if (diff < 0) {
                message = getString(R.string.bigger)
            } else if (diff > 0) {
                message = getString(R.string.smaller)
            }
            counter.text = secretNumber.count.toString()

//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    ed_number.setText("")
                    if (diff == 0) {
                        val intent = Intent(this, RecordActivity::class.java)
                        intent.putExtra("COUNTER", secretNumber.count)
//                        startActivity(intent)
                        startActivityForResult(intent, REQUEST_RECORD)
                    }
                }
                .show()
        }
    }

    private fun replay(name: String = "") {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.replay_game))
            .setMessage("$name ${getString(R.string.are_you_sure)}")
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                secretNumber.reset()
                counter.text = secretNumber.count.toString()
                ed_number.setText("")
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_RECORD) {
            if (resultCode == Activity.RESULT_OK) {
                val nickname = data?.getStringExtra("REC_NICKNAME") ?: ""
                replay(nickname)
            }
        }
    }
}
