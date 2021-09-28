package com.example.multiplevalidations.extension

import android.util.Log
import java.lang.StringBuilder

fun String.toCPFMask(): String {
    val cpf: String = this
    val builder = StringBuilder()
    return try {
        builder.append(this.substring(0, 3))
        builder.append(".")
        builder.append(this.substring(3, 6))
        builder.append(".")
        builder.append(this.substring(6, 9))
        builder.append("-")
        builder.append(this.substring(9, 11))
        builder.toString()
    } catch (e: Exception) {
        cpf
    }
}

fun String.toCNPJMask(): String {
    val cnpj = this
    val builder = StringBuilder()
    return try {
        builder.append(cnpj.substring(0, 2))
        builder.append(".")
        builder.append(cnpj.substring(2, 5))
        builder.append(".")
        builder.append(cnpj.substring(5, 8))
        builder.append("/")
        builder.append(cnpj.substring(8, 12))
        builder.append("-")
        builder.append(cnpj.substring(12, 14))
        builder.toString()
    } catch (e: Exception) {
        cnpj
    }
}

fun String.toPhoneMask(): String {
    val phone = this
    val prefix: String
    return try {
        val builder = StringBuilder()
        if (!this.startsWith("+55")) {
            prefix = "+55("
            builder.append(prefix)
            builder.append(phone.substring(0, 2))
            builder.append(")")
            builder.append(phone.substring(2, 7))
            builder.append("-")
            builder.append(phone.substring(7, 11))
            builder.toString()
        } else {
            prefix = ""
            builder.append(prefix)
            builder.append(phone.substring(0, 2))
            builder.append(") ")
            builder.append(phone.substring(2, 7))
            builder.append("-")
            builder.append(phone.substring(7, 11))
            builder.toString()
        }
    } catch (e: Exception) {
        phone
    }
}

fun String.toChaveAleatoria(): String {
    val builder = StringBuilder()
    return try {
        builder.append(this.substring(0, 8))
        builder.append("-")
        builder.append(this.substring(8, 12))
        builder.append("-")
        builder.append(this.substring(12, 16))
        builder.append("-")
        builder.append(this.substring(16, 20))
        builder.append("-")
        builder.append(this.substring(20, 32))
        builder.toString()
    } catch (e: Exception) {
        this
    }
}