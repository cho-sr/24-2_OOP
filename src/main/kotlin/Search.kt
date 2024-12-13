class Search(private val data: List<MarineObservation>) {

    // 결측치 전처리
    fun preprocessData(): List<MarineObservation> {
        return data.filter {
            it.wh != -99.0 && it.ws != -99.0 && it.tw != -99.0
        }
    }

    // 조건에 맞는 데이터 필터링
    fun filterData(
        minTemperature: Double = Double.NEGATIVE_INFINITY,
        maxWaveHeight: Double = Double.POSITIVE_INFINITY,
        location: String? = null
    ): List<MarineObservation> {
        return preprocessData().filter {
            it.tw >= minTemperature &&
                    it.wh <= maxWaveHeight &&
                    (location == null || it.stnKo == location)
        }
    }
}
