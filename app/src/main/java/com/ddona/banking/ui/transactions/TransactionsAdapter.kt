package com.ddona.banking.ui.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ddona.banking.R
import com.ddona.banking.presentation.model.Transaction
import com.ddona.banking.utils.toCurrency
import com.ddona.banking.utils.toDisplayDate
import kotlinx.android.synthetic.main.item_transaction.view.*

class TransactionsAdapter(
    private val listener: TransactionClickListener
) : RecyclerView.Adapter<TransactionsAdapter.TransactionVH>() {

    private val transactionList: MutableList<Transaction> = ArrayList()

    fun populate(transactions: List<Transaction>) {
        transactionList.clear()
        transactionList.addAll(transactions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionVH {
        val inflater = LayoutInflater.from(parent.context)
        return TransactionVH(
            inflater.inflate(R.layout.item_transaction, parent, false)
        )
    }

    override fun getItemCount() = transactionList.size

    override fun onBindViewHolder(
        holder: TransactionVH,
        position: Int
    ) = holder.bind(transactionList[position])

    inner class TransactionVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(transaction: Transaction) {
            itemView.transactionAmount.text = String.format(
                itemView.context.getString(
                    R.string.txt_amount_type_transaction,
                    (transaction.amountInCents / 100.0).toCurrency(),
                    transaction.type.capitalize()
                )
            )

            itemView.transactionTimestamp.text = transaction.timestamp.toDisplayDate()

            itemView.transactionRemarks.text = transaction.remarks

            itemView.background = when (transaction.type) {
                "debit" -> itemView
                    .context
                    .getDrawable(R.drawable.bg_rounded_border_light_orange)
                else -> itemView
                    .context
                    .getDrawable(R.drawable.bg_rounded_border_cyan)
            }

            itemView.setOnClickListener {
                listener.onTransactionTapped(transaction)
            }

            itemView.transactionFlag.setOnClickListener {
                listener.onFlagToggled(transaction)
            }

            itemView.transactionFlag.setImageResource(
                when (transaction.flagged) {
                    true -> R.drawable.ic_flag_on
                    false -> R.drawable.ic_flag_off
                }
            )

            itemView.transactionHVT.visibility = when (transaction.isHVT) {
                true -> View.VISIBLE
                false -> View.GONE
            }

        }

    }

    interface TransactionClickListener {
        fun onTransactionTapped(transaction: Transaction)
        fun onFlagToggled(transaction: Transaction)
    }
}