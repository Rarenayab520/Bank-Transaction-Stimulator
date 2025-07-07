package com.nayab.banktransitionstimulator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*


class MainActivity : AppCompatActivity() {

    private lateinit var etAmount: EditText
    private lateinit var tvBalance: TextView
    private lateinit var tvMessage: TextView
    private val account = BankAccount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etAmount = findViewById(R.id.etAmount)
        tvBalance = findViewById(R.id.tvBalance)
        tvMessage = findViewById(R.id.tvMessage)

        findViewById<Button>(R.id.btnDeposit).setOnClickListener { performTransaction(true) }
        findViewById<Button>(R.id.btnWithdraw).setOnClickListener { performTransaction(false) }

        updateBalance()
    }

    private fun performTransaction(isDeposit: Boolean) {
        val input = etAmount.text.toString()
        try {
            val amount = input.toDouble()
            if (isDeposit) account.deposit(amount)
            else account.withdraw(amount)

            tvMessage.setTextColor(getColor(R.color.green))
            tvMessage.text = if (isDeposit) "Deposited $$amount" else "Withdrew $$amount"
        } catch (e: InsufficientBalanceException) {
            tvMessage.setTextColor(getColor(R.color.red))
            tvMessage.text = e.message
        } catch (e: IllegalArgumentException) {
            tvMessage.setTextColor(getColor(R.color.red))
            tvMessage.text = e.message
        } catch (e: Exception) {
            tvMessage.setTextColor(getColor(R.color.red))
            tvMessage.text = "Invalid input!"
        } finally {
            updateBalance()
            etAmount.text.clear()
        }
    }

    private fun updateBalance() {
        tvBalance.text = "Current Balance: $${String.format("%.2f", account.getBalance())}"
    }
}
