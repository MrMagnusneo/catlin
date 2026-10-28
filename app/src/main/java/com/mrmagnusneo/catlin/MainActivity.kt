package com.mrmagnusneo.catlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                var task by rememberSaveable { mutableIntStateOf(0) }
                Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
                    Text("Модуль 2 — Kotlin / Compose", style = MaterialTheme.typography.headlineSmall)
                    if (task == 0) {
                        (1..10).forEach { n ->
                            OutlinedButton(onClick = { task = n }, modifier = Modifier.fillMaxWidth()) {
                                Text("Задание $n")
                            }
                        }
                        Text("Задание 9: app/src/androidTest/…/WaterTrackerTest.kt")
                    } else {
                        TextButton(onClick = { task = 0 }) { Text("← К списку") }
                        HorizontalDivider()
                        Spacer(Modifier.height(16.dp))
                        when (task) {
                            1 -> Column {
                                Hello("Андрей")
                                Spacer(Modifier.height(16.dp))
                                Hello(null)
                            }
                            2 -> Hello("Андрей") // also see @Preview configurations below
                            3 -> DescriptionVariants()
                            4 -> StyledButton()
                            5 -> InitialsCard()
                            6 -> CircleExamples()
                            7 -> LayoutExamples()
                            8 -> WaterTracker()
                            9 -> Text("UI-тесты находятся в app/src/androidTest. Запустите WaterTrackerTest через Android Studio.")
                            10 -> ShoppingCart()
                        }
                    }
                }
            }
        }
    }
}

// Task 1: nullable parameter and Elvis operator
@Composable fun Hello(name: String?) {
    Text(name?.let { "Привет, $it!" } ?: "Имя не задано", fontSize = 24.sp)
}

// Task 2: three distinct preview configurations
@Preview(name = "Portrait", showSystemUi = true, widthDp = 411, heightDp = 891)
@Composable fun PortraitPreview() { MaterialTheme { Hello("Андрей") } }

@Preview(name = "Landscape", showSystemUi = true, widthDp = 891, heightDp = 411)
@Composable fun LandscapePreview() { MaterialTheme { Hello("Андрей") } }

@Preview(name = "Round", widthDp = 200, heightDp = 200)
@Composable fun RoundPreview() {
    Box(Modifier.size(200.dp).clip(CircleShape).background(Color.Yellow), contentAlignment = Alignment.Center) {
        Hello("Андрей")
    }
}

// Task 3: text style and ellipsis
@Composable fun DescriptionVariants() {
    val description = stringResource(R.string.compose_description)
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(description, color = Color(0xFF207A35), fontSize = 16.sp,
            fontStyle = FontStyle.Italic, textAlign = TextAlign.Center)
        Text(description, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(description, modifier = Modifier.padding(top = 48.dp).background(Color(0xFFB5EAB8)),
            color = Color.Black, fontSize = 24.sp, textDecoration = TextDecoration.Underline)
    }
}

// Task 4: outlined rounded button with elevation
@Composable fun StyledButton() {
    Button(onClick = {}, modifier = Modifier.fillMaxWidth().shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color.Gray),
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black)) {
        Text("Кнопка")
    }
}

// Task 5: full-width 100dp banner with centered circular initials
@Composable fun InitialsCard() {
    Box(Modifier.fillMaxWidth().height(100.dp).background(Color.LightGray).padding(vertical = 16.dp),
        contentAlignment = Alignment.Center) {
        Box(Modifier.fillMaxHeight().aspectRatio(1f).clip(CircleShape).background(Color.DarkGray),
            contentAlignment = Alignment.Center) {
            Text("РА", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

// Task 6: vector resource and placement
@Composable fun CircleExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Box(Modifier.size(240.dp, 120.dp).background(Color.Black)) {
            Image(painterResource(R.drawable.circle), contentDescription = "Оранжевый круг",
                modifier = Modifier.align(Alignment.TopEnd).size(48.dp))
        }
        Box(Modifier.size(240.dp, 120.dp).background(Color.Blue), contentAlignment = Alignment.Center) {
            Icon(painterResource(R.drawable.circle), contentDescription = "Фиолетовый круг",
                tint = Color.Magenta, modifier = Modifier.fillMaxSize())
        }
    }
}

// Task 7: Column and Row examples
@Composable fun LayoutExamples() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Column(Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Column", fontSize = 24.sp)
            Spacer(Modifier.height(12.dp))
            Text("Элементы расположены по вертикали")
            Button(onClick = {}) { Text("Продолжить") }
        }
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Row", fontWeight = FontWeight.Bold)
                Text("Элементы расположены по горизонтали")
            }
            Icon(Icons.Default.Star, contentDescription = "Звезда", tint = Color(0xFFFFB900))
        }
    }
}

// Task 8: +250 ml increments; day finish updates streak, resets water
@Composable fun WaterTracker() {
    var waterCount by rememberSaveable { mutableIntStateOf(0) }
    var streakCount by rememberSaveable { mutableIntStateOf(0) }
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text("Трекер воды", style = MaterialTheme.typography.headlineMedium)
        Text("Выпито: $waterCount мл", fontSize = 24.sp)
        Text("Дней подряд: $streakCount")
        Button(onClick = { waterCount += 250 }) { Text("+250 мл") }
        Button(onClick = {
            streakCount = if (waterCount >= 1500) streakCount + 1 else 0
            waterCount = 0
        }) { Text("Завершить день") }
        OutlinedButton(onClick = { waterCount = 0 }) { Text("Сброс воды") }
    }
}
