import kotlinx.coroutines.*
import java.util.Scanner

data class MarineObservation(
    val tp: String,          // 관측 종류
    val tm: String,          // 관측 시각
    val stnId: String,       // 지점 ID
    val stnKo: String,       // 지점명
    val lon: Double,         // 경도
    val lat: Double,         // 위도
    val wh: Double,          // 유의파고 (m)
    val wd: Int,             // 풍향 (degree)
    val ws: Double,          // 풍속 (m/s)
    val wsGst: Double,       // GUST 풍속 (m/s)
    val tw: Double,          // 해수면 온도 (°C)
    val ta: Double,          // 기온 (°C)
    val pa: Double,          // 해면기압 (hPa)
    val hm: Double           // 상대습도 (%)
)

fun main() = runBlocking {
    val apiUrl = "https://apihub.kma.go.kr/api/typ01/url/sea_obs.php?tm=202301241200&stn=0&help=0&authKey=IcEupso9S1SBLqbKPUtUxw" // 실제 API 엔드포인트로 교체하세요
    val apiKey = "IcEupso9S1SBLqbKPUtUxw"       // 실제 API 키로 교체하세요

    val call = Call(apiUrl)

    call.fetchMarineData(apiKey) { data ->
        if (data != null) {
            val scanner = Scanner(System.`in`)

            print("location: ")
            val location = scanner.nextLine()
            println("API 데이터:")
            data.forEach { observation -> println(observation) }
            val filteredData = data.filter{ it.stnKo.contains(location) }
            if (filteredData.isEmpty()) {
                println("해당 지점의 데이터가 없습니다.")
            } else {
                println("\n${location}의 기상 데이터:")
                filteredData.forEach { println(it) }

                val takeoffPossible = filteredData.all { checkHelicopterTakeoffConditions(it) }

                if (takeoffPossible) {
                    println("\n헬기 이륙이 가능합니다.")
                } else {
                    println("\n헬기 이륙이 불가능합니다.")
                }
            }
        } else {
            println("데이터를 가져오지 못했습니다.")
        }
    }
}
