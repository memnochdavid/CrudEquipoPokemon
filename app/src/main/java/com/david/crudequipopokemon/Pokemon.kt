package com.david.crudequipopokemon

import java.io.Serializable

data class PokemonFB(
    var id: String? = null,
    var imagenFB: String? = null,
    var id_imagen: String? = null,
    var name: String="",
    var tipo: List<PokemonTipo> = mutableListOf(),
    var stability:Int=0
): Serializable{
    constructor(
        name: String,
        tipo: List<PokemonTipo>
    ) : this(null,null, null, name, tipo)
}

enum class PokemonTipo(val tag: String) {
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

fun enumToDrawableFB(tipo:PokemonTipo):Int{
    return when(tipo){
        PokemonTipo.PLANTA -> R.drawable.planta
        PokemonTipo.AGUA -> R.drawable.agua
        PokemonTipo.FUEGO -> R.drawable.fuego
        PokemonTipo.LUCHA -> R.drawable.lucha
        PokemonTipo.VENENO -> R.drawable.veneno
        PokemonTipo.ACERO -> R.drawable.acero
        PokemonTipo.BICHO -> R.drawable.bicho
        PokemonTipo.DRAGON -> R.drawable.dragon
        PokemonTipo.ELECTRICO -> R.drawable.electrico
        PokemonTipo.HADA -> R.drawable.hada
        PokemonTipo.HIELO -> R.drawable.hielo
        PokemonTipo.PSIQUICO -> R.drawable.psiquico
        PokemonTipo.ROCA -> R.drawable.roca
        PokemonTipo.TIERRA -> R.drawable.tierra
        PokemonTipo.SINIESTRO -> R.drawable.siniestro
        PokemonTipo.NORMAL -> R.drawable.normal
        PokemonTipo.VOLADOR -> R.drawable.volador
        PokemonTipo.FANTASMA -> R.drawable.fantasma
        else -> { R.drawable.fantasma}
    }
}
