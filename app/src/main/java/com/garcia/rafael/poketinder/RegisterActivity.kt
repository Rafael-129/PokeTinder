package com.garcia.rafael.poketinder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.garcia.rafael.poketinder.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var sharedPreferencesRepository: SharedPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesRepository = SharedPreferencesRepository().also {
            it.setSharedPreference(this)
        }

        binding.btnRegister.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString()
            val password2 = binding.edtPassword2.text.toString()

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Ingrese un email válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 8) {
                Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != password2) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sharedPreferencesRepository.saveUserEmail(email)
            sharedPreferencesRepository.saveUserPassword(password)
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            finish() // Retorna al login
        }

        // "Ya tengo una cuenta"
        val btnYaTengoCuenta = findViewById<Button>(R.id.btnYaTengoCuenta)
        btnYaTengoCuenta?.setOnClickListener {
            finish()
        }

        // BackNavigation
        binding.btnBackClose.setOnClickListener {
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}