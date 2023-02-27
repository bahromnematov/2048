package com.example.a2048.data

import android.content.Context
import android.content.SharedPreferences

class AppPref private constructor() {


    var score: String
        set(value) = pref.edit().putString("Score", value).apply()
        get() = pref.getString("Score", "")!!

    companion object {
        private lateinit var pref: SharedPreferences
        private lateinit var instance: AppPref

        fun init(context: Context) {
            pref = context.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
            instance = AppPref()
        }

        fun getInstance(): AppPref = instance


        fun setMatrix(matrix: Array<Array<Int>>) {
            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    pref.edit().putInt("matrix|$i|$j", matrix[i][j]).apply()
                }
            }
        }

        fun getMatrix(): Array<Array<Int>> {
            val matrix: Array<Array<Int>> = arrayOf(
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0)
            )

            for (i in matrix.indices) {
                for (j in 0 until matrix[i].size) {
                    matrix[i][j] = pref.getInt("matrix|$i|$j", 0)
                }
            }
            return matrix
        }

        fun getMatrixUndo(): Array<Array<Int>> {
            val matrixUndo: Array<Array<Int>> = arrayOf(
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0),
                arrayOf(0, 0, 0, 0)
            )
            for (i in matrixUndo.indices) {
                for (j in 0 until matrixUndo[i].size) {
                    matrixUndo[i][j] = pref.getInt("matrixUndo|$i|$j", 0)
                }
            }
            return matrixUndo
        }


//        var score: String
//            set(value) = pref.edit().putString("Score", value).apply()
//            get() = pref.getString("Score", "")!!


        fun setRecord(record: Int) {
            pref.edit().putInt("record", record).apply()
        }

        fun getRecord(): Int {
            return pref.getInt("record", 0)
        }


        fun setRecordUndo(recordUndo: Int) {
            pref.edit().putInt("recordUndo", recordUndo).apply()
        }

        fun getRecordUndo(): Int {
            return pref.getInt("recordUndo", 0)
        }


        fun setScore(score: Int) {
            pref.edit().putInt("score", score).apply()
        }

        fun getScore(): Int {
            return pref.getInt("score", 0)
        }
        fun setScoreUndo(scoreUndo: Int) {
            pref.edit().putInt("scoreUndo", scoreUndo).apply()
        }

        fun getScoreUndo(): Int {
            return pref.getInt("scoreUndo", 0)
        }

        fun setTarget(target: Int) {
            pref.edit().putInt("target", target).apply()
        }

        fun getTarget(): Int {
            return pref.getInt("target", 0)
        }

    }

}
