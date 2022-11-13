package com.example.happybirthday

import android.support.v7.app.AppCompatActivity
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import java.text.FieldPosition

class TransactionAdapter(private val transactions: ArrayList<Transaction>):
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>() {

    class  TransactionHolder(view:View) : RecyclerView.ViewHolder(view){
        val label : TextView = view.findViewById(R.id.label)
        val amount : TextView = view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout,parent,false)
        return TransactionHolder(view)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout, parent, false)
       // return TransactionHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {
        val transaction = transactions[position]
        val context = holder.amount.context

        holder.amount.text = "- €%.2f".format(transaction.amount)
        holder.label.text = transaction.label
    }

    override fun getItemCount(): Int {
        return transactions.size
    }


}

