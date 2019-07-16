fun main() {

    var mapa = Mapa()

    /*mapa.shouldDrawMarcadores {
        it is Marcador && it.enable
    }

    mapa.checkMarcadores()
    */

    mapa.checkAndDrawMarcadoresInRange {

    }

}

class Mapa() {

    val listOfLocalizacao = mutableListOf<Localizacao>().apply { //inicialização e inclusão de itens a lista

        add(marcador {
            enable = true
            latitude = 10.3
            longitude = 13.5
        })

        add(marcador {
            latitude = 11.5
            longitude = 10.5
        })

        add(marcador {
            enable = true
            latitude = 15.3
            longitude = 12.5
        })

        add(marcador {
            enable = true
            latitude = -10.3
            longitude = -13.5
        })

        add(perimetro {

            latitude = 90.5
            longitude = 80.2
        })

        add(perimetro {

            latitude = -90.5
            longitude = 80.2
        })

        add(perimetro {

            latitude = 90.5
            longitude = -80.2
        })
    }

    val listOfPerimetro = listOfLocalizacao.filter { it is Perimetro }
    val listOfMarcadores = listOfLocalizacao.filter { it is Marcador }

    private var drawMarcador: (localizacao: Localizacao) -> Boolean = {
        false
    }

    private var rangeMarcador: (local : Localizacao, firstValue : Int, secondValue : Int) -> Boolean = { _, _, _ ->
        false
    }

    fun shouldDrawMarcadores(event: (localizacao: Localizacao) -> Boolean) {
        drawMarcador = event
    }

    fun shouldDrawMarcadoresRange(event: (local : Localizacao, firstValue : Int, secondValue : Int) -> Boolean) {
        rangeMarcador = event
    }

    fun checkMarcadores() {

        listOfLocalizacao.forEach {
            if (drawMarcador(it)) {
                draw(it as Marcador)
            }
        }
    }

    fun checkAndDrawMarcadoresInRange(firstValue : Int, secondValue : Int) {

        listOfLocalizacao.forEach{ local ->
            if(rangeMarcador(local, firstValue, secondValue))
                draw(local as Marcador)
        }

    }

    fun draw(marcador: Marcador) {
        println("drawing marcador ${marcador.toString()}")
    }
}


open class Localizacao(var latitude: Double, var longitude: Double, var enable: Boolean)

class Marcador(latitude: Double, longitude: Double, enable: Boolean) : Localizacao(latitude, longitude, enable) {
    constructor(): this(0.0,0.0, false)
}

class Perimetro(latitude: Double, longitude: Double, enable: Boolean) : Localizacao(latitude, longitude, enable) {
    constructor(): this(0.0,0.0, false)
}

fun marcador(marcador: Marcador.() -> Unit) = Marcador().apply(marcador)
fun perimetro(perimetro: Perimetro.() -> Unit) = Perimetro().apply(perimetro)