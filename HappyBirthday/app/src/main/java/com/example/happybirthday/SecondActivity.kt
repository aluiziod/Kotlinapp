package com.example.happybirthday


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class SecondActivity : AppCompatActivity() {

    private lateinit var allTransaction: List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var database:AppDatabase
    private lateinit var deletedTransaction: Transaction
    private lateinit var oldTransaction: List<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
//this comment is a test

        allTransaction = arrayListOf()


        transactionAdapter = TransactionAdapter(allTransaction)
        linearLayoutManager = LinearLayoutManager(this)

        database=Room.databaseBuilder(this,
        AppDatabase::class.java,
        "transactions").build()

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.apply {
            adapter = transactionAdapter
            layoutManager = linearLayoutManager
        }

        //this will handle swipes
        val touchHelper = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTransaction(allTransaction[viewHolder.adapterPosition])
            }

        }

        val swipeHelper = ItemTouchHelper(touchHelper)
        swipeHelper.attachToRecyclerView(recyclerview)

        val thirdActivityButton : Button = findViewById(R.id.addButton)
        thirdActivityButton.setOnClickListener {
            val Intent = Intent(this,plusTransactionActivity::class.java)
            startActivity(Intent)
        }
               }


    private fun fetchAll(){
    GlobalScope.launch {


        allTransaction = database.userDao().getAll()

        runOnUiThread {
            updateTotalSpent()
            transactionAdapter.updater(allTransaction)
        }
    }
    }
    private fun updateTotalSpent(){
        val total = allTransaction.map { it.amount }.sum()

        val totalSpent = findViewById<TextView>(R.id.totalSpent)
        totalSpent.text = "$ %.2f".format(total)
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteTran()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun deleteTran(){
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes"){ _, _ ->
            Toast.makeText(applicationContext,
                "All transactions cleared successfully", Toast.LENGTH_LONG).show()
            val  database=Room.databaseBuilder(this,
                AppDatabase::class.java,
                "transactions").build()

            GlobalScope.launch {
                database.userDao().deleteAll()

            }
            finish()



        }
        builder.setNegativeButton("No"){ _, _ ->}
        builder.setTitle("Are you sure you want do delete all the transactions?")
        builder.setMessage("Please confirm")
        builder.create().show()
    }

    private fun undoDelete(){
        GlobalScope.launch {
            database.userDao().insertAll(deletedTransaction)
            allTransaction = oldTransaction
            runOnUiThread {
                transactionAdapter.updater(allTransaction)
                updateTotalSpent()
            }
        }
    }

    private fun createBar(){
        val view = findViewById<View>(R.id.coordinatorLayout)
        val bar = Snackbar.make(view,"Transaction Deleted",Snackbar.LENGTH_LONG)
        bar.setAction("Undo"){
            undoDelete()
        }

            .setActionTextColor(ContextCompat.getColor(this,R.color.red))
            .show()

    }

    private fun deleteTransaction(transaction: Transaction){
        deletedTransaction = transaction
        oldTransaction = allTransaction

        GlobalScope.launch {
            database.userDao().delete(transaction)

            allTransaction = allTransaction.filter { it.id != transaction.id}
            runOnUiThread {
                updateTotalSpent()
                transactionAdapter.updater(allTransaction)
                createBar()
            }
        }
    }




}
