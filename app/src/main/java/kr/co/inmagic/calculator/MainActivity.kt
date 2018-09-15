package kr.co.inmagic.calculator

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var the_four_fundamental_arithmetic_operations_status = 0 // 1 : plus, 2 : minus, 3 : multyply, 4 : division
    var isDeleteOk = false

    var isFirstPushed_equals_flag = false
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

    var first_number: Double = 0.0
    var sec_number: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        width = displayMetrics.widthPixels
        height = displayMetrics.heightPixels

        val numberPadHeight = height / 10 * 6
        val a = displayMetrics.widthPixels / 6

        numberpad.layoutParams = LinearLayout.LayoutParams(width, numberPadHeight)

        for (i in 0 until resNumberId.size) {
            button_Number[i] = findViewById(resNumberId[i])
            button_Number[i]!!.background = ContextCompat.getDrawable(this, R.drawable.numberpad)

            button_Number[i]!!.setOnClickListener {

                if (isDeleteOk) {
                    textview_showNumber.text = ""
                    isDeleteOk = false
                }
                if (textview_showNumber.text == "0") {
                    textview_showNumber.text = i.toString()
                } else {
                    textview_showNumber.text = (textview_showNumber.text.toString() + i)
                    sec_number = textview_showNumber.text.toString().toDouble()
                }

                Log.d("debug", "sec_number : " + sec_number.toString())
                Log.d("debug", "textview_showNumber : " + textview_showNumber.text.toString())

            }

            button_Number[i]!!.layoutParams.width = a
            button_Number[i]!!.layoutParams.height = a
            button_Number[i]!!.setPadding(20, 20, 20, 20)
            button_Number[i]!!.background = ContextCompat.getDrawable(this, R.drawable.numberpad)
            button_Number[i]!!.setTextColor(Color.WHITE)


        }
        button_Number[0]!!.background = ContextCompat.getDrawable(this, R.drawable.zero_button)
        //button_Number[0]!!.background = ContextCompat.getDrawable(this, R.drawable.zero_button)


        for (i in 0 until resFunctionId.size) { // 9개
            button_Function[i] = findViewById(resFunctionId[i])
            button_Function[i]!!.layoutParams.width = a
            button_Function[i]!!.layoutParams.height = a
        }

        button_Function[8]!!.background = ContextCompat.getDrawable(this, R.drawable.numberpad)
        button_Function[8]!!.setTextColor(Color.WHITE)
        for (i in 0 until 3) {
            button_Function[i]!!.background = ContextCompat.getDrawable(this, R.drawable.funpad)
        }

        for (i in 3 until 8) {
            button_Function[i]!!.background = ContextCompat.getDrawable(this, R.drawable.four)
        }

        // 값 초기화 ,AC
        button_Function[0]!!.setOnClickListener {
            accumulation = 0.0
            textview_showNumber.text = "0"
            selectOrder = 1
            isFirstPushed_equals_flag = false
        }


        // +/-
        button_Function[1]!!.setOnClickListener {
            listener_negative_number()
            Log.d("debug", "accumulation : " + accumulation.toString())
        }

        // percent
        button_Function[2]!!.setOnClickListener {
            listener_percent()
            Log.d("debug", "accumulation : " + accumulation.toString())
        }

        /**
         *
        button_Function
        division : 3 - 4
        multyfly : 4 - 3
        minus : 5 - 2
        plus : 6 - 1
         */

        //사칙연산, 추후 사용
