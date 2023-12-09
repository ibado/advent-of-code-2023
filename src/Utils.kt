import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String) = Path("src/$name").readLines()

fun Any?.println() = println(this)
