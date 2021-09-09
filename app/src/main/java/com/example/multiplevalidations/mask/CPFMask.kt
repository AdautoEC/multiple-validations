package com.example.multiplevalidations.mask

import android.util.Log
import java.lang.StringBuilder

class CPFMask {
    companion object {
        fun mask(value: String?): String {
            Log.d("LOG", "CPF[inicio]: $value")
            val cpf : String = value ?: ""
            return try {
                val builder = StringBuilder()
                builder.append(value?.substring(0, 3))
                builder.append(".")
                builder.append(value?.substring(3, 6))
                builder.append(".")
                builder.append(value?.substring(6, 9))
                builder.append("-")
                builder.append(value?.substring(9, 11))
                Log.d("LOG", "CPF[fim]: $builder")
                builder.toString()
            } catch (e: Exception) {
                cpf
            }
        }

        fun unmask(value: String?): String {
            val cpf =
                value?.replace(".", "")
                    ?.replace("-", "")
            return cpf ?: ""
        }

        fun isCPF(value: String): Boolean {
            if (value.isEmpty()) return false

            val numbers = value.filter { it.isDigit() }.map {
                it.toString().toInt()
            }

            if (numbers.size != 11) return false

            //repeticao
            if (numbers.all { it == numbers[0] }) return false

            //digito 1
            val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
                if (it >= 10) 0 else it
            }

            val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
                if (it >= 10) 0 else it
            }

            return numbers[9] == dv1 && numbers[10] == dv2
        }
    }
}