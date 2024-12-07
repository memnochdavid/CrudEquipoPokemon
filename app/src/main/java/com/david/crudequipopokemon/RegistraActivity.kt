package com.david.crudequipopokemon

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.david.crudequipopokemon.databinding.ActivityMainBinding
import com.david.crudequipopokemon.databinding.ActivityRegistraBinding

class RegistraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistraBinding//importamos el layoput al completo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registra)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //se inicializa el binding
        binding = ActivityRegistraBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}