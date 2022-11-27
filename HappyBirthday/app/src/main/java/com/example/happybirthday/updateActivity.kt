package com.example.happybirthday

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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



}