/*
        for (i in 3..6) {
            Log.d("debug", "temp : " + temp.toString())
            button_Function[i]!!.setOnClickListener {
                first_number = accumulation
                the_four_fundamental_arithmetic_operations_status = (i-2)
                isDeleteOk = true
                isDot = false
                isFirstPushed_equals_flag = true

                Log.d("debug", "accumulation : " + accumulation.toString())
                Log.d("debug", "사칙연산 상태 : " + the_four_fundamental_arithmetic_operations_status.toString())
                Log.d("debug", "temp : " + temp.toString())
            }
            temp--
        }
*/

        //division
        button_Function[3]!!.setOnClickListener {
            first_number = sec_number
            the_four_fundamental_arithmetic_operations_status = 4
            isDeleteOk = true
            //isDot = false
            isFirstPushed_equals_flag = true

            Log.d("debug", "accumulation : " + accumulation.toString())
        }

        //multyfly
        button_Function[4]!!.setOnClickListener {
            first_number = sec_number
            the_four_fundamental_arithmetic_operations_status = 3
            isDeleteOk = true
            //isDot = false
            isFirstPushed_equals_flag = true
            Log.d("debug", "accumulation : " + accumulation.toString())
        }

        //minus
        button_Function[5]!!.setOnClickListener {
            first_number = sec_number
            the_four_fundamental_arithmetic_operations_status = 2
            isDeleteOk = true
            //isDot = false
            isFirstPushed_equals_flag = true
            Log.d("debug", "accumulation : " + accumulation.toString())
        }


        //plus
        button_Function[6]!!.setOnClickListener {
            first_number = sec_number
            the_four_fundamental_arithmetic_operations_status = 1
            isDeleteOk = true
            //isDot = false
            isFirstPushed_equals_flag = true

            Log.d("debug", "accumulation : " + accumulation.toString())
        }

        // equal
        button_Function[7]!!.setOnClickListener {
            //sec_number = textview_showNumber.text.toString().toDouble()

            when (the_four_fundamental_arithmetic_operations_status) {

                1 -> {
                    if (isFirstPushed_equals_flag) {
                        accumulation = first_number + sec_number

                        Log.d("debug", "isfirstpush_equals_flag : true")
                        isFirstPushed_equals_flag = false
                    } else {
                        accumulation += sec_number

                        Log.d("debug", "isfirstpush_equals_flag : false")
                    }
                }

                2 -> {
                    if (isFirstPushed_equals_flag) {
                        accumulation = first_number - sec_number

                        Log.d("debug", "isfirstpush_equals_flag : true")
                        isFirstPushed_equals_flag = false
                    } else {
                        //accumulation -= sec_number
                        accumulation -= sec_number
                        Log.d("debug", "isfirstpush_equals_flag : false")
                    }
                }

                3 -> {
                    if (isFirstPushed_equals_flag) {
                        accumulation = first_number * sec_number

                        Log.d("debug", "isfirstpush_equals_flag : true")
                        isFirstPushed_equals_flag = false
                    } else {
                        accumulation *= sec_number

                        Log.d("debug", "isfirstpush_equals_flag : false")
                    }
                }

                4 -> {
                    if (isFirstPushed_equals_flag) {
                        accumulation = first_number / sec_number

                        Log.d("debug", "isfirstpush_equals_flag : true")
                        isFirstPushed_equals_flag = false
                    } else {
                        accumulation /= sec_number

                        Log.d("debug", "isfirstpush_equals_flag : false")
                    }
                }
            }
            Log.d("debug", "accumulation : " + accumulation.toString())
            Log.d("debug", "sec_number : " + sec_number.toString())
            Log.d("debug", "first_number : " + first_number.toString())

            isDeleteOk = false

            if (accumulation % 1 == 0.0) { // 소수점 뒤가 유효할때 대응해야함. 단순히 이 방식은 유효자리 숫자 뒤를 지우지 못함.
                textview_showNumber.text = accumulation.toString().replace(".0", "")
            } else {
                textview_showNumber.text = accumulation.toString()
            }


        }

        button_Function[8]!!.setOnClickListener {

            if (textview_showNumber.text.toString().contains(".")) { // .이 존재하지 않음

            } else { // .이 존재함 추후 소수점 이후 자리는 없애버리는 기능 추가 / 기존 아이폰엔 없음
                setShowNumber(textview_showNumber.text.toString() + ".")
                accumulation = textview_showNumber.text.toString().toDouble()
            }
        }
    }

    private fun listener_negative_number() {
        if (textview_showNumber.text == "0") {
            //아무 동작 안함
        } else { // 0이 아닐때
            if (textview_showNumber.text.substring(0, 1) == "-") { // 음수일때 양수로 변경하기
                setAccumulation(textview_showNumber.text.substring(1))

                textview_showNumber.text = accumulation.toString()
                println(accumulation.toString())

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

    private fun setShowNumber(string: String) {
        textview_showNumber.text = string.trim()
    }
}
