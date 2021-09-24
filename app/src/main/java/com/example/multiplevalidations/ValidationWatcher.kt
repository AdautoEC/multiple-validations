package com.example.multiplevalidations

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import com.example.multiplevalidations.extension.*

class ValidationWatcher : TextWatcher {
    private var cleanText = ""
    private var textMasked = ""
    var shouldEdit = false
    var setText: (String) -> Unit = {}

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(text: Editable?) {
//        shouldEdit = true
    }

    override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
        val text = cs.toString().removeAllFormatting()
        cleanText = text

//        Log.d("LOG", " ")
//        if (shouldEdit) {
//            Log.d("LOG", "shouldEdit")
            handlingValidation(text)
//        } else {
//            Log.d("LOG", "!shouldEdit")
//            return
//        }
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

    private fun emailValidation(text: String) {
        Log.d("LOG", "EMAIL")
        if (text.isValidEmail()) {
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
        setText.invoke(textMasked)
        return
    }

    private fun setMask(text: String) {
        Log.d("LOG", "setMask: $text")
        textMasked = text
        setText.invoke(textMasked)
        return
    }
}