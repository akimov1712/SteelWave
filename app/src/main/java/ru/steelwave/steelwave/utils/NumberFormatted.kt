package ru.steelwave.steelwave.utils

fun formatPrice(number: Int): String {
    val numberString = number.toString()

    val formattedNumber = StringBuilder()

    for (i in numberString.length - 1 downTo 0) {
        formattedNumber.append(numberString[i])

        if ((numberString.length - i) % 3 == 0 && i != 0) {
            formattedNumber.append(' ')
        }
    }

    return formattedNumber.reverse().toString()
}