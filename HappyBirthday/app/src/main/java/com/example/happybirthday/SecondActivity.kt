package com.example.happybirthday


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button


class SecondActivity : AppCompatActivity() {
    private lateinit var transaction: ArrayList<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//this comment is a test
        transaction = arrayListOf(
            Transaction("Expense 1", 100.00),
            Transaction("Expense 2", 200.00),
            Transaction("Expense 3", 300.00,),
            Transaction("Expense 4", 100.00),
            Transaction("Expense 5", 200.00),
            Transaction("Expense 6", 300.00)
        )

        transactionAdapter = TransactionAdapter(transaction)
        linearLayoutManager = LinearLayoutManager(this)

        val thirdActivityButton : Button = findViewById(R.id.addButton)
        thirdActivityButton.setOnClickListener {
            val Intent = Intent(this,plusTransactionActivity::class.java)
            startActivity(Intent)
        }
               }
    }
