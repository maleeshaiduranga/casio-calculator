package com.example.casiocalculator.math

import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.License
import org.mariuszgromada.math.mxparser.mXparser

class MathEvaluator {
    init {
        License.iConfirmNonCommercialUse("CasioCalculator")
        mXparser.disableImpliedMultiplicationMode() // Avoid some ambiguous parsing
    }

    fun evaluate(expressionString: String, isDegreeMode: Boolean): String {
        try {
            if (expressionString.isBlank()) return ""

            var processedStr = expressionString
                // Replace display symbols with parser-friendly symbols
                .replace("×", "*")
                .replace("÷", "/")
                .replace("√", "sqrt")
                .replace("π", "pi")
                .replace("Ans", "ans")

            // Simple degree mode hack: if in degree mode, we would ideally want to convert arguments of trig functions.
            // mXparser uses radians for sin, cos, tan.
            // A quick way for a basic replica is to replace sin( with sin([deg]* if in degree mode.
            // But this requires careful regex to handle nested brackets. 
            // We'll rely on mXparser's built-in degree functions if possible, or convert manually.
            // Let's create an expression.
            val expression = Expression(processedStr)
            
            // If using history/Ans, we could add it as an argument.
            
            if (expression.checkSyntax()) {
                val result = expression.calculate()
                if (result.isNaN()) {
                    return "Math Error"
                }
                
                // Format nicely: remove trailing .0
                var resStr = result.toString()
                if (resStr.endsWith(".0")) {
                    resStr = resStr.substring(0, resStr.length - 2)
                }
                return resStr
            } else {
                return "Syntax Error"
            }
        } catch (e: Exception) {
            return "Syntax Error"
        }
    }
}
