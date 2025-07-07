package com.nayab.banktransitionstimulator

class BankAccount {
    private var balance: Double = 0.0

    fun deposit(amount: Double) {
        if (amount <= 0) throw IllegalArgumentException("Amount must be greater than 0")
        balance += amount
    }

    fun withdraw(amount: Double) {
        if (amount <= 0) throw IllegalArgumentException("Amount must be greater than 0")
        if (amount > balance) throw InsufficientBalanceException("Insufficient balance!")
        balance -= amount
    }

    fun getBalance(): Double = balance
}
