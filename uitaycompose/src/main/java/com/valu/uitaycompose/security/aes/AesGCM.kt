package com.valu.uitaycompose.security.aes

import android.util.Base64
import com.valu.uitaycompose.utils.UI_MY_KEY_SECRET
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

object AesGCM {

    private const val AUTH_TAG_LENGTH = 128

    /**
     * Encripta usando una llave y un IV externos (ambos en Base64).
     */
    fun encrypt(data: String, key: String, iv: String, type: TypeAes): String {
        val keyBytes = Base64.decode(key, Base64.NO_WRAP)
        val secretKey = SecretKeySpec(keyBytes, "AES")
        val ivBytes = Base64.decode(iv, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec)
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    /**
     * Desencripta usando la misma llave y el mismo IV (ambos en Base64).
     */
    fun decrypt(dataBase64: String, keyString: String, iv: String, type: TypeAes): String {
        val keyBytes = Base64.decode(keyString, Base64.NO_WRAP)
        val secretKey = SecretKeySpec(keyBytes, "AES")
        val ivBytes = Base64.decode(iv, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val decodedData = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }

    /**
     * Encripta usando la llave generada internamente por el Keystore.
     */
    fun encryptAut(data: String, ivString: String, type: TypeAes, alias: String = UI_MY_KEY_SECRET): String {
        val key = uiCreateSecretKeyStore(alias, type)
        val ivBytes = Base64.decode(ivString, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.ENCRYPT_MODE, key, spec)
        val encrypted = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Base64.encodeToString(encrypted, Base64.NO_WRAP)
    }

    /**
     * Desencripta usando la llave generada internamente por el Keystore.
     */
    fun decryptAut(dataBase64: String, ivString: String, type: TypeAes, alias: String = UI_MY_KEY_SECRET): String {
        val key = uiCreateSecretKeyStore(alias, type)
        val ivBytes = Base64.decode(ivString, Base64.NO_WRAP)
        val spec = GCMParameterSpec(AUTH_TAG_LENGTH, ivBytes)
        val cipher = Cipher.getInstance(type.transformation)
        cipher.init(Cipher.DECRYPT_MODE, key, spec)
        val decodedData = Base64.decode(dataBase64, Base64.NO_WRAP)
        val decryptedBytes = cipher.doFinal(decodedData)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}