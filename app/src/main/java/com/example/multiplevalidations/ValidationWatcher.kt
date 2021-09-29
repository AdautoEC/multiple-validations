package com.example.multiplevalidations

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import com.example.multiplevalidations.extension.*

class ValidationWatcher(
    private val edt: EditText
) : TextWatcher {
    private var cleanText = ""
    private var textMasked = ""
    var onKeyChoosed: ((Pair<String, PIXKey>) -> Unit)? = null
    var oldKey: PIXKey = PIXKey.CHAVE_INVALIDA
    var selection: Int = 0

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(text: Editable?) {
        edt.removeTextChangedListener(this)
        if(text?.length != cleanText.length || textMasked.length != cleanText.length)
            edt.setSelection(text?.length ?: 0)
        text?.replace(0, text.length, textMasked)
        edt.addTextChangedListener(this)
    }

    override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
        val text = cs.toString()

        if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            //  EMAIL VALIDO
            emailValidation(text)
        } else {
            cleanText = text.removeAllFormatting()
            when (cleanText.length) {
                11 -> {
                    //  CPF ou CELULAR
                    when {
                        cleanText.isValidCPF() && cleanText.isValidCellphone() -> {
                            //  CPF E CELULAR VALIDOS
                            cpfAndCellphoneValidation()
                        }
                        cleanText.isValidCPF() -> {
                            //  CPF VALIDO
                            cpfValidation()
                        }
                        cleanText.isValidCellphone() -> {
                            //  CELULAR VALIDO
                            phoneValidation()
                        }
                        else -> {
                            //  CPF E CELULAR INVALIDOS
                            chaveInvalidaValidation(text)
                        }
                    }
                }
                14 -> {
                    cnpjValidation(text)
                }
                32 -> {
                    //  CHAVE ALEATORIA
                    chaveAleatoriaValidation(text)
                }
                else -> {
                    //  "CHAVE INVALIDA"
                    chaveInvalidaValidation(text)
                }
            }
        }
    }

    private fun cpfAndCellphoneValidation() {
        setMask(cleanText)
        oldKey = PIXKey.CPF_AND_CELULAR
        onKeyChoosed?.invoke(Pair(textMasked, PIXKey.CPF_AND_CELULAR))
    }

    private fun emailValidation(text: String) {
        setMask(text)
        oldKey = PIXKey.EMAIL
        onKeyChoosed?.invoke(Pair(text, PIXKey.EMAIL))
    }

    private fun cpfValidation() {
        setMask(cleanText.toCPFMask())
        oldKey = PIXKey.CPF
        onKeyChoosed?.invoke(Pair(cleanText, PIXKey.CPF))
    }

    private fun cnpjValidation(text: String) {
        if (cleanText.isValidCNPJ()) {
            setMask(cleanText.toCNPJMask())
            oldKey = PIXKey.CNPJ
            onKeyChoosed?.invoke(Pair(cleanText, PIXKey.CNPJ))
        } else {
            //  "CNPJ INVALIDO"
            chaveInvalidaValidation(text)
        }
    }

    private fun phoneValidation() {
        setMask(cleanText.toPhoneMask())
        oldKey = PIXKey.CELULAR
        onKeyChoosed?.invoke(Pair(cleanText, PIXKey.CELULAR))
    }

    private fun chaveAleatoriaValidation(text: String) {
        if (text.isValidChaveAleatoria()) {
            setMask(cleanText.toChaveAleatoria())
            oldKey = PIXKey.CHAVE_ALEATORIA
            onKeyChoosed?.invoke(Pair(cleanText, PIXKey.CHAVE_ALEATORIA))
        } else {
            //  "Chave aleatória invalida"
            chaveInvalidaValidation(text)
        }
    }

    private fun chaveInvalidaValidation(text: String) {
        cleanText = text.removeAllFormatting()
        textMasked = if(oldKey != PIXKey.CHAVE_INVALIDA && oldKey != PIXKey.EMAIL){
            cleanText
        }else{
            text
        }
        oldKey = PIXKey.CHAVE_INVALIDA
        onKeyChoosed?.invoke(Pair("", PIXKey.CHAVE_INVALIDA))
    }

    private fun setMask(text: String) {
        textMasked = text
    }
}