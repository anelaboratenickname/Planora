package ru.zalesov.planora.ui.themes.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.zalesov.planora.ui.R

val Typography = Typography(
    labelSmall = TextStyle(
        fontFamily = CustomFontFamilies.merriweather,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = CustomFontFamilies.merriweather,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = CustomFontFamilies.merriweather,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = CustomFontFamilies.merriweather,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
)

class CustomFontFamilies {
    companion object {
        val merriweather = FontFamily(
            Font(R.font.merriweather_sans_medium, FontWeight.Medium),
            Font(R.font.merriweather_sans_bold, FontWeight.Bold)
        )
    }
}