package com.example.casiocalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.casiocalculator.AngleMode
import com.example.casiocalculator.CalculatorEvent
import com.example.casiocalculator.CalculatorViewModel
import com.example.casiocalculator.ui.theme.*

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    // Dynamic Colors based on Theme
    val bgColor = if (state.isDarkMode) Color(0xFF1A1A1A) else Color(0xFFE0E0E0)
    val displayBgColor = if (state.isDarkMode) Color(0xFF859B73) else Color(0xFF9EAD8B)
    val btnDarkGrey = if (state.isDarkMode) Color(0xFF111111) else Color(0xFF333333)
    val btnLightGrey = if (state.isDarkMode) Color(0xFF444444) else Color(0xFFB0B0B0)
    val textMainColor = if (state.isDarkMode) Color.White else Color.Black
    val strokeColor = if (state.isDarkMode) Color.Black else Color.DarkGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp)
    ) {
        // Top Brand & Mode Area
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "SCICALC",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = textMainColor
                )
                Text(
                    text = "PRO-991",
                    fontSize = 14.sp,
                    color = textMainColor,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
            
            // Theme Toggle Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(btnDarkGrey)
                    .clickable { viewModel.onEvent(CalculatorEvent.ToggleTheme) }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = if (state.isDarkMode) "☀️ Light" else "🌙 Dark",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Screen
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(displayBgColor)
                .padding(12.dp)
        ) {
            // Status indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.isShift) StatusIndicator("S", Color.Black)
                if (state.isAlpha) StatusIndicator("A", Color.Black)
                StatusIndicator(state.angleMode.name, Color.Black)
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = state.input,
                fontSize = 28.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.result,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Keypad Area
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Row 1: SHIFT, ALPHA, MODE
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                SmallFunctionButton("SHIFT", TextYellow, btnDarkGrey, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.ToggleShift) })
                SmallFunctionButton("ALPHA", TextPurple, btnDarkGrey, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.ToggleAlpha) })
                SmallFunctionButton("MODE", textMainColor, btnDarkGrey, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.ToggleAngleMode) })
            }

            // Row 2: Scientific 1
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ScientificButton("x⁻¹", btnDarkGrey, strokeColor, shift = "x!", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("^(-1)")) })
                ScientificButton("√", btnDarkGrey, strokeColor, shift = "³√", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("√(")) })
                ScientificButton("x²", btnDarkGrey, strokeColor, shift = "³√", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("^2")) })
                ScientificButton("x^", btnDarkGrey, strokeColor, shift = "x√", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("^")) })
                ScientificButton("log", btnDarkGrey, strokeColor, shift = "10^", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("log10(")) })
                ScientificButton("ln", btnDarkGrey, strokeColor, shift = "e^", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("ln(")) })
            }

            // Row 3: Scientific 2
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ScientificButton("(-)", btnDarkGrey, strokeColor, shift = "A", alpha = "A", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("-")) })
                ScientificButton("o'\"", btnDarkGrey, strokeColor, shift = "B", alpha = "B", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("°")) })
                ScientificButton("hyp", btnDarkGrey, strokeColor, shift = "C", alpha = "C", onClick = { /* Not fully implemented */ })
                ScientificButton("sin", btnDarkGrey, strokeColor, shift = "sin⁻¹", alpha = "D", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("sin(")) })
                ScientificButton("cos", btnDarkGrey, strokeColor, shift = "cos⁻¹", alpha = "E", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("cos(")) })
                ScientificButton("tan", btnDarkGrey, strokeColor, shift = "tan⁻¹", alpha = "F", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("tan(")) })
            }

            // Row 4: Scientific 3
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ScientificButton("RCL", btnDarkGrey, strokeColor, shift = "STO", onClick = { /* Not fully implemented */ })
                ScientificButton("ENG", btnDarkGrey, strokeColor, shift = "←", onClick = { /* Not fully implemented */ })
                ScientificButton("(", btnDarkGrey, strokeColor, shift = "[", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("(")) })
                ScientificButton(")", btnDarkGrey, strokeColor, shift = "]", alpha = "X", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress(")")) })
                ScientificButton("S⇔D", btnDarkGrey, strokeColor, onClick = { /* Not fully implemented */ })
                ScientificButton("M+", btnDarkGrey, strokeColor, shift = "M-", alpha = "M", onClick = { /* Not fully implemented */ })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Numpad Grid
            val numpadSpacing = 12.dp
            Column(verticalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                // Row 1
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                    NumpadButton("7", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("7")) })
                    NumpadButton("8", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("8")) })
                    NumpadButton("9", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("9")) })
                    NumpadButton("DEL", Modifier.weight(1f), ButtonBlue, Color.White, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.Delete) })
                    NumpadButton("AC", Modifier.weight(1f), ButtonBlue, Color.White, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.Clear) })
                }
                // Row 2
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                    NumpadButton("4", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("4")) })
                    NumpadButton("5", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("5")) })
                    NumpadButton("6", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("6")) })
                    NumpadButton("×", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("×")) })
                    NumpadButton("÷", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("÷")) })
                }
                // Row 3
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                    NumpadButton("1", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("1")) })
                    NumpadButton("2", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("2")) })
                    NumpadButton("3", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("3")) })
                    NumpadButton("+", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("+")) })
                    NumpadButton("-", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("-")) })
                }
                // Row 4
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                    NumpadButton("0", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("0")) })
                    NumpadButton(".", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress(".")) })
                    NumpadButton("×10ˣ", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("×10^")) })
                    NumpadButton("Ans", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("Ans")) })
                    NumpadButton("=", Modifier.weight(1f), btnLightGrey, textMainColor, strokeColor, onClick = { viewModel.onEvent(CalculatorEvent.Calculate) })
                }
            }
        }
    }
}

@Composable
fun StatusIndicator(text: String, color: Color) {
    Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
}

@Composable
fun BorderedText(text: String, fillColor: Color, strokeColor: Color, fontSize: androidx.compose.ui.unit.TextUnit, fontWeight: FontWeight? = null) {
    Box(contentAlignment = Alignment.Center) {
        Text(
            text = text, 
            color = strokeColor, 
            fontSize = fontSize, 
            fontWeight = fontWeight,
            style = TextStyle(drawStyle = Stroke(width = 3f))
        )
        Text(text = text, color = fillColor, fontSize = fontSize, fontWeight = fontWeight)
    }
}

@Composable
fun SmallFunctionButton(text: String, textColor: Color, bgColor: Color, strokeColor: Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp, 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(bgColor)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            BorderedText(text = text, fillColor = textColor, strokeColor = strokeColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ScientificButton(
    primary: String,
    bgColor: Color,
    strokeColor: Color,
    shift: String? = null,
    alpha: String? = null,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(50.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (shift != null) {
                BorderedText(text = shift, fillColor = TextYellow, strokeColor = strokeColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            } else {
                Text(text = "", fontSize = 11.sp)
            }
            if (alpha != null) {
                BorderedText(text = alpha, fillColor = TextPurple, strokeColor = strokeColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            } else {
                Text(text = "", fontSize = 11.sp)
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .size(45.dp, 35.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(bgColor)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            BorderedText(text = primary, fillColor = Color.White, strokeColor = strokeColor, fontSize = 14.sp)
        }
    }
}

@Composable
fun NumpadButton(
    text: String,
    modifier: Modifier = Modifier,
    bgColor: Color,
    textColor: Color,
    strokeColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(55.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        BorderedText(text = text, fillColor = textColor, strokeColor = strokeColor, fontSize = 20.sp, fontWeight = FontWeight.Medium)
    }
}
