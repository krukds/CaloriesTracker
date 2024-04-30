package com.example.caloriestracker.Views

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.caloriestracker.R

@Composable
fun HyperlinkText(
    linkText: String,
    link: String
) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = AnnotatedString.Builder().apply {
        withStyle(
            style = SpanStyle(
                color = colorResource(id = R.color.appLightBlueLink),
                textDecoration = TextDecoration.Underline,
                fontSize = 15.sp
            )
        ) {
            append(linkText)
            addStringAnnotation("URL", link, start = 0, end = linkText.length)
        }
    }.toAnnotatedString()

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            ).firstOrNull()?.let { stringAnnotation ->
                uriHandler.openUri(stringAnnotation.item)
            }
        }
    )
}
