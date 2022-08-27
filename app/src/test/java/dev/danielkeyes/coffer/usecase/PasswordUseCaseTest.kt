package dev.danielkeyes.coffer.usecase

import org.junit.Assert
import org.junit.Test

class PasswordUseCaseTest {
    private val passwordUseCase = PasswordUseCase()

    @Test
    fun string_to_bytearray_conversion1() {
        for (i in 1200..1221) {
            val someStringByteArray = passwordUseCase.convertToByteArray(i.toString())
            Assert.assertEquals(passwordUseCase.convertToString(someStringByteArray), i.toString())
        }
    }

    @Test
    fun string_to_bytearray_conversion2() {
        val someString = "sdasdk as da a s l192ewja jsj"

        val someStringByteArray = passwordUseCase.convertToByteArray(someString)
        Assert.assertEquals(passwordUseCase.convertToString(someStringByteArray), someString)

    }

    @Test
    fun generated_hash_matches_password(){
        val salt: String = passwordUseCase.generateSalt()
        val passwordHash: String = passwordUseCase.hash("1221", salt)

        Assert.assertEquals(true, passwordUseCase.isExpectedPassword("1221", salt, passwordHash))
    }
}