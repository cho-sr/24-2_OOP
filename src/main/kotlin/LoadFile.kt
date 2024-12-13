import java.io.File

fun saveDataToFile(data: String, fileName: String, format: String) {
    val file = File("$fileName.$format")
    file.writeText(data)
    println("파일 저장 완료: ${file.absolutePath}")
}
