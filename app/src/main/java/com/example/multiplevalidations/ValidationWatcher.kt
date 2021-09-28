package com.example.multiplevalidations

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import com.example.multiplevalidations.extension.*

class ValidationWatcher(
    private val edt: EditText,
    private val onKeyChoosed: (Pair<String?, PIXKey?>) -> Unit
) : TextWatcher {
    private var cleanText = ""
    private var textMasked = ""

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(text: Editable?) {
        edt.removeTextChangedListener(this)
        text?.replace(0, text.length, textMasked)
        edt.setSelection(text?.length ?: 0)
        edt.addTextChangedListener(this)
    }

    override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {
        val text = cs.toString()

        if (!text.hasOnlyNumbers()) {
            textMasked = text
            if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                //  EMAIL VALIDO
                emailValidation(text)
            }
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
                            chaveInvalidaValidation()
                        }
                    }
                }
                14 -> {
                    cnpjValidation()
                }
                32 -> {
                    //  CHAVE ALEATORIA
                    chaveAleatoriaValidation(text)
                }
                else -> {
                    //  "CHAVE INVALIDA"
                    chaveInvalidaValidation()
                }
            }
        }
    }

    private fun cpfAndCellphoneValidation() {
        textMasked = cleanText
        onKeyChoosed.invoke(Pair(textMasked, PIXKey.CPF_AND_CELULAR))
    }

    private fun emailValidation(text: String) {
        onKeyChoosed.invoke(Pair(text, PIXKey.EMAIL))
    }

    private fun cpfValidation() {
        setMask(cleanText.toCPFMask())
        onKeyChoosed.invoke(Pair(cleanText, PIXKey.CPF))
    }

    private fun cnpjValidation() {
        if (cleanText.isValidCNPJ()) {
            setMask(cleanText.toCNPJMask())
            onKeyChoosed.invoke(Pair(cleanText, PIXKey.CNPJ))
        } else {
            //  "CNPJ INVALIDO"
            onKeyChoosed.invoke(Pair("", PIXKey.CHAVE_INVALIDA))
        }
    }

    private fun phoneValidation() {
        setMask(cleanText.toPhoneMask())
        onKeyChoosed.invoke(Pair(cleanText, PIXKey.CELULAR))
    }

    private fun chaveAleatoriaValidation(text: String?) {
        if (text.toString().isValidChaveAleatoria()) {
            setMask(cleanText)
            onKeyChoosed.invoke(Pair(cleanText, PIXKey.CHAVE_ALEATORIA))
        } else {
            //  "Chave aleatória invalida"
            onKeyChoosed.invoke(Pair("", PIXKey.CHAVE_INVALIDA))
        }
    }

    private fun chaveInvalidaValidation() {
        cleanText = cleanText.removeAllFormatting()
        textMasked = cleanText
        onKeyChoosed.invoke(Pair("", PIXKey.CHAVE_INVALIDA))
    }

    private fun setMask(text: String) {
        textMasked = text
    }
}