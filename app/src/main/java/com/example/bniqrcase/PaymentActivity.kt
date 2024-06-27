package com.example.bniqrcase

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bniqrcase.QRPay.Persentation.Util.Components.myTopAppbar
import com.example.bniqrcase.ui.theme.BNIDarkBlue
import com.example.bniqrcase.ui.theme.BNIQRCaseTheme
import java.io.IOException
import java.io.OutputStreamWriter

class PaymentActivity : ComponentActivity() {

    private var bankName = "";
    private var transactionId = "";
    private var nameMerchant = "";
    private var trxNominal = "";

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("qrdata").toString().split('.')
            bankName = value[0]
            transactionId = value[1]
            nameMerchant = value[2]
            trxNominal = value[3]
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BNIQRCaseTheme {
                Scaffold(
                    topBar = {
                        myTopAppbar(tittle = "Pembayaran")
                    },
                ){
                        innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .offset(x = 20.dp, y = 20.dp),
                        verticalArrangement = Arrangement.Top,

                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(text = "Merchant Name : $nameMerchant",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                        Text(text = transactionId,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal)
                        Text(text = "Nominal : $trxNominal",
                            modifier = Modifier.offset(x = 0.dp, y = 10.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold)
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(x = 0.dp, y = -40.dp)
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Bottom,

                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Button(onClick = {updateSaldo()},
                            shape = RoundedCornerShape(10),
                            colors = ButtonDefaults.buttonColors(BNIDarkBlue),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                            ) {
                            Text(text = "Bayar")
                        }
                    }
                }
            }
        }
    }

    private fun updateSaldo(){
        val sharedPreferences = getSharedPreferences("savedSaldo", Context.MODE_PRIVATE)
        val sharedPreferencesRiwayat = getSharedPreferences("riwayatTransaksi", Context.MODE_PRIVATE)
        val savedSaldo = sharedPreferences.getString("SALDO_ANDA", null)

        var newSaldo = (savedSaldo?.toInt() ?: 0) - trxNominal.toInt();

        val editor = sharedPreferences.edit()
        editor.apply{
            putString("SALDO_ANDA", newSaldo.toString())
        }.apply()

        writeToFile(this)

        val i: Intent = Intent(
            this@PaymentActivity,
            AckActivity::class.java
        )
        i.putExtra("newSaldo", newSaldo.toString())
        startActivity(i)
    }

    fun writeToFile(context: Context) {
        try {
            val outputStreamWriter = OutputStreamWriter(
                context.openFileOutput("QRAppsData.txt", MODE_PRIVATE)
            )

            var data = "\"Merchant\":\"$nameMerchant\",\"Nominal\":\"$trxNominal\"},";

            outputStreamWriter.write(data)
            outputStreamWriter.close()

            return
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: ${e.toString()}")
        }
    }


}
