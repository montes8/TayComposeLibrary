package com.valu.taycomposelibrary

import android.os.Bundle
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valu.taycomposelibrary.ui.theme.TayComposeLibraryTheme
import com.valu.uitaycompose.label.UiTayEditBasic
import com.valu.uitaycompose.label.UiTayEditLayout

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier.padding(top=250.dp, start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(36.dp)) {
        UiTayEditLayout(
            value = text,
            onValueChange = { text = it },
            hint = "Nombre de usuario"
        )
    }
}