package com.example.multiplevalidations.mask

import java.lang.StringBuilder

class CNPJMask {
    companion object {
        //96.534.094/0001-58
        fun mask(value: String?): String {
            val cnpj = value
            return try {
                val builder = StringBuilder()
                builder.append(cnpj?.substring(0, 2))
                builder.append(".")
                builder.append(cnpj?.substring(2, 5))
                builder.append(".")
                builder.append(cnpj?.substring(5, 8))
                builder.append("/")
                builder.append(cnpj?.substring(8, 12))
                builder.append("-")
                builder.append(cnpj?.substring(12, 14))
                builder.append(builder.toString())
                builder.toString()
            } catch (e: Exception) {
                cnpj ?: ""
            }
        }

        fun unmask(value: String?): String {
            val cnpj =
                value?.replace(".", "")
                    ?.replace("/", "")
            return cnpj ?: ""
        }

        fun isCNPJ(value: String): Boolean {
            if (value.isEmpty()) return false

            val numbers = value.filter { it.isDigit() }.map {
                it.toString().toInt()
            }

            if (numbers.size != 14) return false

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