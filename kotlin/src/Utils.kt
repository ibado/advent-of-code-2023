import java.nio.file.Paths
import kotlin.io.path.Path
import kotlin.io.path.readLines

fun readInput(name: String): List<String> {
    val currentDir = ClassLoader.getSystemResource(".")!!.toURI()
    val rootDir = Paths.get(currentDir).parent.parent.parent.parent.parent
    return Path("$rootDir/input/$name").readLines()
}

fun Any?.println() = println(this)
