package com.example.myfridge

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.myfridge.model.Product
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        fun newInstance(editText: EditText, product: Product? = null): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.editText = editText
            fragment.product = product
            return fragment
        }
    }

    private var editText: EditText? = null
    private var product: Product? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // Use the current date as the default date in the picker.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        return DatePickerDialog(requireContext(), this, year, month, day)

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val dateString = "${day.toString().padStart(2,'0')}.${(month+1).toString().padStart(2,'0')}.${year}"
//        val activity = activity as ActivityAddPersistentDataTest?
//        activity?.findViewById<EditText>(R.id.editTextNewProductDate)?.setText(dateString)
        editText?.setText(dateString)
//        product?.date = dateString

        return
    }
}

