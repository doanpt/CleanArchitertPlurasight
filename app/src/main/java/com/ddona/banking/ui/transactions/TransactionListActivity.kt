package com.ddona.banking.ui.transactions

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ddona.banking.R
import com.ddona.banking.presentation.factory.ViewModelFactory
import com.ddona.banking.presentation.model.Status
import com.ddona.banking.presentation.model.Transaction
import com.ddona.banking.presentation.viewmodels.TransactionsViewModel
import com.ddona.banking.utils.ALPHA_HIDDEN
import com.ddona.banking.utils.ALPHA_VISIBLE
import com.ddona.banking.utils.setCustomChecked
import kotlinx.android.synthetic.main.activity_transaction_list.*

class TransactionListActivity : AppCompatActivity(),
    TransactionsAdapter.TransactionClickListener,
    CompoundButton.OnCheckedChangeListener {

    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var transactionsViewModel: TransactionsViewModel

    private val transactionListAdapter = TransactionsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setTitle(R.string.title_transactions)
        }

        rvTransactionList.layoutManager = LinearLayoutManager(this)
        rvTransactionList.adapter = transactionListAdapter

        chkTransactionCredit.setOnCheckedChangeListener(this)
        chkTransactionDebit.setOnCheckedChangeListener(this)
        chkTransactionFlagged.setOnCheckedChangeListener(this)

        transactionsViewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(TransactionsViewModel::class.java)
        transactionsViewModel.transactionListSource.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    println("Transactions Loading")
                    showLoader()
                }
                Status.ERROR -> {
                    println("Transactions ERROR: ${it.message}")
                    hideLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()
                    it.data?.let { transactions ->
                        transactionListAdapter.populate(transactions)
                        supportActionBar?.let { actionBar ->
                            actionBar.title = "Transactions (${transactions.size})"
                        }
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_transaction, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (item.itemId) {
                android.R.id.home -> {
                    this@TransactionListActivity.finish()
                }
                R.id.action_reset_filter -> {
                    chkTransactionCredit.setCustomChecked(false, this)
                    chkTransactionDebit.setCustomChecked(false, this)
                    chkTransactionFlagged.setCustomChecked(false, this)
                    transactionsViewModel.resetFilters()
                }
            }
        }
        return false
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        buttonView?.let {
            transactionsViewModel.filterTransactions(
                credit = chkTransactionCredit.isChecked,
                debit = chkTransactionDebit.isChecked,
                flagged = chkTransactionFlagged.isChecked
            )
        }
    }

    override fun onTransactionTapped(transaction: Transaction) {
        Toast.makeText(this, "${transaction.amountInCents} clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onFlagToggled(transaction: Transaction) {
        transactionsViewModel.toggleFlaggedStatus(transaction)
    }

    private fun showLoader() {
        pbTransactionLoader.visibility = View.VISIBLE
        rvTransactionList.alpha = ALPHA_HIDDEN
        llTransactionFilterHolder.alpha = ALPHA_HIDDEN
    }

    private fun hideLoader() {
        pbTransactionLoader.visibility = View.GONE
        rvTransactionList.alpha = ALPHA_VISIBLE
        llTransactionFilterHolder.alpha = ALPHA_VISIBLE
    }
}