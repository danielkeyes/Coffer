package dev.danielkeyes.coffer.usecase

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

// used https://gist.github.com/tuesd4y/e1584120484ac24be9f00f3968a4787d
interface IPasswordUseCase {
    fun isPinValid(pin: String?): Boolean
    fun generateSalt(): ByteArray
    fun isExpectedPassword(password: String, salt: ByteArray, expectedHash: ByteArray): Boolean
    fun hash(password: String, salt: ByteArray): ByteArray
}

class PasswordUseCase: IPasswordUseCase {
    override fun isPinValid(pin: String?): Boolean {
        return pin != null && pin.isNotEmpty()
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
}