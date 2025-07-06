package com.devpaul.shared.ui.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devpaul.core_platform.R
import com.devpaul.core_platform.theme.Black

@Composable
fun TopBarPrincipal(
    modifier: Modifier = Modifier,
    style: Int = 1,
    titleStyle2: String = "",
    onStartIconClick: (() -> Unit)? = null,
    onEndIconClick: (() -> Unit)? = null
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
            .statusBarsPadding(),
        shadowElevation = 4.dp,
        color = Color.White
    ) {
        when (style) {
            1 -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = buildAnnotatedString {
                            appendStyled("Info", Color(0xFFD32F2F), FontWeight.Bold)
                            appendStyled("Perú", Black, FontWeight.Normal)
                        },
                        fontSize = 22.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    onEndIconClick?.let {
                        IconButton(
                            onClick = it,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_hr_resting_24),
                                contentDescription = "Favoritos",
                                tint = Black
                            )
                        }
                    }
                }
            }

            2 -> {
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { onStartIconClick?.invoke() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_arrow_circle_left_24),
                                contentDescription = "Atrás",
                                tint = Black
                            )
                        }

                        Text(
                            text = titleStyle2,
                            fontSize = 20.sp,
                            color = Black
                        )
                    }

                    onEndIconClick?.let {
                        IconButton(onClick = it) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_hr_resting_24),
                                contentDescription = "Favoritos",
                                tint = Black
                            )
                        }
                    }
                }
            }

            else -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "TopBar",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

private fun AnnotatedString.Builder.appendStyled(
    text: String,
    color: Color,
    weight: FontWeight
) {
    pushStyle(SpanStyle(color = color, fontWeight = weight))
    append(text)
    pop()
}
