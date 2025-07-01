package com.example.engdictionaryapp.trainer.data

import android.util.JsonReader
import com.example.engdictionaryapp.models.Word
import java.io.InputStream
import java.io.InputStreamReader

object Dictionary {
    var words = listOf<Word>()

    fun fillWords(inputStream: InputStream) {
        val reader = JsonReader(InputStreamReader(inputStream, "UTF-8"))

        reader.use {
            words = readWordArray(it)
        }
    }

    private fun readWordArray(reader: JsonReader): List<Word> {
        val words = mutableListOf<Word>()

        reader.beginArray()
        while (reader.hasNext())
            words.add(readWord(reader))
        reader.endArray()
        return words
    }

    private fun readWord(reader: JsonReader): Word {
        var wordOriginal = ""
        var wordTranslate = ""

        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            when (name) {
                "original" -> wordOriginal = reader.nextString()
                "translate" -> wordTranslate = reader.nextString()
                else -> reader.skipValue()
            }
        }
        reader.endObject()
        return Word(wordOriginal, wordTranslate)
    }

}