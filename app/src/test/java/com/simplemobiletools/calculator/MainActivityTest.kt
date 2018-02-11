package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.MainActivity
import com.simplemobiletools.calculator.helpers.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class MainActivityTest {
    private lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun addSimpleDigit() {
        activity.calc.addDigit(2)
        assertEquals("2", activity.getResult())
    }

    @Test
    fun removeLeadingZero() {
        activity.calc.addDigit(0)
        activity.calc.addDigit(5)
        assertEquals("5", activity.getResult())
    }

    @Test
    fun additionTest() {
        activity.calc.addDigit(5)
        activity.calc.handleOperation("plus")
        activity.calc.addDigit(4)
        activity.calc.handleEquals()
        assertEquals("9", activity.getResult())
        checkFormula("5+4")
    }

    @Test
    fun subtractTest() {
        activity.calc.addDigit(5)
        activity.calc.handleOperation("minus")
        activity.calc.addDigit(4)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("1", result)
        checkFormula("5-4")
    }

    @Test
    fun multiplyTest() {
        activity.calc.addDigit(5)
        activity.calc.handleOperation("multiply")
        activity.calc.addDigit(4)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("20", result)
        checkFormula("5×4")
    }

    @Test
    fun divisionTest() {
        activity.calc.addDigit(6)
        activity.calc.handleOperation("divide")
        activity.calc.addDigit(2)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("3", result)
    }

    @Test
    fun divisionByZero_returnsInfinity() {
        activity.calc.addDigit(6)
        activity.calc.handleOperation("divide")
        activity.calc.addDigit(0)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        print("result: "+result)
        assertEquals("∞", result)
    }

//    @Test
//    fun clearBtnSimpleTest() {
//        setDouble(156.0)
//        activity.calc.handleClear()
//        assertEquals("15", getDisplayedNumber())
//    }
//
//    @Test
//    fun clearBtnComplexTest() {
//        setDouble(-26.0)
//        activity.calc.handleClear()
//        assertEquals("-2", getDisplayedNumber())
//        activity.calc.handleClear()
//        assertEquals("0", getDisplayedNumber())
//    }
//
//    @Test
//    fun clearBtnLongClick_resetsEverything() {
//        calcResult(-1.2, PLUS, 3.4)
//        activity.calc.handleReset()
//        handleOperation(PLUS)
//        setDouble(3.0)
//        activity.calc.handleResult()
//        assertEquals("3", getDisplayedNumber())
//        checkFormula("")
//    }

    @Test
    fun moduloTest() {
        activity.calc.addDigit(6)
        activity.calc.handleOperation("modulo")
        activity.calc.addDigit(3)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("0", result)
        checkFormula("6%3")
    }

    @Test
    fun powerTest() {
        activity.calc.addDigit(3)
        activity.calc.handleOperation("power")
        activity.calc.addDigit(6)
        activity.calc.handleEquals()
        var result  = activity.getResult().toString()

        assertEquals("729", result)
        checkFormula("3^6")
    }

//    @Test
//    fun clearBtnLongClick_resetsEverything() {
//        calcResult(-1.2, PLUS, 3.4)
//        activity.calc.handleReset()
//        handleOperation(PLUS)
//        setDouble(3.0)
//        activity.calc.handleResult()
//        assertEquals("3", getDisplayedNumber())
//        checkFormula("")
//    }

    @Test
    fun complexTest() {
        setDouble(12.2)
        handleOperation(PLUS)
        setDouble(21.0)
        handleOperation(MINUS)
        assertEquals("33.2", activity.getResult())
        checkFormula("12.2+21")

        setDouble(1.6)
        activity.calc.handleEquals()
        println(activity.getResult())
        assertEquals("31.6", activity.getResult())
        checkFormula("33.2-1.6")
        activity.calc.handleEquals()

        handleOperation(MULTIPLY)
        setDouble(5.0)
        handleOperation(DIVIDE)
        assertEquals("158", activity.getResult())
        checkFormula("31.6×5")

        setDouble(4.0)
        handleOperation(MODULO)
        assertEquals("39.5", activity.getResult())
        checkFormula("158÷4")

        setDouble(5.0)
        handleOperation(POWER)
        assertEquals("4.5", activity.getResult())
        checkFormula("39.5%5")

        activity.calc.handleClear()
        assertEquals("0", activity.getResult())
    }

    private fun setDouble(d: Double) {
        var doubleString = d.toString()
        for (letter in doubleString.indices) {
           if(doubleString[letter].equals(".".single())){
                activity.calc.decimalClick();
           }
            else{
               activity.calc.addDigit(Integer.parseInt(doubleString[letter].toString()))
           }
        }

    }

    private fun handleOperation(operation: String) {
        activity.calc.handleOperation(operation)
    }

    private fun checkFormula(desired: String) {
        assertEquals(desired, activity.getFormula())
    }

}
