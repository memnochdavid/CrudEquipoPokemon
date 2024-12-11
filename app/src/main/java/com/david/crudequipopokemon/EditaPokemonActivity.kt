package com.david.crudequipopokemon

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.david.crudequipopokemon.databinding.ActivityEditaPokemonBinding
import com.david.crudequipopokemon.databinding.ActivityRegistraBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.appwrite.Client
import io.appwrite.models.InputFile
import io.appwrite.services.Storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditaPokemonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditaPokemonBinding//importamos el layoput al completo
    //Firebase
    private lateinit var refDB: DatabaseReference
    private var url_foto: Uri? = null
    //AppWriteStorage
    private lateinit var id_projecto: String
    private lateinit var id_bucket: String
    //Scope
    lateinit var scopeUser: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_edita_pokemon)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //se inicializa el binding
        binding = ActivityEditaPokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializamos el scope
        scopeUser = MainScope()

        //firebase
        refDB = FirebaseDatabase.getInstance().reference

        //re recupera el intent
        val pokemon = intent.getSerializableExtra("pokemon") as PokemonFB



        Glide.with(this)
            .load(pokemon.imagenFB)
            .fitCenter()
            .into(binding.foto)

        binding.nombreTextInputEdit.setText(pokemon.name)



        //AppWriteStorage
        id_projecto = "67542604001bce94410d"
        id_bucket = "6754262800031b5bc5bb"

        val client = Client()
            .setEndpoint("https://cloud.appwrite.io/v1")
            .setProject(id_projecto)

        val storage = Storage(client)


        var newPokemon=pokemon
        var nombre=pokemon.name
        var tipo=pokemon.tipo as MutableList<PokemonTipo>
        var foto=pokemon.imagenFB
        var tipo1=pokemon.tipo[0]
        var tipo2:PokemonTipo=PokemonTipo.NULL
        if(pokemon.tipo.size>1) {
            tipo2 = pokemon.tipo[1]
        }
        var num=pokemon.num
        var puntuacion=pokemon.puntuacion

        var foto_seleccionada=false

        binding.foto.setOnClickListener {
            accesoGaleria.launch("image/*")
            foto_seleccionada=true
        }


        //spinner
        val tipos= PokemonTipo.entries

        val predefinedIndex1 = tipos.indexOf(tipo.getOrNull(0) ?: PokemonTipo.NULL)
        val predefinedIndex2 = tipos.indexOf(tipo.getOrNull(1) ?: PokemonTipo.NULL)

        binding.tipoPokemon1.setSelection(predefinedIndex1)
        binding.tipoPokemon2.setSelection(predefinedIndex2)

        val adapter1 = ArrayAdapter(this, R.layout.spinner_a, tipos)
        adapter1.setDropDownViewResource(R.layout.spinner_b)
        binding.tipoPokemon1.adapter = adapter1
        binding.tipoPokemon1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position) as PokemonTipo
                if (selectedType != PokemonTipo.NULL) {
                    if (tipo.size < 1) {
                        tipo.add(selectedType)
                    } else {
                        tipo[0] = selectedType
                    }
                } else {
                    if (selectedType == PokemonTipo.NULL) {
                        tipo.removeAt(0)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nada
            }
        }

        //las estrellas
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val parentLayout = binding.cuatro
        parentLayout.removeAllViews()

        for (i in 1..5) {
            val imageView = ImageView(this)
            imageView.layoutParams = params
            imageView.setImageResource(if (i <= puntuacion) R.drawable.star_full else R.drawable.star_empty)
            parentLayout.addView(imageView, params)
        }



        binding.star1.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_full)
            binding.star2.setImageResource(R.drawable.star_empty)
            binding.star3.setImageResource(R.drawable.star_empty)
            binding.star4.setImageResource(R.drawable.star_empty)
            binding.star5.setImageResource(R.drawable.star_empty)
            puntuacion=1f
        }
        binding.star2.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_full)
            binding.star2.setImageResource(R.drawable.star_full)
            binding.star3.setImageResource(R.drawable.star_empty)
            binding.star4.setImageResource(R.drawable.star_empty)
            binding.star5.setImageResource(R.drawable.star_empty)
            puntuacion=2f
        }
        binding.star3.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_full)
            binding.star2.setImageResource(R.drawable.star_full)
            binding.star3.setImageResource(R.drawable.star_full)
            binding.star4.setImageResource(R.drawable.star_empty)
            binding.star5.setImageResource(R.drawable.star_empty)
            puntuacion=3f
        }
        binding.star4.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_full)
            binding.star2.setImageResource(R.drawable.star_full)
            binding.star3.setImageResource(R.drawable.star_full)
            binding.star4.setImageResource(R.drawable.star_full)
            binding.star5.setImageResource(R.drawable.star_empty)
            puntuacion=4f
        }
        binding.star5.setOnClickListener {
            binding.star1.setImageResource(R.drawable.star_full)
            binding.star2.setImageResource(R.drawable.star_full)
            binding.star3.setImageResource(R.drawable.star_full)
            binding.star4.setImageResource(R.drawable.star_full)
            binding.star5.setImageResource(R.drawable.star_full)
            puntuacion=5f
        }

        val adapter2 = ArrayAdapter(this, R.layout.spinner_a, tipos)//el tema para el objeto del layout
        adapter2.setDropDownViewResource(R.layout.spinner_b)//el tema para la lista que se despliega
        binding.tipoPokemon2.adapter = adapter2
        binding.tipoPokemon2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position) as PokemonTipo
                if (selectedType != PokemonTipo.NULL) {
                    if (tipo.size < 2) {
                        tipo.add(selectedType)
                    } else {
                        tipo[1] = selectedType
                    }
                } else {
                    if (tipo.size >= 2 && selectedType == PokemonTipo.NULL) {
                        tipo.removeAt(1)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //nada
            }
        }



        //confirma
        binding.edita.setOnClickListener {
            //nombre
            nombre=binding.nombreTextInputEdit.text.toString()
            num=binding.numeroTextInputEdit.text.toString().toInt()
            //valida los datos introducidos y muestra los errores

            if(!validaNombre(nombre)) {
                binding.nombreLayout.error="El nombre no puede estar vacío!"
            }
            else if(!validaTipo(tipo)){
                binding.errorTipo.visibility= View.VISIBLE
            }
            else if(!foto_seleccionada){
                binding.errorFoto.visibility= View.VISIBLE
            }
            else{
                binding.errorTipo.visibility= View.GONE
                binding.errorFoto.visibility= View.GONE

                val identificador_poke = pokemon.id

                //subimos la imagen a appwrite storage y los datos a firebase
                var identificadorAppWrite = identificador_poke?.substring(1, 20) ?: "" // coge el identificador y lo adapta a appwrite

                //necesario para crear un archivo temporal con la imagen
                val inputStream = this.contentResolver.openInputStream(url_foto!!)
                scopeUser.launch(Dispatchers.IO) {//scope para las funciones de appwrite, pero ya aprovechamos y metemos el código de firebase
                    try{
                        storage.deleteFile(
                            bucketId = id_bucket,
                            fileId = pokemon.id_imagen!!
                        )

                        val file = inputStream.use { input ->
                            val tempFile = kotlin.io.path.createTempFile(identificadorAppWrite).toFile()
                            if (input != null) {
                                tempFile.outputStream().use { output ->
                                    input.copyTo(output)
                                }
                            }
                            InputFile.fromFile(tempFile) // tenemos un archivo temporal con la imagen
                        }
                        storage.createFile(
                            bucketId = id_bucket,
                            fileId = identificadorAppWrite,
                            file = file
                        )
                        foto = "https://cloud.appwrite.io/v1/storage/buckets/$id_bucket/files/$identificadorAppWrite/preview?project=$id_projecto"

                        newPokemon = PokemonFB(identificador_poke,foto,identificadorAppWrite,nombre,tipo,num,puntuacion)

                        //subimos los datos a firebase
                        refDB.child("equipo").child("pokemon").child(identificador_poke!!).setValue(newPokemon)

                    }catch (e: Exception){
                        Log.e("UploadError", "Error al subir la imagen: ${e.message}")
                    }
                }

            }


        }
        binding.volver.setOnClickListener {
            finish()
        }


    }


    fun validaNombre(input:String):Boolean{
        if(input.length<1) return false
        else return true
    }

    fun validaTipo(input:List<PokemonTipo>):Boolean{
        if(input.size<1) return false
        else return true
    }
    private val accesoGaleria = registerForActivityResult(ActivityResultContracts.GetContent())
    { uri: Uri? ->
        if (uri != null) {
            url_foto = uri
            binding.foto.setImageURI(url_foto)
        }
    }
}