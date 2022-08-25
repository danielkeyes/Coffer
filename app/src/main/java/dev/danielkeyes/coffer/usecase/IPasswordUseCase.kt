package dev.danielkeyes.coffer.usecase

import android.util.Base64
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
interface IPasswordUseCase {
    fun isHashValid(hash: String?): Boolean
    fun generateSalt(): ByteArray
    fun isExpectedPassword(password: String, salt: ByteArray, expectedHash: ByteArray): Boolean
    fun hash(password: String, salt: ByteArray): ByteArray
}

class PasswordUseCase @Inject constructor(): IPasswordUseCase {
    override fun isHashValid(hash: String?): Boolean {
        return hash != null && hash.isNotEmpty()
    }

    override fun generateSalt(): ByteArray {
        val salt = ByteArray(16)
        SecureRandom().nextBytes(salt)
        return salt
    }

    override fun isExpectedPassword(password: String, salt: ByteArray, expectedHash: ByteArray):
            Boolean {
        val pwdHash = hash(password, salt)
        if (pwdHash.size != expectedHash.size) return false
        return pwdHash.indices.all { pwdHash[it] == expectedHash[it] }
    }

    override fun hash(password: String, salt: ByteArray): ByteArray {
        val spec = PBEKeySpec(password.toCharArray(), salt, 1000, 256)
        try {
            val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            return skf.generateSecret(spec).encoded
        } catch (e: NoSuchAlgorithmException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } catch (e: InvalidKeySpecException) {
            throw AssertionError("Error while hashing a password: " + e.message, e)
        } finally {
            spec.clearPassword()
        }
    }

    private fun convertToByteArray(string: String): ByteArray {
        return string.toByteArray(charset = charset("UTF-8"))
    }

    private fun convertToString(byteArray: ByteArray): String {
        return Base64.encodeToString(byteArray, Base64.DEFAULT).filter { !it.isWhitespace() }
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