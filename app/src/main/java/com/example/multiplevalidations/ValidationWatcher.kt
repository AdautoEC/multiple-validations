package com.example.multiplevalidations

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.widget.EditText
import com.example.multiplevalidations.extension.*

class ValidationWatcher(private val edt: EditText) : TextWatcher {
    private var cleanText = ""
    private var textMasked = ""

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(text: Editable?) {
        edt.removeTextChangedListener(this)
        text?.replace(0, text.length, textMasked)
        edt.addTextChangedListener(this)
    }

    override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
        val text = cs.toString().removeAllFormatting()
        cleanText = text
        handlingValidation(text)
    }

    private fun handlingValidation(text: String) {

        when {
            Patterns.EMAIL_ADDRESS.matcher(text).matches() -> {
                //  EMAIL VALIDO
                emailValidation(text)
            }
            cleanText.length == 11 && cleanText.hasOnlyNumbers() -> {
                //  CPF ou CELULAR
                when {
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
                        Log.d("LOG", "CPF E CELULAR INVALIDOS")
                        chaveInvalidaValidation()
                    }
                }
            }
            cleanText.length == 14 && cleanText.hasOnlyNumbers() -> {
                if (cleanText.isValidCNPJ()) {
                    //  CNPJ
                    cnpjValidation()
                } else {
                    //  "CNPJ INVALIDO"
                    Log.d("LOG", "CNPJ INVALIDO")
                }
            }
            cleanText.length == 32 -> {
                //  CHAVE ALEATORIA
                chaveAleatoriaValidation(text)
            }
            else -> {
                //  "CHAVE INVALIDA"
                chaveInvalidaValidation()
            }
        }
    }

    private fun emailValidation(text: String) {
        Log.d("LOG", "EMAIL")
        Log.d("LOG", "EMAIL VALIDO")
    }

    private fun cpfValidation() {
        Log.d("LOG", "CPF")
        setMask(cleanText.toCPFMask())
    }

    private fun cnpjValidation() {
        setMask(cleanText.toCNPJMask())
    }

    private fun phoneValidation() {
        setMask(cleanText.toPhoneMask())
    }

    private fun chaveAleatoriaValidation(text: String?) {
        if (text.toString().isValidChaveAleatoria()) {
            setMask(cleanText)
        } else {
            //  "Chave aleatória invalida"
            Log.d("LOG", "CHAVE ALEATORIA INVALIDA")
        }
    }

    private fun chaveInvalidaValidation() {
        Log.d("LOG", "CHAVE INVALIDA")
        cleanText = cleanText.removeAllFormatting()
        textMasked = cleanText
    }

    private fun setMask(text: String) {
        Log.d("LOG", "setMask: $text")
        textMasked = text
    }
}