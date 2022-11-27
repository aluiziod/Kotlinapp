package com.example.happybirthday

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.widget.EditText
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_plus_transaction.*
import kotlinx.android.synthetic.main.activity_plus_transaction.costInput
import kotlinx.android.synthetic.main.activity_plus_transaction.costLayout
import kotlinx.android.synthetic.main.activity_plus_transaction.expenseInput
import kotlinx.android.synthetic.main.activity_plus_transaction.expenseLayout
import kotlinx.android.synthetic.main.activity_update.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class plusTransactionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus_transaction)



        rootView2.setOnClickListener{
            this.window.decorView.clearFocus()
            val aux2 = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            aux2.hideSoftInputFromWindow(it.windowToken,0)
        }

        submitTransactionButton.setOnClickListener{

            val expense = expenseInput.text.toString()
            val cost = costInput.text.toString().toDoubleOrNull()

            if(expense.isEmpty())
                expenseLayout.error = "This field cannot be empty"

            if(cost == null)
                costLayout.error = "This field cannot be empty"
            else{
                val transaction = Transaction(0,expense,cost)
                submitData(transaction)
                Toast.makeText(applicationContext,
                    "Transaction added", Toast.LENGTH_LONG).show()
            }
        }

        }
        private fun submitData(transaction: Transaction){
            val  database= Room.databaseBuilder(this,
                AppDatabase::class.java,
                "transactions").build()

            GlobalScope.launch {
                database.userDao().insertAll(transaction)
                finish()
            }
        }

    }
