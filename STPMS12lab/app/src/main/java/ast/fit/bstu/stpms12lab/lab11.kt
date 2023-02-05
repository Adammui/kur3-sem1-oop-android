fun main() {

    var KING: ChessFigure = ChessFigure("KING", "WHITE", Positions.A1)

    KING.MoveUp();
    KING.MoveDown();
    KING.MoveDown();
    KING.MoveUp();
    KING.MoveUp();

    KING.figureCondition.isCanGo = true;
    KING.figureCondition.isBattle = true;

    KING.getFigureInfo();

    //функция расширения
    fun ChessFigure.clearPosition(cf: ChessFigure) {
        cf.position = null;
    }

    val a = A();
    a.display();
    a.converter("*");

}


interface IChessFigure {
    var color: String
    var position: Positions?
    fun MoveUp()
    fun MoveDown()
    fun StandartMove() = println("Standart Move")
}

//первичный конструктор (основной)
class ChessFigure(_name: String) : IChessFigure {

    //вспомогательный конструктор
    constructor(_name: String, _color: String, _position: Positions) : this(_name) {
        this.color = _color
        this.position = _position
    }

    var name: String

    override lateinit var color: String
    override var position: Positions? = null

    //инициализатор
    init {
        name = _name
    }

    var moveStepNumber: Int = 0
    var moveHistory: String = "Move History: ${System.lineSeparator()}"
    var figureCondition: FigureCondition = FigureCondition()


    override fun MoveUp() {
        moveStepNumber++
        println("Moving Up!")
        position = Positions.values().toList().shuffled().first();
        moveHistory += ("$moveStepNumber: Moving Up! - Position: $position ${System.lineSeparator()}")
    }

    override fun MoveDown() {
        moveStepNumber++
        println("Moving Down!")
        position = Positions.values().toList().shuffled().first();
        moveHistory += ("$moveStepNumber: Moving Down! - Position: $position ${System.lineSeparator()}")
    }

    fun getFigureInfo() {

        println("Figure INFO:")

        println(
            "Figure name: ${this.name} ${System.lineSeparator()}" +
                    "Figure color: ${this.color} ${System.lineSeparator()}" +
                    "Figure position: ${this.position} ")

        println("isBattle: ${figureCondition.isBattle} ${System.lineSeparator()}" +
                "isCanFight: ${figureCondition.isCanFight} ${System.lineSeparator()}" +
                "isCanGo: ${figureCondition.isCanGo} ${System.lineSeparator()}")

        println(moveHistory);
    }

    class FigureCondition {
        var isBattle: Boolean = false
        var isCanFight: Boolean = false
        var isCanGo: Boolean = false
    }
}

enum class Positions {
    A1, A2, A3, A4, A5, A6, A7, A8,
    B1, B2, B3, B4, B5, B6, B7, B8,
    C1, C2, C3, C4, C5, C6, C7, C8,
    D1, D2, D3, D4, D5, D6, D7, D8,
    E1, E2, E3, E4, E5, E6, E7, E8,
    F1, F2, F3, F4, F5, F6, F7, F8,
    G1, G2, G3, G4, G5, G6, G7, G8,
    H1, H2, H3, H4, H5, H6, H7, H8;
}
//класс данных
data class Player(val name:String, val category:Int, val wins:Int, val loses:Int){}
//object используется када экземпляр некоторого класса с незначительной модификацией
val helloWorld = object {
    val hello = "Hello"
    val world = "World"
    // `override` метода `toString()`
    override fun toString() = "$hello $world"
}
//3
class A{
    private lateinit var prop: String
    fun setUp() {
        prop = "100 + 200"
    }

    fun display() {
        if(::prop.isInitialized){
            println(prop)
        }
    }

    override operator fun equals(other: Any?) : Boolean{
        return true
    }

    fun converter (which: String): Unit? {
        fun plus(){println("Plus function")}
        fun minus() {println("Minus function")}
        fun divide() {println("Divide function")}
        fun multiply() {println("Multiple function")}

        if(which == "+")
            return plus()

        else if(which == "-")
            return minus()

        else if(which == "/")
            return divide()

        else if (which == "*")
            return multiply()

        else
            return null
    }

}