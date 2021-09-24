package com.example.multiplevalidations

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import com.example.multiplevalidations.extension.*

class ValidationWatcher : TextWatcher {
    private var cleanText = ""
    private var textMasked = ""
    var setText: (String) -> Unit = {}

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(text: Editable?) {
        setText.invoke(textMasked)
    }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
        cleanText = text?.toString()?.removeAllFormatting() ?: ""

        when {
            Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches() -> {
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
                    cleanText.isValidPhone() -> {
                        //  CELULAR VALIDO
                        phoneValidation()
                    }
                    else -> {
                        //  CPF E CELULAR INVALIDOS
                        Log.d("LOG", "NEM CPF | NEM CPNJ")
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

    private fun emailValidation(text: CharSequence?) {
        Log.d("LOG", "EMAIL")
        if (text.toString().isValidEmail()) {
            Log.d("LOG", "EMAIL VALIDO")
        } else {
            Log.d("LOG", "EMAIL INVALIDO")
        }
    }

    private fun cpfValidation() {
        Log.d("LOG", "CPF")
        if (cleanText.isCPF()) {
            setMask(cleanText.toCPFMask())
        } else {
            Log.d("LOG", "CPF INVALIDO")
        }
    }

    private fun cnpjValidation() {
        setMask(cleanText.toCNPJMask())
    }

    private fun phoneValidation() {
        setMask(cleanText.toPhoneMask())
    }

    private fun chaveAleatoriaValidation(text: CharSequence?) {
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