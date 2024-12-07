package com.david.crudequipopokemon

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.david.crudequipopokemon.databinding.ActivityEquipoBinding
import com.david.crudequipopokemon.databinding.ActivityMainBinding
import com.david.crudequipopokemon.databinding.ActivityRegistraBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EquipoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEquipoBinding
    private lateinit var equipo:MutableList<PokemonFB>
    private lateinit var refDB: DatabaseReference
    private lateinit var adaptador: PokemonAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_equipo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        refDB= FirebaseDatabase.getInstance().reference
        equipo= mutableListOf()

        refDB.child("equipo")
            .child("pokemon")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    equipo.clear()
                    snapshot.children.forEach{ hijo: DataSnapshot?->
                        val pojo_pokemon=hijo?.getValue(PokemonFB::class.java)
                        equipo.add(pojo_pokemon!!)
                    }
                    binding.pokelistRV.adapter?.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                    println(error.message)
                }
            })


        adaptador= PokemonAdaptador(equipo)
        binding.pokelistRV.adapter=adaptador
        binding.pokelistRV.setHasFixedSize(true)
        binding.pokelistRV.layoutManager= LinearLayoutManager(applicationContext)

        binding.volver.setOnClickListener{
            finish()
        }






    }
}