package com.vivekvista.taglocationassignment.presentation.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val ButtonLightBackground = Color(0xFF525252)
val ButtonDarkBackground = Color(0xFFFFFFFF)

val Colors.buttonBackground: Color
    @Composable
    get() {
       return if(isLight) ButtonLightBackground else ButtonDarkBackground
    }

val Colors.buttonTextColor: Color
    @Composable
    get() {
        return if(isLight) Color.White else Color.Black
    }

val Colors.bottomSheetBackground: Color
    @Composable
    get() {
        return if(isLight) Color.White else Color.Black
    }


