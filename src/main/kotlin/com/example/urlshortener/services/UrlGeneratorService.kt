package com.example.urlshortener.services

import com.example.urlshortener.model.Range
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class UrlGeneratorService {

    val charCount = 62
    val arr = charArrayOf(
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9'
    )

    @Autowired
    private var range: Range? = null

    @Autowired
    private val srs: SharedRangeService? = null

    @Value("#{range.start}")
    private var pointer: Long = 0

    @Synchronized
    private fun resetRange() {
        range = srs!!.getUniqueRange()
        pointer = range!!.start
    }

    @Synchronized
    fun getNewURL(original: String?): String? {
        if (compareValues(pointer, range!!.end as Comparable<*>) > 0) resetRange()
        var count = pointer
        val sNewUrl = StringBuilder()
        while (count > 0) {
            sNewUrl.append(arr[count.mod(charCount).toInt()])
            count /= charCount
        }
        while (sNewUrl.length < 7) sNewUrl.append('a')
        pointer++
        return sNewUrl.reverse().toString()
    }

    companion object {
        val charCount = null
        val arr = charArrayOf()
    }

}




