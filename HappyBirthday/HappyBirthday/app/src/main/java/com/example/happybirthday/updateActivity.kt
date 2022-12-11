package com.example.happybirthday

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_plus_transaction.*
import kotlinx.android.synthetic.main.activity_plus_transaction.costInput
import kotlinx.android.synthetic.main.activity_plus_transaction.costLayout
import kotlinx.android.synthetic.main.activity_plus_transaction.expenseInput
import kotlinx.android.synthetic.main.activity_plus_transaction.expenseLayout
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.*
import kotlinx.android.synthetic.main.activity_testactivity.*
import kotlinx.android.synthetic.main.activity_update.imageView

private const val REQUEST_CODE = 42
class updateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var transaction : Transaction



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        transaction = intent.getSerializableExtra("transaction") as Transaction


        expenseInput.setText(transaction.label)
        costInput.setText(transaction.amount.toString())

       rootView.setOnClickListener{
           this.window.decorView.clearFocus()
           val aux = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
           aux.hideSoftInputFromWindow(it.windowToken,0)
       }



        updateBtn.setOnClickListener{

            val expense = expenseInput.text.toString()
            val cost = costInput.text.toString().toDoubleOrNull()

            if(expense.isEmpty())
                expenseLayout.error = "This field cannot be empty"

            if(cost == null)
                costLayout.error = "This field cannot be empty"
            else{
                val transaction = Transaction(transaction.id,expense,cost)
                updateData(transaction)
            }
        }


        receiptButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)

            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }




    }
    private fun updateData(transaction: Transaction){
        val  database= Room.databaseBuilder(this,
            AppDatabase::class.java,
            "transactions").build()

        GlobalScope.launch {
            database.userDao().update(transaction)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val takenImage = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(takenImage)
            receiptSign.setVisibility(View.VISIBLE)
            imageView.setVisibility(View.VISIBLE)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }



}

