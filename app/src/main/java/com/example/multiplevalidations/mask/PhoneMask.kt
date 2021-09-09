package com.example.multiplevalidations.mask

import java.lang.StringBuilder

class PhoneMask {
    companion object {
        fun mask(value: String?, withDDI: Boolean = false): String {
            val phone = value?.removePrefix("+")?.removePrefix("55")
            val prefix = if (withDDI) "+55 (" else "("
            return try {
                val builder = StringBuilder()
                builder.append(prefix)
                builder.append(phone?.substring(0, 2))
                builder.append(") ")
                builder.append(phone?.substring(2, 7))
                builder.append("-")
                builder.append(phone?.substring(7, 11))
                builder.toString()
            } catch (e: Exception) {
                phone ?: ""
            }
        }

        fun unmask(value: String?, withDDI: Boolean = false): String {
            val phone =
                value?.removePrefix("+")
                    ?.removePrefix("55")
                    ?.replace("(", "")
                    ?.replace(")", "")
                    ?.replace(" ", "")
                    ?.replace("-", "")
            val prefix = if (withDDI) "55" else ""
            return prefix + phone
        }
    }
}