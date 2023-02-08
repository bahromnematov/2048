package com.example.a2048.domain

import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.example.a2048.R
import com.example.a2048.data.AppPref

class AppRepository {
    private var score: Int = 0
    private val appPref: AppPref = AppPref.getInstance()

    private var listener: ((Array<Array<Int>>, Int, Int) -> Unit)? = null

    private val matrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )

    fun submitListener(block: ((Array<Array<Int>>, Int, Int) -> Unit)) {
        this.listener = block
    }

    init {
        addNewElement()
        addNewElement()
    }

    fun getMatrix(): Array<Array<Int>> = matrix

    private fun addNewElement() {
        val list = ArrayList<Pair<Int, Int>>()

        for (i in 0 until 4) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) list.add(Pair(i, j))
            }
        }
        list.shuffle()
        if (list.size == 0) return
        val element = list[0]
        matrix[element.first][element.second] = 2
    }

    fun replay() {
        clearMatrix()
        addNewElement()
        addNewElement()
    }


    private fun clearMatrix() {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                matrix[i][j] = 0
            }
        }
    }


    fun actionToLeft() { // chapga yurish
        var canAdd = false
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {

                if (matrix[i][j] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[i][j])


                } else {
                    if (amounts.last() == matrix[i][j] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[i][j] * 2
                        score += matrix[i][j] * 2
                    } else {
                        state = true
                        amounts.add(matrix[i][j])
                    }
                }

                matrix[i][j] = 0

            }

            for (j in 0 until amounts.size) {
                if (matrix[i][j] != amounts[j]) {
                    canAdd = true
                }
                matrix[i][j] = amounts[j]
            }
        }
        if (canAdd)
            addNewElement()


    }

    fun actionToRight() { //o'ngga yurish
        var canAdd = false
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[matrix.size - 1 - i][matrix.size - 1 - j] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[matrix.size - 1 - i][matrix.size - 1 - j])
                } else {
                    if (amounts.last() == matrix[matrix.size - 1 - i][matrix.size - 1 - j] && state) {
                        state = false
                        amounts[amounts.lastIndex] =
                            matrix[matrix.size - 1 - i][matrix.size - 1 - j] * 2
                        score += matrix[matrix.size - 1 - i][matrix.size - 1 - j] * 2
                    } else {
                        state = true
                        amounts.add(matrix[matrix.size - 1 - i][matrix.size - 1 - j])
                    }
                }
                matrix[matrix.size - 1 - i][matrix.size - 1 - j] = 0
            }

            for (j in 0 until amounts.size) {
                if (matrix[matrix.size - 1 - i][matrix.size - 1 - j] != amounts[j]) {
                    canAdd = true
                }
                matrix[matrix.size - 1 - i][matrix.size - 1 - j] = amounts[j]

            }
        }
        if (canAdd)
            addNewElement()

    }

    fun actionToUP() {              //  pastga yurish
        var canAdd = false
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[j][i] == 0) continue;

                if (amounts.isEmpty()) {
                    amounts.add(matrix[j][i])

                } else {
                    if (amounts.last() == matrix[j][i] && state) {
                        state = false
                        amounts[amounts.lastIndex] = matrix[j][i] * 2
                        score += matrix[i][j] * 2
                    } else {
                        state = true
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }

            for (j in 0 until amounts.size) {
                if (matrix[j][i] != amounts[j]) {
                    canAdd = true
                }
                matrix[j][i] = amounts[j]
            }
        }
        if (canAdd)
            addNewElement()

    }

    fun actionToDown() {           // tepaga yurish
        var canAdd = false
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[matrix.size - 1 - j][matrix.size - 1 - i] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[matrix.size - 1 - j][matrix.size - 1 - i])

                } else {
                    if (amounts.last() == matrix[matrix.size - 1 - j][matrix.size - 1 - i] && state) {
                        state = false
                        amounts[amounts.lastIndex] =
                            matrix[matrix.size - 1 - j][matrix.size - 1 - i] * 2
                        score += matrix[matrix.size - 1 - j][matrix.size - 1 - i] * 2

                    } else {
                        state = true
                        amounts.add(matrix[matrix.size - 1 - j][matrix.size - 1 - i])

                    }
                }
                matrix[matrix.size - 1 - j][matrix.size - 1 - i] = 0
            }

            for (j in 0 until amounts.size) {
                if (matrix[matrix.size - 1 - j][matrix.size - 1 - i] != amounts[j]) {
                    Log.d(
                        "TTT",
                        "${matrix[matrix.size - 1 - j][matrix.size - 1 - i]}   ${amounts[j]}"
                    )
                    canAdd = true
                }
                matrix[matrix.size - 1 - j][matrix.size - 1 - i] = amounts[j]
            }
        }
        if (canAdd)
            addNewElement()
    }

    fun getScore():Int {
        return score
    }

    fun setScore(score:Int){
        this.score=score
    }


}

