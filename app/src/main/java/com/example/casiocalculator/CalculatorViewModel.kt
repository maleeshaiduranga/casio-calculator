package com.example.casiocalculator

import androidx.lifecycle.ViewModel
import com.example.casiocalculator.math.MathEvaluator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class AngleMode {
    DEG, RAD
}

data class CalculatorState(
    val input: String = "",
    val result: String = "",
    val isShift: Boolean = false,
    val isAlpha: Boolean = false,
    val angleMode: AngleMode = AngleMode.DEG,
    val memory: String = "0",
    val isDarkMode: Boolean = false
)

class CalculatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CalculatorState())
    val state: StateFlow<CalculatorState> = _state.asStateFlow()

    private val evaluator = MathEvaluator()

    fun onEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.KeyPress -> handleKeyPress(event.key)
            is CalculatorEvent.Calculate -> calculateResult()
            is CalculatorEvent.Clear -> clear()
            is CalculatorEvent.Delete -> deleteLast()
            is CalculatorEvent.ToggleShift -> toggleShift()
            is CalculatorEvent.ToggleAlpha -> toggleAlpha()
            is CalculatorEvent.ToggleAngleMode -> toggleAngleMode()
            is CalculatorEvent.ToggleTheme -> toggleTheme()
        }
    }

    private fun handleKeyPress(key: String) {
        _state.update { currentState ->
            // Reset shift/alpha after a key press usually, but for simplicity let's do it manually or per key
            val newInput = currentState.input + key
            currentState.copy(
                input = newInput,
                isShift = false,
                isAlpha = false
            )
        }
    }

    private fun calculateResult() {
        _state.update { currentState ->
            val result = evaluator.evaluate(currentState.input, currentState.angleMode == AngleMode.DEG)
            currentState.copy(result = result)
        }
    }

    private fun clear() {
        _state.update { it.copy(input = "", result = "") }
    }

    private fun deleteLast() {
        _state.update { currentState ->
            if (currentState.input.isNotEmpty()) {
                currentState.copy(input = currentState.input.dropLast(1))
            } else {
                currentState
            }
        }
    }

    private fun toggleShift() {
        _state.update { it.copy(isShift = !it.isShift, isAlpha = false) }
    }

    private fun toggleAlpha() {
        _state.update { it.copy(isAlpha = !it.isAlpha, isShift = false) }
    }
    
    private fun toggleAngleMode() {
        _state.update { 
            it.copy(angleMode = if (it.angleMode == AngleMode.DEG) AngleMode.RAD else AngleMode.DEG) 
        }
    }

    private fun toggleTheme() {
        _state.update { it.copy(isDarkMode = !it.isDarkMode) }
    }
}

sealed class CalculatorEvent {
    data class KeyPress(val key: String) : CalculatorEvent()
    object Calculate : CalculatorEvent()
    object Clear : CalculatorEvent()
    object Delete : CalculatorEvent()
    object ToggleShift : CalculatorEvent()
    object ToggleAlpha : CalculatorEvent()
    object ToggleAngleMode : CalculatorEvent()
    object ToggleTheme : CalculatorEvent()
}
