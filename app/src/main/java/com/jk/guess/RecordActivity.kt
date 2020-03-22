package com.jk.guess

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val count = intent.getIntExtra("COUNTER", -1)
        counter.text = count.toString()

        // OnClickListener
        save.setOnClickListener {
            val nick = nickname.text.toString()
            if (nick.isNotEmpty()) {
                getSharedPreferences("guess", Context.MODE_PRIVATE)
                    .edit()
                    .putInt("REC_COUNTER", count)
                    .putString("REC_NICKNAME", nick)
                    .apply()

                val intent = Intent()
                intent.putExtra("REC_NICKNAME", nick)

                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_title))
                    .setMessage(getString(R.string.please_input_your_nick_name))
                    .setPositiveButton(getString(R.string.ok), null)
                    .show()
            }
        }
    }
}
