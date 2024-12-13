import okhttp3.*
import java.io.IOException

class Call(private val apiUrl: String) {
    private val client = OkHttpClient()

    fun fetchMarineData(apiKey: String, callback: (List<MarineObservation>?) -> Unit) {
        val url = "$apiUrl&serviceKey=$apiKey"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                println("API 호출 실패: ${e.message}")
                callback(null)
            }

            override fun onResponse(call: okhttp3.Call, response:Response) {
                if (!response.isSuccessful) {
                    println("API 호출 실패: ${response.code}")
                    callback(null)
                } else {
                    response.body?.string()?.let { csv ->
                        val data = parseCsv(csv)
                        callback(data)
                    } ?: callback(null)
                }
            }
        })
    }

    private fun parseCsv(csv: String): List<MarineObservation> {
        return csv.lines()
            .filter { it.isNotBlank() && it.contains(",") }
            .mapNotNull { line ->
                val parts = line.split(",").map { it.trim() }
                try {
                    MarineObservation(
                        tp = parts[0],
                        tm = parts[1],
                        stnId = parts[2],
                        stnKo = parts[3],
                        lon = parts[4].toDouble(),
                        lat = parts[5].toDouble(),
                        wh = parts[6].toDoubleOrNull() ?: -99.0,
                        wd = parts[7].toIntOrNull() ?: -99,
                        ws = parts[8].toDoubleOrNull() ?: -99.0,
                        wsGst = parts[9].toDoubleOrNull() ?: -99.0,
                        tw = parts[10].toDoubleOrNull() ?: -99.0,
                        ta = parts[11].toDoubleOrNull() ?: -99.0,
                        pa = parts[12].toDoubleOrNull() ?: -99.0,
                        hm = parts[13].toDoubleOrNull() ?: -99.0
                    )
                } catch (e: Exception) {
                    null
                }
            }
    }
}
fun checkHelicopterTakeoffConditions(data: MarineObservation): Boolean {
    return data.ws <= 10.0 &&       // 풍속 10m/s 이하
            data.wh <= 2.0 &&        // 유의파고 2m 이하
            data.ta >= -10.0         // 기온 -10°C 이상
}