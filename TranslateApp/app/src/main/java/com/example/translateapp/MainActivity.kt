package com.example.translateapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.translateapp.ui.theme.TranslateAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        GlobalScope.launch (Dispatchers.IO) {
//            getTranslation("Hello there", "ru")
//        }

        setContent {
            TranslateAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

fun getTranslation(text: String, lang: String) {
    val API_URL = "https://api.cognitive.microsofttranslator.com/translate?api-version=3.0&"
    val key = "077d6c8e53fd4c07ab8dfa4a1c15a618" // записать ключ в переменную
    val POSTData = "[{'Text':'Мама, можно сегодня в школу не ходить?'}]"
    // TODO: 2) создать класс, в котором будете хранить текст для перевода

    val url = URL(API_URL + "to=$lang")
    val conn = url.openConnection() as HttpURLConnection
    conn.apply {
        setRequestProperty("Content-Type", "application/json")
        setRequestProperty("Ocp-Apim-Subscription-Key", "891c1008d9a84e9c90fbe6e59fd1d62b")
        setRequestProperty("Ocp-Apim-Subscription-Region", "westeurope")

        doOutput = true // setting POST method
        val outStream: OutputStream = outputStream
        outStream.write(POSTData.toByteArray())
        val sc = Scanner(conn.inputStream)

        while (sc.hasNextLine()) {
            Log.d("mytag", sc.nextLine())
        }

        disconnect()
    } // finished connection
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    TranslateAppTheme {
//        Greeting("Android")
//    }
//}