package com.nayab.banktransitionstimulator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var amountInput: EditText
    private lateinit var cardBalanceText: TextView
    private lateinit var messageText: TextView

    private val account = BankAccount()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // ← your new layout

        // Bind views from your new layout
        amountInput = findViewById(R.id.etAmount)
        cardBalanceText = findViewById(R.id.tvBalance)
        messageText = findViewById(R.id.tvMessage) // optional - add this TextView if you want messages

        findViewById<MaterialButton>(R.id.btnDeposit).setOnClickListener {
            performTransaction(true)
        }

        findViewById<MaterialButton>(R.id.btnWithdraw)?.setOnClickListener {
            performTransaction(false)
        }

        updateBalance()
    }

    private fun performTransaction(isDeposit: Boolean) {
        val input = amountInput.text.toString()
        try {
            val amount = input.toDouble()

            if (isDeposit) account.deposit(amount)
            else account.withdraw(amount)

            messageText.setTextColor(getColor(R.color.primaryDark))
            messageText.text = if (isDeposit) "Deposited $$amount" else "Withdrew $$amount"

        } catch (e: InsufficientBalanceException) {
            messageText.setTextColor(getColor(R.color.red))
            messageText.text = e.message
        } catch (e: IllegalArgumentException) {
            messageText.setTextColor(getColor(R.color.red))
            messageText.text = e.message
        } catch (e: Exception) {
            messageText.setTextColor(getColor(R.color.red))
            messageText.text = "Invalid input!"
        } finally {
            updateBalance()
            amountInput.text.clear()
        }
    }

    private fun updateBalance() {
        val balanceText = "$${String.format("%.2f", account.getBalance())}"
        cardBalanceText.text = balanceText
    }
}
