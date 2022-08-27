package dev.danielkeyes.coffer.usecase

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject

// used https://gist.github.com/tuesd4y/e1584120484ac24be9f00f3968a4787d
// this example but with conversion to Strings for easier usability and later storage to
// preferences store
interface IPasswordUseCase {
    fun isHashValid(hash: String?): Boolean
    fun generateSalt(): String
    fun isExpectedPassword(password: String, salt: String, expectedHash: String): Boolean
    fun  hash(password: String, salt: String): String
}

class PasswordUseCase @Inject constructor(): IPasswordUseCase {
    override fun isHashValid(hash: String?): Boolean {
        return hash != null && hash.isNotEmpty()
    }

    override fun generateSalt(): String {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)

        return convertToString(salt)
    }

    override fun isExpectedPassword(password: String, salt: String, expectedHash: String):
            Boolean {
        val passwordHashByteArray = convertToByteArray(string = hash(password = password, salt = salt))
        val expectedHashByteArray = convertToByteArray(string = expectedHash)
        if (passwordHashByteArray.size != expectedHashByteArray.size) return false
        return passwordHashByteArray.indices.all { passwordHashByteArray[it] == expectedHashByteArray[it] }
    }

    override fun hash(password: String, salt: String): String {
        val saltByteArray = convertToByteArray(salt)
        val spec = PBEKeySpec(password.toCharArray(), saltByteArray, 1000, 256)
        try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            return convertToString(skf.generateSecret(spec).encoded)
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } catch (e: InvalidKeySpecException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } finally {
            spec.clearPassword()
        }
    }

    fun convertToByteArray(string: String): ByteArray {
        return string.toByteArray(charset = charset("UTF-8"))
    }

    fun convertToString(byteArray: ByteArray): String {
        return String(byteArray, charset("UTF-8"))
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindPasswordUseCase(
        passwordUseCase: PasswordUseCase
    ): IPasswordUseCase
}