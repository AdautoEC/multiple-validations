package com.example.multiplevalidations

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.multiplevalidations.mask.CNPJMask
import com.example.multiplevalidations.mask.CPFMask
import com.example.multiplevalidations.mask.PhoneMask

class ValidationWatcher(private val edt: EditText) : TextWatcher {
    /*
        * CELULAR(DDD + DDI) = 11
        * CPF = 11
        * CNPJ = 14
        * EMAIL= @
        * CHAVE ALEATORIA = ?
    */
    private var finalText = ""
    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        if (hasOnlyNumbers(getOnlyNumbers(text.toString()))) {
            Log.d("LOG", "[IF] hasOnlyNumbers: ${getOnlyNumbers(text.toString())}")
            when (text?.length) {
                11 -> {
                    //  CPF ou CELULAR
                    if (text.startsWith("+55")) {
                        //  CELULAR
                        Log.d("LOG", "CELULAR")
                        finalText = getOnlyNumbers(finalText)
                        finalText = PhoneMask.mask(text.toString())
                        edt.setText(finalText)
                        return
                    } else {
                        //  CPF
                        finalText = getOnlyNumbers(finalText)
                        finalText = CPFMask.mask(text.toString())
                        edt.setText(finalText)
                        return
                    }
                }
                14 -> {
                    //  CNPJ
                    Log.d("LOG", "CNPJ")
                    finalText = getOnlyNumbers(finalText)
                    finalText = CNPJMask.mask(text.toString())
                    return
                }
                else -> {
                    //  CHAVE ALEATORIA
                    Log.d("LOG", "CHAVE ALEATORIA")
//                    PhoneMask.unmask(text.toString(), true)
//                    CPFMask.unmask(text.toString())
//                    CNPJMask.unmask(text.toString())
//                    return
                    finalText = text.toString()
//                    Log.d("LOG", "[IF] finalText: $finalText")
                    return
                }
            }
        } else {
            Log.d("LOG", "[ELSE] hasOnlyNumbers: ${text.toString()}")
            finalText = text.toString()
        }
        //edt.setText(finalText)
    }

    override fun afterTextChanged(text: Editable?) {
    }

    fun getOnlyNumbers(str: String): String {
        val textUnmasked = str.replace("[.]".toRegex(), "")
            .replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "")
            .replace("[(]".toRegex(), "")
            .replace("[ ]".toRegex(), "")
            .replace("[+]".toRegex(), "")
            .replace("[)]".toRegex(), "")
        return textUnmasked
    }

    fun hasOnlyNumbers(str: String): Boolean {
        val textUnmasked = getOnlyNumbers(str)
        return textUnmasked.all { it in '0'..'9' }
    }

}