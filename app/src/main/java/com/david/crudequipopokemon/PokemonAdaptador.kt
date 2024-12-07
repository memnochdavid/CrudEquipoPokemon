package com.david.crudequipopokemon

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.google.firebase.database.FirebaseDatabase
import io.appwrite.Client
import io.appwrite.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class PokemonAdaptador(private val equipo: MutableList<PokemonFB>) :
    RecyclerView.Adapter<PokemonAdaptador.PokemonViewHolder>() {

    private lateinit var contexto: Context
    private var lista_filtrada = equipo

    inner class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImage: ImageView = itemView.findViewById(R.id.pokemonImage)
        val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
        val pokemonTipo1: ImageView = itemView.findViewById(R.id.tipo1)
        val pokemonTipo2: ImageView = itemView.findViewById(R.id.tipo2)
        val editar: ImageView = itemView.findViewById(R.id.edita)
        val borrar: ImageView = itemView.findViewById(R.id.borra)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.objeto_pokemon, parent, false)
        contexto=parent.context
        return PokemonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon_actual = lista_filtrada[position]
        holder.pokemonName.text = pokemon_actual.name
        holder.pokemonTipo1.setImageResource(enumToDrawableFB(pokemon_actual.tipo[0]))
        if (pokemon_actual.tipo.size > 1) {
            holder.pokemonTipo2.setImageResource(enumToDrawableFB(pokemon_actual.tipo[1]))
            holder.pokemonTipo2.visibility = View.VISIBLE
        }



        val URL:String?=when(pokemon_actual.imagenFB){
            ""->null
            else->pokemon_actual.imagenFB
        }

        Glide.with(contexto)
            .load(URL)
            .apply(Util.opcionesGlide(contexto))
            .transition(Util.transicion)
            .into(holder.pokemonImage)

        holder.pokemonTipo1.setImageResource(enumToDrawableFB(pokemon_actual.tipo.get(0)))

        if (pokemon_actual.tipo.size ==2) {
            holder.pokemonTipo2.visibility = View.VISIBLE////
            holder.pokemonTipo2.setImageResource(enumToDrawableFB(pokemon_actual.tipo.get(1)))
        }else holder.pokemonTipo2.visibility = View.GONE

        holder.editar.setOnClickListener{
            val intent= Intent(contexto,EditaPokemonActivity::class.java)
            intent.putExtra("pokemon",pokemon_actual)
            contexto.startActivity(intent)
        }

        holder.borrar.setOnClickListener{
            val refDB= FirebaseDatabase.getInstance().reference
            val id_projecto = "67542604001bce94410d"
            val id_bucket = "6754262800031b5bc5bb"

            val client = Client()
                .setEndpoint("https://cloud.appwrite.io/v1")
                .setProject(id_projecto)

            val storage = Storage(client)
            val scopeUser = MainScope()

            scopeUser.launch(Dispatchers.IO) {
                storage.deleteFile(
                    bucketId = id_bucket,
                    fileId = pokemon_actual.id_imagen!!
                )
            }

            lista_filtrada.removeAt(position)
            refDB.child("equipo").child("pokemon").child(pokemon_actual.id!!).removeValue()
            Toast.makeText(contexto,"${pokemon_actual.name} liberado",Toast.LENGTH_SHORT).show()
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,lista_filtrada.size)

        }

    }
    override fun getItemCount() = lista_filtrada.size


}