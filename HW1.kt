
import java.io.File
import kotlin.io.*
import kotlin.math.*

data class Stat (var count : Int = 0, var min : Double = 0.0, var max : Double = 0.0, var sum : Double = 0.0) {
    fun insert (value  : Double)  =  {
        if (count ==0)  {
            min =  value
            max = value
        }
        else {
            if (value < min)
                min = value
            if (value > max)
                max = value
        }
        sum+= value
        count++

    }
}

fun main() {
    val fileName  = "weather_stations.csv"
    var line : String
    var value : Double

    val locations : MutableMap<String, Stat> = mutableMapOf()
    var stat : Stat

    for (line in File(fileName).readLines()) {
        if (line[0] == '#')
            continue
        var words = line.split(';')
        value = words[1].toDouble()
        if (!locations.containsKey(words[0])) {
            locations[words[0]] = Stat (count = 1, min = value, max = value,sum = value)
        }
        else {
            locations[words[0]]?.insert(value)
        }
    }

    val sorted_loc = locations.toSortedMap()
    var roundMin : Double
    var roundMax : Double
    var roundMean : Double
    var bFirst = 1
    print("{ ")
    for (entry in sorted_loc) {
        if (bFirst != 1) print(",")
        bFirst = 0
        line = entry.key
        stat = entry.value
        roundMin = (10.0 *stat.min).roundToInt()/10.0
        roundMax = (10.0 *stat.max).roundToInt()/10.0
        roundMean = (10.0 *stat.sum/stat.count).roundToInt()/10.0
        print(" $line  $roundMin/$roundMean/$roundMax")

    }
    print("}")

}


