package com.valu.taycomposelibrary

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valu.taycomposelibrary.ui.theme.TayComposeLibraryTheme
import com.valu.uitaycompose.button.UiTayButton
import com.valu.uitaycompose.loading.uiShowProgress
import com.valu.uitaycompose.security.aes.AesCBC
import com.valu.uitaycompose.security.aes.AesGCM
import com.valu.uitaycompose.security.aes.TypeAes
import com.valu.uitaycompose.security.aes.uiCreateIv
import com.valu.uitaycompose.security.aes.uiCreateKeyHexToString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Generamos una llave manual para las funciones que piden String
        Log.d("TAG_ENCROIP","forma unoo -----------------------------------------\n")
        val type =   TypeAes.GCM_256
        val iv = uiCreateIv(isGCM = true)
        val secretKeyManual = uiCreateKeyHexToString(type)
        val textoOriginal = "¡Hola! Este es un mensaje secreto 123."
        var textencrip = AesGCM.encrypt(data = textoOriginal, key = secretKeyManual,
            iv= iv,
            type = type)

       Log.d("TAG_ENCROIP",textencrip)

        var textdes = AesGCM.decrypt(dataBase64 = textencrip, key = secretKeyManual,
            iv= iv,type = type)
        Log.d("TAG_ENCROIP",textdes)







        Log.d("TAG_ENCROIP","forma dos -----------------------------------------\n")
        var textencriptwo = AesGCM.encryptAut(data = textoOriginal,
            iv= iv,type = type)

        Log.d("TAG_ENCROIP",textencriptwo)
        var textdestwo = AesGCM.decryptAut(dataBase64 = textencriptwo,
            iv= iv,type = type)
        Log.d("TAG_ENCROIP",textdestwo)
      /*  Log.d("TAG_ENCROIP","forma dos tress-----------------------------------------\n")
        val secretstring = uiKeyBase64ToString(type)

        var textencriptre = AesECB.encrypt(data = textoOriginal,key =secretstring,type = type)

        Log.d("TAG_ENCROIP",textencriptre)
        var textdestre = AesECB.decrypt(dataBase64 = textencriptre,key=secretstring,type = type)
        Log.d("TAG_ENCROIP",textdestre)*/

        setContent {
            TayComposeLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    Screençhome()
                }
            }
        }
    }
}


@Composable
fun Screençhome(){
    var showMyModal by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.padding(top=250.dp, start = 16.dp, end = 16.dp)
        .uiShowProgress(showMyModal),
        verticalArrangement = Arrangement.spacedBy(36.dp)) {
        UiTayButton() {
            showMyModal = true
            scope.launch {
                delay(3000)
                showMyModal = false
            }
        }
    }
}