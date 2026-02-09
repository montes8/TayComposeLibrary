package com.valu.uitaycompose.security.encryption.quantum

import android.util.Base64
import android.util.Log
import com.valu.uitaycompose.security.encryption.TypeAes
import com.valu.uitaycompose.security.encryption.aes.AesGCM
import com.valu.uitaycompose.security.encryption.uiCreateIv
import org.bouncycastle.crypto.AsymmetricCipherKeyPair
import org.bouncycastle.crypto.SecretWithEncapsulation
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKEMExtractor
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKEMGenerator
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyGenerationParameters
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyPairGenerator
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPrivateKeyParameters
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPublicKeyParameters
import java.security.SecureRandom

object QuantumEngine {

    fun generateKeys(): AsymmetricCipherKeyPair {
        val keyGen = KyberKeyPairGenerator()
        // Kyber768 es el balance perfecto entre velocidad y seguridad
        keyGen.init(KyberKeyGenerationParameters(SecureRandom(), KyberParameters.kyber768))
        return keyGen.generateKeyPair()
    }

    // 2. ENCAPSULAR: Genera una llave secreta y su "paquete" cifrado
    // Se usa la Llave Pública del receptor
    fun encapsulate(publicKey: KyberPublicKeyParameters): Pair<ByteArray, ByteArray> {
        val encapGen = KyberKEMGenerator(SecureRandom())
        val secretWithEncapsulation: SecretWithEncapsulation = encapGen.generateEncapsulated(publicKey)

        val secretKey = secretWithEncapsulation.secret // Esta es la llave para AES
        val encapsulation = secretWithEncapsulation.encapsulation // Esto es lo que se envía por internet

        return Pair(secretKey, encapsulation)
    }

    // 3. DESENCAPSULAR: Recupera la llave secreta usando la privada
    fun decapsulate(privateKey: KyberPrivateKeyParameters, encapsulation: ByteArray): ByteArray {
        val extractor = KyberKEMExtractor(privateKey)
        return extractor.extractSecret(encapsulation)
    }

}

fun testQuantumEncryption() {
    // --- ESCENARIO: Ana quiere enviarle un mensaje a Beto ---

    // 1. Beto genera sus llaves y le manda la PÚBLICA a Ana
    val keyPairBeto = QuantumEngine.generateKeys()
    val publicaBeto = keyPairBeto.public as KyberPublicKeyParameters
    val privadaBeto = keyPairBeto.private as KyberPrivateKeyParameters

    // 2. Ana genera la "Llave Maestra" de forma cuántica
    // Obtiene: La llave para AES y el "Paquete" (encapsulation) para Beto
    val (llaveSecretaAES, paqueteParaBeto) = QuantumEngine.encapsulate(publicaBeto)

    // 3. Ana encripta su mensaje usando AES-GCM con esa llave secreta
    val mensaje = "Este es un mensaje protegido contra computadoras cuánticas"
    val iv = uiCreateIv(true) // Tu función de IV para GCM

    // Usamos el objeto AesGCM que creamos antes
    val textoCifrado = AesGCM.encrypt(
        data = mensaje,
        key = Base64.encodeToString(llaveSecretaAES, Base64.NO_WRAP),
        iv = iv,
        type = TypeAes.GCM_256
    )

    // --- EN EL RECEPTOR (Beto) ---

    // 4. Beto recibe el 'paqueteParaBeto' y usa su PRIVADA para sacar la llave AES
    val llaveRecuperadaBeto = QuantumEngine.decapsulate(privadaBeto, paqueteParaBeto)

    // 5. Beto desencripta el mensaje
    val mensajeFinal = AesGCM.decrypt(
        dataBase64 = textoCifrado,
        key = Base64.encodeToString(llaveRecuperadaBeto, Base64.NO_WRAP),
        iv = iv,
        type = TypeAes.GCM_256
    )

    Log.d("QUANTUM", "Mensaje recuperado: $mensajeFinal")
}