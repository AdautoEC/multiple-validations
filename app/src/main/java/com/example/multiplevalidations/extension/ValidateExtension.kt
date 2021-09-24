package com.example.multiplevalidations.extension

import java.util.regex.Pattern

fun String.removeAllFormatting(): String {
    var textUnmasked = this
    textUnmasked = textUnmasked.replace(".", "")
    if (this.startsWith("+55"))
        textUnmasked = textUnmasked.replace("+55", "")
    textUnmasked = textUnmasked.replace("-", "")
    textUnmasked = textUnmasked.replace("/", "")
    textUnmasked = textUnmasked.replace("(", "")
    textUnmasked = textUnmasked.replace(" ", "")
    textUnmasked = textUnmasked.replace("+", "")
    textUnmasked = textUnmasked.replace(")", "")
    return textUnmasked
}

fun String.hasOnlyNumbers(): Boolean {
    val textUnmasked = this.removeAllFormatting()
    return textUnmasked.all { it in '0'..'9' }
}

fun String.isValidCPF(): Boolean {
    if (this.isEmpty()) return false

    val numbers = this.filter { it.isDigit() }.map {
        it.toString().toInt()
    }

    if (numbers.size != 11) return false

    if (numbers.all { it == numbers[0] }) return false

    val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
        if (it >= 10) 0 else it
    }

    val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
        if (it >= 10) 0 else it
    }

    return numbers[9] == dv1 && numbers[10] == dv2
}

fun String.isValidCNPJ(): Boolean {
    if (this.isEmpty()) return false
    val numbers = this.filter { it.isDigit() }.map {
        it.toString().toInt()
    }
    if (numbers.size != 14) return false

    if (numbers.all { it == numbers[0] }) return false

    val dv1 = ((0..8).sumOf { (it + 1) * numbers[it] }).rem(11).let {
        if (it >= 10) 0 else it
    }

    val dv2 = ((0..8).sumOf { it * numbers[it] }.let { (it + (dv1 * 9)).rem(11) }).let {
        if (it >= 10) 0 else it
    }

    return numbers[9] == dv1 && numbers[10] == dv2
}

fun String.isValidPhone(): Boolean {
    val invalidDDI = "10|20|23|25|26|29|30|36|39|40|50|52|56|57|58|59|60|70|72|76|78|80|90"
    val phoneRegex = "^(\\+55)?([(]?(?:$invalidDDI)[)]?|(?:$invalidDDI))9[5-9]\\d{4}[-]?\\d{4}$"
    return !Pattern.matches(phoneRegex, this)
}

fun String.isValidChaveAleatoria(): Boolean {
    val chaveAleatoriaRegex = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}"
    return Pattern.matches(chaveAleatoriaRegex, this)
}

fun String.isValidEmail(): Boolean {
    val emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
    return Pattern.matches(emailRegEx, this)
}

fun String.isCPF(): Boolean {
    val cpfRegex = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}-?[0-9]{2}"
    return Pattern.matches(cpfRegex, this)
}