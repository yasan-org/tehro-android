package dev.yasan.metro.tehran.ui.composable.common.teh

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import dev.yasan.kit.compose.foundation.grid
import dev.yasan.metro.tehran.R
import dev.yasan.metro.tehran.model.tehro.Line
import dev.yasan.metro.tehran.ui.preview.line.LinePreviewProvider
import dev.yasan.metro.tehran.ui.theme.vazirFamily
import dev.yasan.metro.tehran.util.LocaleHelper
import dev.yasan.metro.tehran.util.extension.getTextOnColor

/**
 * Tehro-themed screen title.
 * This composable must be used on top of every single screen.
 */
@Composable
fun TehTitle(
    modifier: Modifier = Modifier,
    title: String,
    fontFamily: FontFamily = LocaleHelper.properFontFamily,
    colorBackground: Color = colorResource(id = R.color.layer_foreground),
    colorText: Color = colorBackground.getTextOnColor()
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(color = colorBackground)
                .padding(grid(2)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title.uppercase(),
                fontFamily = fontFamily,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = colorText,
                textAlign = TextAlign.Center
            )
        }
        TehDivider()
    }

}

@Preview(name = "Teh Title (En)", group = "Base (Day)", locale = "en", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Teh Title (En)", group = "Base (Night)", locale = "en", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TehTitlePreviewEn() {
    TehTitle(title = stringResource(id = R.string.app_name))
}

@Preview(name = "Teh Title (Fa)", group = "Base (Day)", locale = "fa", uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "Teh Title (Fa)", group = "Base (Night)", locale = "fa", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun TehTitlePreviewFa() {
    TehTitle(title = stringResource(id = R.string.app_name), fontFamily = vazirFamily)
}

@Preview(name = "Line Title (En)", group = "Line", locale = "en")
@Composable
private fun TehTitlePreviewEnLine(@PreviewParameter(LinePreviewProvider::class) line: Line) {
    TehTitle(title = line.nameEn, colorBackground = line.color)
}

@Preview(name = "Line Title (Fa)", group = "Line", locale = "fa")
@Composable
private fun TehTitlePreviewFaLine(@PreviewParameter(LinePreviewProvider::class) line: Line) {
    TehTitle(title = line.nameFa, colorBackground = line.color, fontFamily = vazirFamily)
}