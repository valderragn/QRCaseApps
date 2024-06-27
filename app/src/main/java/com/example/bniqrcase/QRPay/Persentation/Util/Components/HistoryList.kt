package com.example.bniqrcase.QRPay.Persentation.Util.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import com.example.bniqrcase.data.model.HistoryModel

@Composable
fun ListHistory(historyList: List<HistoryModel>){
    Box(modifier = Modifier.fillMaxSize().offset(x = 0.dp, y = 100.dp)){
        LazyColumn(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            items(historyList) { history ->
                HistoryItem(item = history)
            }
        }
    }
}

@Composable
private fun HistoryItem(item: HistoryModel){
    Box(modifier = Modifier.fillMaxWidth()){
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = item.merchantName)
            Text(text = item.trxNominal)
        }

    }
}