package com.valu.uitaycompose.extra

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.model.UiTayToolBarModel
import com.valu.uitaycompose.utils.UI_TAY_TEXT_DEFAULT

@Composable
fun UiTayCToolBar(uiTayText : String = UI_TAY_TEXT_DEFAULT,
                  uiTayModifier : UiTayToolBarModel = UiTayToolBarModel(),
                  uiTayClick: (Boolean) -> Unit = {}
) {
    Box (modifier = Modifier.height(uiTayModifier.uiHeight.dp).background(uiTayModifier.uiBgColor)
        ,contentAlignment = Alignment.CenterStart
    ){
        if (uiTayModifier.uiTypeStart){
            IconButton(onClick = {
                uiTayClick(true)
            },modifier = Modifier
                .align(Alignment.CenterStart).padding(start = uiTayModifier.uiIconMarginStar.dp)) {
                Icon(painter = painterResource(id = uiTayModifier.uiIconStart),
                    contentDescription = "uiTayBackIcon",
                    tint = uiTayModifier.uiTextColor
                )
            }
        }
        Text(text = uiTayText,modifier = Modifier.fillMaxWidth().padding(
            start = uiTayModifier.uiTextMarginHorizontal.dp,
            end = uiTayModifier.uiTextMarginHorizontal.dp),
            color = uiTayModifier.uiTextColor,
            textAlign = uiTayModifier.uiTextPosition,
            style = uiTayModifier.uiTextFont)
        if (uiTayModifier.uiTypeEnd){
            IconButton(onClick = {
                uiTayClick(false)
            }, modifier = Modifier
                .align(Alignment.CenterEnd).padding(start = uiTayModifier.uiIconMarginEnd.dp)) {
                Icon( painter = painterResource(id = uiTayModifier.uiIconEnd), contentDescription =
                    "uiTayMenuIcon", tint = uiTayModifier.uiTextColor)
            }
        }

    }
}