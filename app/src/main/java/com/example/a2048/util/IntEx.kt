package com.example.a2048.util

fun Int.getDegree(): Int {
    var degree = 0
    var amount = this

    while (amount > 0) {
        degree++;
        amount /= 2
    }
    return degree
}