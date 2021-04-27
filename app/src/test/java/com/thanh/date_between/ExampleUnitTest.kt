package com.thanh.date_between

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testWith(){
        var x = 1
        var y = 1

        var o1: TEST = TEST("THANH", 22)
        var o2: TEST = TEST("THANH_2", 18)


        print(o1.toString())

    }

    data class TEST(var name: String, var age: Int)
}
