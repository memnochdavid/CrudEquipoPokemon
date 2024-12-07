package com.david.crudequipopokemon

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class Pokemon(
    val id: String,
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val imagen: String
)
@Parcelize
data class PokemonFB(
    var imagenFB: String? = null,//soporte en la nube
    var name: String="",
    var tipo: List<PokemonTipoFB> = listOf(),
    var stability:Int=0
): Serializable, Parcelable {
    constructor(
        name: String,
        tipo: List<PokemonTipoFB>
    ) : this(null, name, tipo)
}

enum class PokemonTipoFB(val tag: String) {
    PLANTA("planta"),
    AGUA("agua"),
    FUEGO("fuego"),
    LUCHA("lucha"),
    VENENO("veneno"),
    ACERO("acero"),
    BICHO("bicho"),
    DRAGON("dragon"),
    ELECTRICO("electrico"),
    HADA("hada"),
    HIELO("hielo"),
    PSIQUICO("psiquico"),
    ROCA("roca"),
    TIERRA("tierra"),
    SINIESTRO("siniestro"),
    NORMAL("normal"),
    VOLADOR("volador"),
    FANTASMA("fantasma"),
    NULL("null");
}

fun enumToDrawableFB(tipo:PokemonTipoFB):Int{
    return when(tipo){
        PokemonTipoFB.PLANTA -> R.drawable.planta
        PokemonTipoFB.AGUA -> R.drawable.agua
        PokemonTipoFB.FUEGO -> R.drawable.fuego
        PokemonTipoFB.LUCHA -> R.drawable.lucha
        PokemonTipoFB.VENENO -> R.drawable.veneno
        PokemonTipoFB.ACERO -> R.drawable.acero
        PokemonTipoFB.BICHO -> R.drawable.bicho
        PokemonTipoFB.DRAGON -> R.drawable.dragon
        PokemonTipoFB.ELECTRICO -> R.drawable.electrico
        PokemonTipoFB.HADA -> R.drawable.hada
        PokemonTipoFB.HIELO -> R.drawable.hielo
        PokemonTipoFB.PSIQUICO -> R.drawable.psiquico
        PokemonTipoFB.ROCA -> R.drawable.roca
        PokemonTipoFB.TIERRA -> R.drawable.tierra
        PokemonTipoFB.SINIESTRO -> R.drawable.siniestro
        PokemonTipoFB.NORMAL -> R.drawable.normal
        PokemonTipoFB.VOLADOR -> R.drawable.volador
        PokemonTipoFB.FANTASMA -> R.drawable.fantasma
        else -> { R.drawable.fantasma}
    }
}
