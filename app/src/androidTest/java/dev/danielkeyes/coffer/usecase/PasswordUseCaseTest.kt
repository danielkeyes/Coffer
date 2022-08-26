package dev.danielkeyes.coffer.usecase

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

// Have this in AndroidTest for the use of Base64 as I don't want to mock and want to test the
// conversion

@RunWith(AndroidJUnit4::class)
class PasswordUseCaseTest {
    val pUC = PasswordUseCase()

    @Test
    fun string_to_bytearray_conversion1() {

        for (i in 1200..1221) {
            val someStringByteArray = pUC.convertToByteArray(i.toString())
            assertEquals(pUC.convertToString(someStringByteArray), i.toString())
        }
    }

    @Test
    fun string_to_bytearray_conversion2() {
        val someString = "sdasdk as da a s l192ewja jsj"

        val someStringByteArray = pUC.convertToByteArray(someString)
        assertEquals(pUC.convertToString(someStringByteArray), someString)

    }

    @Test
    fun generated_hash_matches_password(){
        val salt = pUC.generateSalt()
        val hash = pUC.hash("1221", salt)

        assertEquals( true, pUC.isExpectedPassword("1221",salt, hash))
    }
}