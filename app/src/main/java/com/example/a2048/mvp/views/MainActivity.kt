package com.example.a2048.mvp.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.get
import com.example.a2048.R
import com.example.a2048.data.AppPref
import com.example.a2048.data.SideEnum
import com.example.a2048.domain.AppRepository
import com.example.a2048.util.TinyDB
import com.example.a2048.util.MyTouchListener

class MainActivity : AppCompatActivity() {
    private val elements = ArrayList<TextView>(16)
    private val repository = AppRepository()
    private val colorArr = HashMap<Int, Int>()
    private val pref = AppPref.getInstance()
    private lateinit var score: TextView
    private lateinit var bestScore: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadViews()
        loadData()
        describeMatrix()
        score = findViewById(R.id.step)
        bestScore = findViewById(R.id.bestScore)

    }

    private fun loadData() {
        colorArr[0] = R.drawable.bg_item_0
        colorArr[2] = R.drawable.bg_item_2
        colorArr[4] = R.drawable.bg_item_4
        colorArr[8] = R.drawable.bg_item_8
        colorArr[16] = R.drawable.bg_item_16
        colorArr[32] = R.drawable.bg_item_32
        colorArr[64] = R.drawable.bg_item_64
        colorArr[128] = R.drawable.bg_item_128
        colorArr[256] = R.drawable.bg_item_256
        colorArr[512] = R.drawable.bg_item_512
        colorArr[1024] = R.drawable.bg_item_1024
        colorArr[2048] = R.drawable.bg_item_2048
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun loadViews() {


        val mainContainer = findViewById<LinearLayout>(R.id.mainView)
        val myTouchListener = MyTouchListener(this)
        myTouchListener.setDetectSideListener {
            when (it) {
                SideEnum.RIGHT -> {
                    repository.actionToRight()
                    describeMatrix()
                }
                SideEnum.LEFT -> {
                    repository.actionToLeft()
                    describeMatrix()
                }
                SideEnum.UP -> {
                    repository.actionToUP()
                    describeMatrix()
                }
                SideEnum.DOWN -> {
                    repository.actionToDown()
                    describeMatrix()
                }
            }
            score.text = repository.getScore().toString()
            pref.score=repository.getScore().toString()
            bestScore.text=pref.score

            if (checkToLose()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Siz yutqazdiz")
                builder.setMessage("Yana qayta uynashni hohlaysizmi")
                builder.setIcon(R.drawable.info)

                builder.setPositiveButton("Yes") { _, _ ->
                    repository.replay()
                    describeMatrix()
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.cancel()

                }
                builder.setNegativeButton("No") { _, _ ->
                    finish()

                }
                val alertDialog: AlertDialog = builder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
            }
        }
        mainContainer.setOnTouchListener(myTouchListener)

        for (i in 0 until mainContainer.childCount) {
            val linear = mainContainer[i] as LinearLayout
            for (j in 0 until linear.childCount) {
                elements.add(linear[j] as TextView)
            }
        }

        val reloadButton = findViewById<AppCompatButton>(R.id.buttonReload)
        reloadButton.setOnClickListener {
            repository.replay()
            describeMatrix()
            score.text = "0"
            repository.setScore(0)
        }
    }

    private fun describeMatrix() {
        val matrix = repository.getMatrix()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                elements[i * 4 + j].setBackgroundResource(colorArr[matrix[i][j]]!!)
                if (matrix[i][j] != 0) elements[i * 4 + j].text = "${matrix[i][j]}"
                else elements[i * 4 + j].text = ""
            }
        }
    }

    private fun checkToLose(): Boolean {
        val matrix = repository.getMatrix()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                if (i != 0 && matrix[i][j] == matrix[i - 1][j] || matrix[i][j] == 0) {
                    return false
                }
                if (j != 0 && matrix[i][j] == matrix[i][j - 1]) {
                    return false
                }
            }
        }
        return true
    }



}






