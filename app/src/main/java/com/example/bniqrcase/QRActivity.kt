package com.example.bniqrcase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.bniqrcase.ui.theme.BNIBaseWhite
import com.example.bniqrcase.ui.theme.BNILightBlue
import com.example.bniqrcase.ui.theme.BNIOrange
import com.example.bniqrcase.ui.theme.BNIQRCaseTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class QRActivity : ComponentActivity() {

    private var textResult = mutableStateOf("");

    private val barCodeLauncher = registerForActivityResult(ScanContract()) {
        result ->
        if(result.contents == null) {
            Toast.makeText(this@QRActivity, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            val i: Intent = Intent(
                this@QRActivity,
                PaymentActivity::class.java
            )
            i.putExtra("qrdata", result.contents)
            startActivity(i)
        }
    }

    private fun showCamera(){
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan a QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        barCodeLauncher.launch(options)
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission())
        {
            isGranted ->
            if(isGranted){
                showCamera()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        DetermineSaldo();
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BNIQRCaseTheme {
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier.fillMaxWidth(),
                            actions = {},
                            containerColor = BNILightBlue,

                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            modifier = Modifier.offset(x = 0.dp, y = 60.dp) ,
                            shape = CircleShape,
                            containerColor = BNIBaseWhite,
                            onClick = { checkCameraPermission(this@QRActivity)}) {
                            Icon(
                                painter = painterResource(id = R.drawable.qr_scan),
                                contentDescription = "QR Scan",
                                tint = BNIOrange,
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(20.dp)
                            )
                        }
                    },

                    floatingActionButtonPosition = FabPosition.Center

                ) { innerPadding ->
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .offset(x = 30.dp, y = 20.dp)
                                .padding(innerPadding),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(text = "Saldo Anda : ",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Normal)
                            Text(text = textResult.value,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold)
                        }
                        Row (
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.offset(x = 30.dp, y = 0.dp)
                        ) {
                            Button(onClick = {gotoHistory()}) {
                                Icon(painter = painterResource(id = R.drawable.riwayat_ogo), contentDescription ="Riwayat")

                            }
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset(x = 30.dp, y = 20.dp)
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(text = "Saldo Anda : ",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Normal)
                        Text(text = textResult.value,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun gotoHistory() {
        val i: Intent = Intent(
            this@QRActivity,
            HistoryActivity::class.java
        )
            startActivity(i)
    }

    private fun DetermineSaldo() {
        val sharedPreferences = getSharedPreferences("savedSaldo", Context.MODE_PRIVATE)
        val savedSaldo = sharedPreferences.getString("SALDO_ANDA", null)

        if(savedSaldo.equals(null)){
            val editor = sharedPreferences.edit()
            editor.apply{
                putString("SALDO_ANDA", "500000")
            }.apply()
        }

        textResult.value = sharedPreferences.getString("SALDO_ANDA", null).toString()
    }

    private fun checkCameraPermission(context: Context) {

        if(ContextCompat.checkSelfPermission(
                context,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED){
            showCamera()
        } else if(shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            Toast.makeText(this@QRActivity, "Camera Required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionsLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
}
