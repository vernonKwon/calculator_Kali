package kr.co.inmagic.calculator

import android.app.ActionBar
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutCompat
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // github upadata 2
    // Other node

    var the_four_fundamental_arithmetic_operations_status = 0 // 1 : plus, 2 : minus, 3 : multyply, 4 : division
    var isEquals = false

    var accumulation: Double = 0.0 // 누산 변수

    val resNumberId = intArrayOf(R.id.btn_zero, R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five,
            R.id.btn_six, R.id.btn_seven, R.id.btn_eight, R.id.btn_nine) // 숫자 10개

    val resFunctionId = intArrayOf(R.id.ac, R.id.btn_negative_number, R.id.btn_percent, R.id.btn_division,
            R.id.btn_multiply, R.id.minus, R.id.btn_plus, R.id.btn_equal, R.id.btn_decimal_point) // 기능 9개

    val button_Number = arrayOfNulls<Button>(10)
    val button_Function = arrayOfNulls<Button>(resFunctionId.size)

    var width = 0 //lazy { parentView.measuredWidth }
    var height = 0 //lazy { parentView.measuredHeight }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        val numberPadHeight = height / 10 * 6
        val a: Int
        a = displayMetrics.widthPixels / 6

        Toast.makeText(applicationContext, height.toString(), Toast.LENGTH_SHORT).show() // 해상도 세로값 출력

        numberpad.layoutParams = LinearLayout.LayoutParams(width, numberPadHeight)

        for (i in 0 until resNumberId.size) {
            button_Number[i] = findViewById(resNumberId[i])

            button_Number[i]!!.setOnClickListener {
                Toast.makeText(applicationContext, i.toString(), Toast.LENGTH_SHORT).show()
                textview_showNumber.text = (textview_showNumber.text.toString() + i)
            }
            button_Number[i]!!.layoutParams.width = a
            button_Number[i]!!.layoutParams.height = a
            button_Number[i]!!.setPadding(20, 20, 20, 20)
            button_Number[i]!!.background = ContextCompat.getDrawable(this, R.drawable.round_button)

        }
        button_Number[0]!!.background = ContextCompat.getDrawable(this, R.drawable.zero_button)

        for (i in 0 until resFunctionId.size) {
            button_Function[i] = findViewById(resFunctionId[i])
        }
        accumulation = 0.0


        // 값 초기화 ,AC
        button_Function[0]!!.setOnClickListener {
            accumulation = 0.0
            textview_showNumber.text = "0"
        }

        // +/-
        button_Function[1]!!.setOnClickListener {

            listener_negative_number()
        }

        button_Function[2]!!.setOnClickListener {
            listener_percent()
        }

        button_Function[3]!!.setOnClickListener {
            // 나누기

        }


    }

    private fun listener_negative_number() {
        if (textview_showNumber.text == "0") { // 0일때
            //아무 동작 안함
        } else { // 0이 아닐때
            if (textview_showNumber.text.substring(0, 1) == "-") { // 음수일때 양수로 변경하기
                setAccumulation(textview_showNumber.text.substring(1))

                textview_showNumber.text = accumulation.toString()
                println(accumulation.toString())

                //accumulation = ("-" + textview_showNumber.text).toDouble()
            } else { // 양수일때 음수로 변경하기
                setAccumulation("-" + textview_showNumber.text)
                textview_showNumber.text = accumulation.toString()
                println(accumulation.toString())

            }
        }
    }

    private fun listener_percent() { // 동작하지만 맨 처음 0 처리 필요
        if (textview_showNumber.text == "0") { //0일때
            //아무 동작 안함
        } else {
            setAccumulation((textview_showNumber.text.toString().toDouble() * 0.01).toString())
            textview_showNumber.text = accumulation.toString()
        }
    }

    private fun setAccumulation(number: String) {
        accumulation = number.toDouble()
    }
}
