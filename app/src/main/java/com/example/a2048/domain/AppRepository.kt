package com.example.a2048.domain

import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import com.example.a2048.R
import com.example.a2048.data.AppPref
import com.example.a2048.data.SideEnum

class AppRepository {
    private var score: Int = 0
    private var maxValue = AppPref.getTarget()
    private var matrix = AppPref.getMatrix()
    private val appPref: AppPref = AppPref.getInstance()
    private lateinit var lastAction: SideEnum
    private var record = AppPref.getRecord()

    private var listener: ((Array<Array<Int>>, Int, Int) -> Unit)? = null

    private val oldmatrix = arrayOf(
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0),
        arrayOf(0, 0, 0, 0)
    )


    private var undoMatrix = AppPref.getMatrixUndo()
    private var oldScore = AppPref.getScoreUndo()
    private var oldRecord = AppPref.getRecordUndo()

    fun submitListener(block: ((Array<Array<Int>>, Int, Int) -> Unit)) {
        this.listener = block
    }

    init {
        addFirst()
    }

    private fun addFirst() {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] != 0) return
            }
        }
        addElement()
        addElement()
    }


    fun getMatrix(): Array<Array<Int>> = matrix

    private fun addElement() {
        val elements = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] == 0) elements.add(Pair(i, j))
            }
        }
        elements.shuffle()
        if (elements.size == 0) return

        val element = elements[0]
        matrix[element.first][element.second] = 2
    }

    fun replay() {
        matrix = arrayOf(
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0),
            arrayOf(0, 0, 0, 0)
        )
        score = 0
        maxValue = 2
        addElement()
        addElement()
    }


    private fun clearMatrix() {
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                matrix[i][j] = 0
            }
        }
    }


    private fun horizantlChanged(): Boolean {
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                if (matrix[i][j] != oldmatrix[i][j]) {
                    return true
                }
            }
        }
        return false
    }


    fun moveLeft() {
        lastAction = SideEnum.LEFT
        var isSwipe = false
        oldScore = score
        oldRecord = record
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldmatrix[i][j] = matrix[i][j]
            }
        }

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
                        isSwipe = true

                        amounts[amounts.lastIndex] = matrix[i][j] * 2
                        score += amounts[amounts.lastIndex]
                        if (amounts[amounts.lastIndex] > maxValue) maxValue =
                            amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[i][j])
                    }
                }
                matrix[i][j] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[i][j] = amounts[j]
            }
        }
        if (isSwipe) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    undoMatrix[i][j] = oldmatrix[i][j]
                }
            }
        }

        if (horizantlChanged()) {
            addElement()
        }
    }

    fun moveRight() {
        lastAction = SideEnum.RIGHT
        var isSwipe = false
        oldScore = score
        oldRecord = record
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldmatrix[i][j] = matrix[i][j]
            }
        }
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[3 - i][3 - j] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[3 - i][3 - j])
                } else {
                    if (amounts.last() == matrix[3 - i][3 - j] && state) {
                        state = false
                        isSwipe = true
                        amounts[amounts.lastIndex] = matrix[3 - i][3 - j] * 2
                        score += amounts[amounts.lastIndex]
                        if (amounts[amounts.lastIndex] > maxValue) maxValue =
                            amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[3 - i][3 - j])
                    }
                }
                matrix[3 - i][3 - j] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[3 - i][3 - j] = amounts[j]
            }
        }
        if (isSwipe) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    undoMatrix[i][j] = oldmatrix[i][j]
                }
            }
        }

        if (horizantlChanged()) {
            addElement()
        }
    }

    fun moveUp() {
        lastAction = SideEnum.UP
        var isSwipe = false
        oldScore = score
        oldRecord = record
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldmatrix[i][j] = matrix[i][j]
            }
        }
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[j][i] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[j][i])
                } else {
                    if (amounts.last() == matrix[j][i] && state) {
                        state = false
                        isSwipe = true
                        amounts[amounts.lastIndex] = matrix[j][i] * 2
                        score += amounts[amounts.lastIndex]
                        if (amounts[amounts.lastIndex] > maxValue) maxValue =
                            amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[j][i] = amounts[j]
            }
        }
        if (isSwipe) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    undoMatrix[i][j] = oldmatrix[i][j]
                }
            }
        }

        if (horizantlChanged()) {
            addElement()
        }
    }

    fun moveDown() {
        lastAction = SideEnum.DOWN
        var isSwipe = false
        oldScore = score
        oldRecord = record
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                oldmatrix[i][j] = matrix[i][j]
            }
        }
        for (i in matrix.indices) {
            var state = true
            val amounts: ArrayList<Int> = arrayListOf()
            for (j in 0 until matrix[i].size) {
                if (matrix[3 - j][i] == 0) continue

                if (amounts.isEmpty()) {
                    amounts.add(matrix[3 - j][i])
                } else {
                    if (amounts.last() == matrix[3 - j][i] && state) {
                        state = false
                        isSwipe = true
                        amounts[amounts.lastIndex] = matrix[3 - j][i] * 2
                        score += amounts[amounts.lastIndex]
                        if (amounts[amounts.lastIndex] > maxValue) maxValue =
                            amounts[amounts.lastIndex]
                    } else {
                        state = true
                        amounts.add(matrix[3 - j][i])
                    }
                }
                matrix[3 - j][i] = 0
            }
            for (j in 0 until amounts.size) {
                matrix[3 - j][i] = amounts[j]
            }
        }
        if (isSwipe) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    undoMatrix[i][j] = oldmatrix[i][j]
                }
            }
        }
        if (horizantlChanged()) {
            addElement()
        }
    }

    fun getScore(): Int {
        return score
    }


    fun saveData() {
        if (record <= score)
            AppPref.setRecord(record)
        AppPref.setScore(score)
        AppPref.setMatrix(matrix)
        AppPref.setTarget(maxValue)
        AppPref.setRecordUndo(oldRecord)
        AppPref.setScoreUndo(oldScore)
    }

     fun undo() {
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                matrix[i][j] = undoMatrix[i][j]
            }
        }
        score = oldScore
        record = oldRecord
    }


     fun getRecord(): Int {
        if (record < score)
            record = score
        return record
    }


}

