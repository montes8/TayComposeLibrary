package com.valu.uitaycompose.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.valu.uitaycompose.R
import com.valu.uitaycompose.utils.tay_blue_700
import com.valu.uitaycompose.utils.textGabbiB20

data class UiTayToolBarModel (
    var uiHeight : Int = 56,
    var uiBgColor : Color = tay_blue_700,
    var uiTextColor: Color = Color.White,
    var uiIconColor: Color = Color.White,
    var uiIconStart : Int = R.drawable.uic_tay_ic_back,
    var uiIconEnd : Int = R.drawable.uic_tay_ic_menu,
    var uiTextMarginHorizontal : Int = 0,
    var uiIconMarginStar : Int = 0,
    var uiIconMarginEnd : Int = 0,
    var uiTextFont : TextStyle = textGabbiB20,
    var uiTextPosition : TextAlign = TextAlign.Center,
    var uiTypeStart : Boolean = true,
    var uiTypeEnd : Boolean = true
)