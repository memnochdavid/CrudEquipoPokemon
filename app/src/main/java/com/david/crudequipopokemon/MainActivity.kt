package com.david.crudequipopokemon

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.david.crudequipopokemon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding//importamos el layoput al completo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //se inicializa el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registra.setOnClickListener {
            val intent = Intent(this, RegistraActivity::class.java)
            startActivity(intent)
        }
        binding.verEquipo.setOnClickListener {
            val intent = Intent(this, EquipoActivity::class.java)
            startActivity(intent)
        }


    }
}