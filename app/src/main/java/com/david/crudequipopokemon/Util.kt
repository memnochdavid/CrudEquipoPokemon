package com.david.crudequipopokemon

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class Util {
    companion object {

        fun existePoke(equipo: List<PokemonFB>, nombre: String): Boolean {
            return equipo.any { it.name!!.lowercase() == nombre.lowercase() }
        }


        fun obtenerEquipo(db_ref: DatabaseReference, contexto: Context): MutableList<PokemonFB> {
            val equipo = mutableListOf<PokemonFB>()

            db_ref.child("equipo").child("pokemon")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach { club ->
                            val poke = club.getValue(PokemonFB::class.java)
                            equipo.add(poke!!)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(contexto, "Error al consultar el equipo", Toast.LENGTH_SHORT)
                            .show()
                    }

                })
            return equipo
        }

        fun registraPoke(db_ref: DatabaseReference,id: String, poke: PokemonFB) {
            db_ref.child("equipo").child("pokemon").child(id).setValue(poke)
        }

        //LO CAMBIAREMOS
        suspend fun guardarFoto(almacen: StorageReference, id: String, foto: Uri): String {
            var urlAlmacen: Uri
            urlAlmacen =
                almacen.child("fotos").child(id).putFile(foto).await()
                    .storage.downloadUrl.await()

            return urlAlmacen.toString()
        }

        fun tostadaCorrutina(activity: AppCompatActivity, contexto: Context, texto: String){
            activity.runOnUiThread{
                Toast.makeText(contexto,texto,Toast.LENGTH_SHORT).show()
            }
        }

        fun animacion_carga(contexto: Context): CircularProgressDrawable {
            val animacion = CircularProgressDrawable(contexto)
            animacion.strokeWidth = 5f
            animacion.centerRadius = 30f
            animacion.start()

            return animacion
        }

        val transicion = DrawableTransitionOptions.withCrossFade(500)

        fun opcionesGlide(context: Context): RequestOptions {
            val options = RequestOptions()
                .placeholder(animacion_carga(context))
                .fallback(R.drawable.pokeball)
                .error(R.drawable.pokeball)
            return options
        }


    }
}