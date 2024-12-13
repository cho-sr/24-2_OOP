class Search(private val data: List<MarineObservation>) {

    // 결측치 전처리

    fun removeInvalidData(): List<MarineObservation> {
        return data.filter { observation ->
            observation.wh != -99.0 &&
                    observation.ws != -99.0 &&
                    observation.tw != -99.0 &&
                    observation.ta != -99.0
        }
    }
    fun filterByLocation(location: String): List<MarineObservation> {
        val validData = removeInvalidData()
        return validData.filter { it.stnKo.contains(location) }
    }
    // 조건에 맞는 데이터 필터링
    fun filterForHelicopterTakeoff(location: String): List<MarineObservation> {
        val locationFilteredData = filterByLocation(location)
        return locationFilteredData.filter { observation ->
            observation.ws <= 10.0 &&    // 풍속 10m/s 이하
                    observation.wh <= 5.0 &&     // 유의파고 2m 이하
                    observation.ta >= -10.0      // 기온 -10°C 이상
        }
    }
}

