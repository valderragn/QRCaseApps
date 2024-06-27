package com.example.bniqrcase

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bniqrcase.data.model.HistoryModel
import com.example.bniqrcase.ui.theme.BNIQRCaseTheme
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var listHistory = getHistory(this)
        enableEdgeToEdge()
        setContent {
            BNIQRCaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun getHistory(context: Context): List<HistoryModel> {
            try {
                val inputStream = context.openFileInput("QRAppsData.txt")
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val stringBuilder = StringBuilder()
                var line: String?
                while (bufferedReader.readLine().also { line = it } != null) {
                    stringBuilder.append(line)
                }
                bufferedReader.close()

                val json = stringBuilder.toString().split(',')
                val historyList = mutableListOf<HistoryModel>()
                json.forEach { item ->
                    // Parse the JSON element into a HistoryModel
                    val jsonObject = JSONObject(item) // Assuming item is a valid JSON string
                    val merchantName = jsonObject.optString("merchantName")
                    val trxNominal = jsonObject.optString("trxNominal")

                    // Create a new HistoryModel
                    val newModel = HistoryModel(merchantName, trxNominal)

                    // Add it to the list
                    historyList.add(newModel)
                }
                return historyList

            } catch (e: IOException) {
                Log.e("Exception", "File read failed: ${e.toString()}")
                return emptyList<HistoryModel>();
            }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    BNIQRCaseTheme {
        Greeting("Android")
    }
}