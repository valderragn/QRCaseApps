package com.example.bniqrcase.QRPay.Persentation.Util.Components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bniqrcase.ui.theme.BNILightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myTopAppbar(
    tittle : String
){
    CenterAlignedTopAppBar(
        title = { Text(text = tittle, maxLines = 1, overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Bold)},
        modifier = Modifier.shadow(elevation = 5.dp),
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = BNILightBlue)
    )
}