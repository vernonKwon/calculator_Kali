package kr.co.inmagic.calculator

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var the_four_fundamental_arithmetic_operations_status = 0 // 1 : plus, 2 : minus, 3 : multyply, 4 : division
    var isDot = false
    var isDeleteOk = false
    var isSelected = false

    var selectOrder = 1

    var accumulation: Double = 0.0 // 누산 변수

    val resNumberId = intArrayOf(R.id.btn_zero, R.id.btn_one, R.id.btn_two, R.id.btn_three, R.id.btn_four, R.id.btn_five,
            R.id.btn_six, R.id.btn_seven, R.id.btn_eight, R.id.btn_nine) // 숫자 10개

    val resFunctionId = intArrayOf(R.id.ac, R.id.btn_negative_number, R.id.btn_percent, R.id.btn_division,
            R.id.btn_multiply, R.id.minus, R.id.btn_plus, R.id.btn_equal, R.id.btn_decimal_point) // 기능 9개

    val button_Number = arrayOfNulls<Button>(10)
    val button_Function = arrayOfNulls<Button>(resFunctionId.size)

    var width = 0 //lazy { parentView.measuredWidth }
    var height = 0 //lazy { parentView.measuredHeight }

    var temp_number: Double = 0.0
    var sec_number: Double = 0.0

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

        Toast.makeText(applicationContext, height.toString(), Toast.LENGTH_SHORT).show() // 해상도 세로값 출력 for debug

        numberpad.layoutParams = LinearLayout.LayoutParams(width, numberPadHeight)

        for (i in 0 until resNumberId.size) {
            button_Number[i] = findViewById(resNumberId[i])

            button_Number[i]!!.setOnClickListener {

                if (isDeleteOk) {
                    textview_showNumber.text = ""
                    isDeleteOk = false
                }
                if (textview_showNumber.text == "0") {
                    textview_showNumber.text = i.toString()
                } else {
                    textview_showNumber.text = (textview_showNumber.text.toString() + i)
                }
                when(selectOrder) {
                    1 -> {
                        selectOrder = 2
                    }
                    2 -> {
                        sec_number = textview_showNumber.text.toString().toDouble()
                        selectOrder = 1
                    }
                }
                accumulation = textview_showNumber.text.toString().toDouble()
                isSelected = true

                Toast.makeText(applicationContext, i.toString(), Toast.LENGTH_SHORT).show()

            }

            button_Number[i]!!.layoutParams.width = a
            button_Number[i]!!.layoutParams.height = a
            button_Number[i]!!.setPadding(20, 20, 20, 20)
            button_Number[i]!!.background = ContextCompat.getDrawable(this, R.drawable.round_button)

        }
        button_Number[0]!!.background = ContextCompat.getDrawable(this, R.drawable.zero_button)

        for (i in 0 until resFunctionId.size) { // 9개
            button_Function[i] = findViewById(resFunctionId[i])
        }

        // 값 초기화 ,AC
        button_Function[0]!!.setOnClickListener {
            accumulation = 0.0
            textview_showNumber.text = "0"
            isDot = false
            selectOrder = 1
            isSelected = false
        }

        // +/-
        button_Function[1]!!.setOnClickListener {

            listener_negative_number()
            isSelected = false
        }
        // percent
        button_Function[2]!!.setOnClickListener {
            listener_percent()
            isSelected = false
        }

        // division
        button_Function[3]!!.setOnClickListener {
            temp_number = accumulation
            the_four_fundamental_arithmetic_operations_status = 4
            isDeleteOk = true
            isDot = false
            isSelected = false
            // button_Function[3]!!.setBackgroundColor() // 색깔 변경 (눌려있는 상태)

        }

        //multyfly
        button_Function[4]!!.setOnClickListener {
            temp_number = accumulation
            the_four_fundamental_arithmetic_operations_status = 3
            isDeleteOk = true
            isDot = false
            isSelected = false
        }

        //minus
        button_Function[5]!!.setOnClickListener {
            temp_number = accumulation
            the_four_fundamental_arithmetic_operations_status = 2
            isDeleteOk = true
            isDot = false
            isSelected = false
        }

        //plus
        button_Function[6]!!.setOnClickListener {
            temp_number = accumulation
            the_four_fundamental_arithmetic_operations_status = 1
            isDeleteOk = true
            isDot = false
            isSelected = false
        }

        // equal
        button_Function[7]!!.setOnClickListener {

            if(!isSelected) {
                temp_number = sec_number
            }

            when (the_four_fundamental_arithmetic_operations_status) {

                1 -> {
                    accumulation = (temp_number + textview_showNumber.text.toString().toDouble())
                    textview_showNumber.text = accumulation.toString()
                    isDeleteOk = false
                }

                2 -> {
                    accumulation = (temp_number - textview_showNumber.text.toString().toDouble())
                    textview_showNumber.text = accumulation.toString()
                    isDeleteOk = false
                }

                3 -> {
                    accumulation = (temp_number * textview_showNumber.text.toString().toDouble())
                    textview_showNumber.text = accumulation.toString()
                    isDeleteOk = false
                }

                4 -> {
                    accumulation = (temp_number / textview_showNumber.text.toString().toDouble())
                    textview_showNumber.text = accumulation.toString()
                    isDeleteOk = false
                    // button_Function[3]!!.setBackgroundColor() // 색깔 변경 (안눌려 있는 상태)
                }
            }
            isSelected = false
        }

        button_Function[8]!!.setOnClickListener {


            if (isDot) { // .이 존재하지 않음

            } else { // .이 존재함 추후 소수점 이후 자리는 없애버리는 기능 추가 / 기존 아이폰엔 없음
                textview_showNumber.text = (textview_showNumber.text.toString() + ".")
                accumulation = textview_showNumber.text.toString().toDouble()
                isDot = true
            }


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
