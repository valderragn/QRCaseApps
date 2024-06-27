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
import com.example.bniqrcase.QRPay.Persentation.Util.Components.ListHistory
import com.example.bniqrcase.QRPay.Persentation.Util.Components.myTopAppbar
import com.example.bniqrcase.data.helper.getHistory
import com.example.bniqrcase.data.model.HistoryModel
import com.example.bniqrcase.ui.theme.BNIQRCaseTheme
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.math.log

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var listHistory = getHistory(this)
        enableEdgeToEdge()
        setContent {
            BNIQRCaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    myTopAppbar(tittle = "Riwayat")
                    ListHistory(listHistory)
                }
            }
        }
    }
}