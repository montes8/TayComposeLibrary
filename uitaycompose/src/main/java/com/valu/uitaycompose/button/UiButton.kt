package com.valu.uitaycompose.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valu.uitaycompose.R
import com.valu.uitaycompose.model.UTStyleCButton
import com.valu.uitaycompose.model.UTStyleIcon
import com.valu.uitaycompose.model.UiTayButtonModel
import com.valu.uitaycompose.model.uiTayBackground
import com.valu.uitaycompose.model.uiTayStroke
import com.valu.uitaycompose.model.uiTayTextColor
import com.valu.uitaycompose.model.utBtnState
import com.valu.uitaycompose.utils.UI_TAY_TEXT_DEFAULT
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun UiTayButton(uiTayText : String = UI_TAY_TEXT_DEFAULT,
                uiTayEnable : Boolean = true,
                uiTayStyleBtn : UTStyleCButton = UTStyleCButton.UI_TAY_PRIMARY,
                uiTayStyleIcon : UTStyleIcon = UTStyleIcon.FULL,
                uiTayBtnModel : UiTayButtonModel = UiTayButtonModel(),
                paddingDrawable : Dp = 8.dp,
                colorDefaultIcon : Boolean = false,
                uiTayClick: () -> Unit
) {
    var selected by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Button(modifier = Modifier
        .height(uiTayBtnModel.uTHeight.dp).fillMaxWidth(),
        shape = RoundedCornerShape(uiTayBtnModel.uTRadius.dp),
        border = BorderStroke(uiTayBtnModel.uTStrokeWith.dp,
            uiTayBtnModel.uiTayStroke(uiTayStyleBtn,uiTayEnable.utBtnState(selected))),
        colors = ButtonDefaults.buttonColors(containerColor =
            uiTayBtnModel.uiTayBackground(uiTayStyleBtn,uiTayEnable.utBtnState(selected)),
            contentColor =
                uiTayBtnModel.uiTayBackground(uiTayStyleBtn,uiTayEnable.utBtnState(selected)
            )),
        onClick = {
            if(uiTayEnable){
                selected  = true
                scope.launch {
                    delay(500)
                    selected = false
                }
                uiTayClick()
            }
        }) {


        Row(horizontalArrangement = Arrangement.spacedBy(paddingDrawable),
            verticalAlignment = Alignment.CenterVertically) {
            if (uiTayStyleIcon == UTStyleIcon.FULL || uiTayStyleIcon == UTStyleIcon.START){
                Icon(
                    painter = painterResource(id = R.drawable.uic_tay_ic_menu),
                    contentDescription = null,

                    tint = if (colorDefaultIcon)Color.Unspecified else
                        uiTayBtnModel.uiTayTextColor(uiTayStyleBtn,uiTayEnable.utBtnState(selected)


                ))
            }
            Text(text = uiTayText, color =
                uiTayBtnModel.uiTayTextColor(uiTayStyleBtn,uiTayEnable.utBtnState(selected))
            )
            if (uiTayStyleIcon == UTStyleIcon.FULL || uiTayStyleIcon == UTStyleIcon.END){
                Icon(
                    painter = painterResource(id = R.drawable.uic_tay_ic_menu),
                    contentDescription = null,
                    tint = if (colorDefaultIcon)Color.Unspecified else
                        uiTayBtnModel.uiTayTextColor(uiTayStyleBtn,uiTayEnable.utBtnState(selected)
                    )
                )
            }
        }

    }

}
