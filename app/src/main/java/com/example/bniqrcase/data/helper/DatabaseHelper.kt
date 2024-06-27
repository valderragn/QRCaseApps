package com.example.bniqrcase.data.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.bniqrcase.data.model.HistoryModel

class MyDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "my_database.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        // Create your tables here (if needed)
        db.execSQL("CREATE TABLE IF NOT EXISTS history (id INTEGER PRIMARY KEY, merchant TEXT, nominal TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades (if needed)
    }

    fun insertHistoryDB(merchant: String, nominal: String) {
        val values = ContentValues().apply {
            put("merchant", merchant)
            put("nominal", nominal)
        }
        writableDatabase.insert("history", null, values)
    }

    fun getHistoryDB(): List<HistoryModel> {
        val historyList = mutableListOf<HistoryModel>()
        val cursor = readableDatabase.query("history", null, null, null, null, null, null)
        cursor.use {
            while (it.moveToNext()) {
                val merchant = it.getString(it.getColumnIndex("merchant"))
                val nominal = it.getString(it.getColumnIndex("nominal"))
                historyList.add(HistoryModel(merchant, nominal))
            }
        }
        return historyList
    }
}

fun insertHistory(context: Context ,merchant: String, nominal: String){
    val dbHelper = MyDatabaseHelper(context = context)
    dbHelper.insertHistoryDB(merchant, nominal)
}

fun getHistory(context: Context): List<HistoryModel>{
    val dbHelper = MyDatabaseHelper(context = context)
    var list =  dbHelper.getHistoryDB()
    return list
}