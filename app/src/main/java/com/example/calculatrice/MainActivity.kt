package com.example.calculatrice

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextResult: EditText  // Le champ pour afficher le résultat
    private var currentInput: String = "" // La saisie de l'utilisateur
    private var operator: String = "" // L'opérateur sélectionné (+, -, *, /)
    private var firstValue: Double = 0.0 // La première valeur avant un opérateur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Relier la vue XML à cette activité

        // Récupérer la référence de l'EditText pour afficher les résultats
        editTextResult = findViewById(R.id.editTextResult)

        // Ajouter des écouteurs d'événements pour chaque bouton (chiffres)
        findViewById<View>(R.id.button0).setOnClickListener { appendNumber("0") }
        findViewById<View>(R.id.button1).setOnClickListener { appendNumber("1") }
        findViewById<View>(R.id.button2).setOnClickListener { appendNumber("2") }
        findViewById<View>(R.id.button3).setOnClickListener { appendNumber("3") }
        findViewById<View>(R.id.button4).setOnClickListener { appendNumber("4") }
        findViewById<View>(R.id.button5).setOnClickListener { appendNumber("5") }
        findViewById<View>(R.id.button6).setOnClickListener { appendNumber("6") }
        findViewById<View>(R.id.button7).setOnClickListener { appendNumber("7") }
        findViewById<View>(R.id.button8).setOnClickListener { appendNumber("8") }
        findViewById<View>(R.id.button9).setOnClickListener { appendNumber("9") }

        // Ajouter des écouteurs pour les opérateurs
        findViewById<View>(R.id.buttonPlus).setOnClickListener { setOperator("+") }
        findViewById<View>(R.id.buttonMinus).setOnClickListener { setOperator("-") }
        findViewById<View>(R.id.buttonMultiply).setOnClickListener { setOperator("*") }
        findViewById<View>(R.id.buttonDivide).setOnClickListener { setOperator("/") }

        // Ajouter un écouteur pour le bouton point décimal
        findViewById<View>(R.id.buttonDot).setOnClickListener { appendDot() }

        // Bouton égal pour effectuer le calcul
        findViewById<View>(R.id.buttonEqual).setOnClickListener { calculateResult() }

        // Bouton clear pour réinitialiser l'entrée et le résultat
        findViewById<View>(R.id.buttonClear).setOnClickListener { clear() }
    }

    // Ajouter un chiffre à l'entrée actuelle
    private fun appendNumber(number: String) {
        currentInput += number // Ajouter le chiffre à l'entrée
        editTextResult.setText(currentInput) // Mettre à jour l'affichage
    }

    // Ajouter un point décimal si nécessaire
    private fun appendDot() {
        if (!currentInput.contains(".")) {  // Assure-toi qu'il n'y a pas déjà un point
            currentInput += "." // Ajouter un point décimal seulement s'il n'existe pas déjà
            editTextResult.setText(currentInput) // Mettre à jour l'affichage
        }
    }

    // Définir l'opérateur (+, -, *, /)
    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            firstValue = currentInput.toDouble() // Sauvegarder la première valeur
            currentInput = "" // Réinitialiser l'entrée pour la deuxième valeur
            operator = op // Enregistrer l'opérateur
        }
    }

    // Effectuer le calcul
    private fun calculateResult() {
        if (currentInput.isNotEmpty()) {
            // Vérification si currentInput est bien un nombre
            val secondValue = currentInput.toDoubleOrNull() ?: return // Si ce n'est pas un nombre, on arrête
            var result = 0.0

            // Calcul en fonction de l'opérateur
            when (operator) {
                "+" -> result = firstValue + secondValue
                "-" -> result = firstValue - secondValue
                "*" -> result = firstValue * secondValue
                "/" -> result = if (secondValue != 0.0) {
                    firstValue / secondValue
                } else {
                    // Affichage de l'erreur de division par zéro
                    editTextResult.setText(getString(R.string.error_message)) // Affiche "Error"
                    return
                }
            }

            // Afficher le résultat
            editTextResult.setText(result.toString())
            currentInput = result.toString() // Mettre à jour l'entrée pour continuer à partir du résultat
            operator = "" // Réinitialiser l'opérateur
        }
    }

    // Réinitialiser l'entrée et le champ de résultat
    private fun clear() {
        currentInput = ""
        operator = ""
        firstValue = 0.0
        editTextResult.setText("")
    }
}
