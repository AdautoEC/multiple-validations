package com.example.multiplevalidations.extension

import android.util.Log
import java.util.regex.Pattern

fun String.removeAllFormatting(): String {
    var textUnmasked = this
    textUnmasked = textUnmasked.replace(".", "")
    if (this.startsWith("+55"))
        textUnmasked = textUnmasked.replace("+55", "")
    textUnmasked = textUnmasked.replace(",", "")
    textUnmasked = textUnmasked.replace("-", "")
    textUnmasked = textUnmasked.replace("/", "")
    textUnmasked = textUnmasked.replace("(", "")
    textUnmasked = textUnmasked.replace(" ", "")
    textUnmasked = textUnmasked.replace("+", "")
    textUnmasked = textUnmasked.replace(")", "")
    return textUnmasked
}

fun String.hasOnlyNumbers(): Boolean {
    val text = this.removeAllFormatting()
    val hasNumber = Pattern.matches("\\d+", text)
    val hasLetter = Pattern.matches("[a-zA-Z]+", text)
//    Log.d("LOG", " ")
//    Log.d("LOG", "[hasOnlyNumbers] hasNumber: $hasNumber | hasLetter: $hasLetter | text: $text")
    return hasNumber && !hasLetter
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

fun String.isValidCellphone(): Boolean {
    val validDDD =
        "11|12|13|14|15|16|17|18|19|21|22|24|27|28|31|32|33|34|35|37|38|41|42|43|44|45|46|47|48|49|51|53|54|55|61|62|63|64|65|66|67|68|69|71|73|74|75|77|79|81|82|83|84|85|86|87|88|89|91|92|93|94|95|96|97|98|99"
    val phoneRegex = "(?:(?:\\+|00)?(55))?($validDDD)((9[6-9]\\d{3})-?(\\d{4}))\$"
    return Pattern.matches(phoneRegex, this)
}

fun String.isValidChaveAleatoria(): Boolean {
    val text = this.removeAllFormatting()
    return Pattern.matches("[a-zA-Z0-9]+", text)
}