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
import com.valu.uitaycompose.security.encryption.quantum.QuantumEngine
import com.valu.uitaycompose.security.encryption.quantum.uiKeyPrivateQuantum
import com.valu.uitaycompose.security.encryption.quantum.uiKeyPublicQuantum
import com.valu.uitaycompose.security.encryption.uiCreateIv
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val message = "Este es un mensaje protegido contra computadoras cuánticas"
        val keyPairBeto = QuantumEngine.uiCreateKeys()
        val publicaBeto = keyPairBeto.uiKeyPublicQuantum()
        val privadaBeto = keyPairBeto.uiKeyPrivateQuantum()
        val iv = uiCreateIv(true)


        val textEncrypt =  QuantumEngine.encrypt(
            data = message,publicKey = publicaBeto,iv=  iv)

        Log.d("tagquatum", textEncrypt.first)
        Log.d("tagquatum", "\n")
        val textDesencrypt =  QuantumEngine.decrypt(
            data = message,privateKey = privadaBeto, packageKey = textEncrypt.second,iv=  iv)

        Log.d("tagquatum", textDesencrypt)

        setContent {
            TayComposeLibraryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { p ->
                    print(p.toString())
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