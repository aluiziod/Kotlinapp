package com.example.happybirthday

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.widget.Filter
import android.widget.Filterable


class TransactionAdapter(private var transactions: List<Transaction>):
    RecyclerView.Adapter<TransactionAdapter.TransactionHolder>(), Filterable {

    class  TransactionHolder(view:View) : RecyclerView.ViewHolder(view){
        val label : TextView = view.findViewById(R.id.label)
        val amount : TextView = view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.transaction_layout,parent,false)
        return TransactionHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionHolder, position: Int) {

        val transaction : Transaction = transactions[position]
        val context : Context = holder.amount.context

        holder.amount.text = "- €%.2f".format(transaction.amount)
        holder.label.text = transaction.label

        holder.itemView.setOnClickListener{
            val intent = Intent(context, updateActivity::class.java)
            intent.putExtra("transaction",transaction)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
    fun updater(transactions: List<Transaction>){
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        var fiter = object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                TODO("Not yet implemented")
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                TODO("Not yet implemented")
            }
        }

        return filter;
    }


}

