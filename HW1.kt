
import java.io.File
import kotlin.io.*
import kotlin.math.*

data class Stat (var count : Int = 0, var min : Double = 0.0, var max : Double = 0.0, var sum : Double = 0.0) {
    fun insert (value  : Double)    {
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
    val locations : MutableMap<String, Stat> = mutableMapOf()

    for (line in File(fileName).readLines()) {
        if (line[0] == '#')
            continue
        val (location, temperature)  = line.split(';')
        val value = temperature.toDouble()
        val statistics = locations.getOrPut(location) { Stat() }
        statistics.insert(value)
    }

    val sortedLoc = locations.toSortedMap()
    var subList = mutableListOf<String> ()
    for (entry in sortedLoc) {
        val line = entry.key
        val stat : Stat = entry.value
        val roundMin = (10.0 *stat.min).roundToInt()/10.0
        val roundMax = (10.0 *stat.max).roundToInt()/10.0
        val roundMean = (10.0 *stat.sum/stat.count).roundToInt()/10.0
        val subString  = "$line  $roundMin/$roundMean/$roundMax"
        subList.addLast(subString)
    }
    print (subList.joinToString(separator = ", ", prefix = "{ ",postfix = " }"))

}


