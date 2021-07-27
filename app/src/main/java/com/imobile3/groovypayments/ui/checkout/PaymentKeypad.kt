package com.imobile3.groovypayments.ui.checkout

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableRow
import androidx.constraintlayout.widget.ConstraintLayout
import com.imobile3.groovypayments.R
import com.imobile3.groovypayments.databinding.CheckoutPayWithCashLayoutBinding
import com.imobile3.groovypayments.rules.CurrencyRules
import java.util.*

class PaymentKeypad @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by lazy { CheckoutPayWithCashLayoutBinding.bind(this) }

    init {
        inflate(context, R.layout.checkout_pay_with_cash_layout, this)
    }

    fun getTotalAmount() : Long {
        return binding.labelCashAmount.text.filter {it.isDigit()}.toString().toLong()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding.keypad.run {
            // go through each child[TableRow] in the keypad
            for (i in 0 until root.childCount) {
                // get the child[TableRow]
                val row = root.getChildAt(i) as TableRow
                for (j in 0 until row.childCount)
                // get the views in each child[TableRow]
                    row.getChildAt(j).setOnClickListener { v ->
                        when {
                            v is ImageButton -> updateDisplay(backSpace = true)
                            (v as Button).text == context.getString(R.string.keypad_00) -> updateDisplay()
                            else -> updateDisplay(v.text.toString())
                        }
                    }
            }
        }
    }

    /**
     * Handles updating the `labelCashAmount` based on action taken by user
     * @param amount the value to be appended to the display
     * @param backSpace removes last digit in the display
     */
    private fun updateDisplay(
        amount: String = "", backSpace: Boolean = false
    ) = with(binding.labelCashAmount) {
        if (amount.isBlank() && !backSpace) text =
            CurrencyRules().getFormattedAmount(0, Locale.US)
        else {
            val amt = "$text$amount".run {
                if (backSpace) return@run dropLast(1)
                return@run this
            }.filter { txt -> txt.isDigit() }.toLongOrNull()
            if (amt != null) text = CurrencyRules().getFormattedAmount(amt, Locale.US)
        }
    }
}