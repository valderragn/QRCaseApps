package com.example.bniqrcase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.bniqrcase.ui.theme.BNIDarkBlue
import com.example.bniqrcase.ui.theme.BNIQRCaseTheme

class AckActivity : ComponentActivity() {
    var newSaldo:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        val extras = intent.extras
        if (extras != null) {
            newSaldo = extras.getString("newSaldo").toString()

        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BNIQRCaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 0.dp, vertical = 20.dp)
                                .offset(x = 0.dp, y = 50.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Column {
                                Text(
                                    text = "Pembayaran Berhasil",
                                    fontSize = 25.sp
                                )
                                Column(
                                    modifier = Modifier.offset(x = 0.dp, y = 20.dp),
                                ) {
                                    Text(
                                        text = "SaldoAnda",
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = newSaldo,
                                        fontSize = 25.sp
                                    )
                                }

                            }

                        }
                        Button(onClick = {homeBack()},
                            shape = RoundedCornerShape(10),
                            colors = ButtonDefaults.buttonColors(BNIDarkBlue),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Home")
                        }
                    }
                }
            }
        }
    }


    private fun homeBack(){
        val i: Intent = Intent(
            this@AckActivity,
            QRActivity::class.java
        )
        startActivity(i)
    }
}
