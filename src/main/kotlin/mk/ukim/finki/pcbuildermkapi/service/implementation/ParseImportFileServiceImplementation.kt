package mk.ukim.finki.pcbuildermkapi.service.implementation

import com.google.gson.Gson
import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProduct
import mk.ukim.finki.pcbuildermkapi.domain.dto.ScrapedProductList
import mk.ukim.finki.pcbuildermkapi.service.ParseImportFileService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader


@Service
class ParseImportFileServiceImplementation : ParseImportFileService {
    override fun parseJsonFile(file: MultipartFile): Array<ScrapedProduct> {
        val br: BufferedReader
        val result = StringBuilder()
        try {
            var line: String?
            val `is`: InputStream = file.inputStream
            br = BufferedReader(InputStreamReader(`is`))
            while (br.readLine().also { line = it } != null) {
                result.append(line)
            }
        } catch (e: IOException) {
            System.err.println(e.message)
        }

        return Gson().fromJson(result.toString(), Array<ScrapedProduct>::class.java)
    }
}