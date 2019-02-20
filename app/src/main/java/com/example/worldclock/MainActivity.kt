package com.example.worldclock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //画面のレイアウトを指定
        setContentView(R.layout.activity_main)

        // ユーザーのデフォルトタイムゾーンを追加する
        val timeZone = TimeZone.getDefault()

        // タイムゾーン名を表示するTextView
        val timeZoneView = findViewById<TextView>(R.id.timeZone)
        //タイムゾーン名を表示
        timeZoneView.text = timeZone.displayName

        //「追加する」ボタン
        val addButton = findViewById<Button>(R.id.add)

        addButton.setOnClickListener {
            val intent = Intent(this, TimeZoneSelectActivity::class.java)
            //遷移先の結果を受け取りたい場合
            startActivityForResult(intent, Companion.TIME_ZONE_SELECT)
        }

        // 世界時計のリストを表示する
        showWorldClocks()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == TIME_ZONE_SELECT
            && resultCode == Activity.RESULT_OK
            && data != null
        ) {
            //選択したタイムゾーンの取得
            val tiemZone = data.getStringExtra("timeZone")

            //プリファレンスから保存しているタイムゾーンを取得する
            val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val timeZones = pref.getStringSet("time_zone", mutableSetOf()).toMutableSet()

            //選択を追加
            timeZones.add(tiemZone)

            //プリファレンスへ保存
            pref.edit().putStringSet("time_zone", timeZones).apply()

            //リストを再表示
            showWorldClocks()

        }
    }

    private fun showWorldClocks() {
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val timeZones = pref.getStringSet("time_zone", setOf())

        val list = findViewById<ListView>(R.id.clockList)
        list.adapter = TimeZoneAdpter(this, timeZones.toTypedArray())

        //listを選択したときに削除するように
        list.setOnItemClickListener { _, _, position, _ ->
            val listId = list.adapter.getItem(position)

            //プリファレンス取得
            val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val timeZones = pref.getStringSet("time_zone", mutableSetOf()).toMutableSet()

            //プリファレンスから削除
            timeZones.remove(listId)

            //プリファレンスに保存
            pref.edit().putStringSet("time_zone", timeZones).apply()

            //Toastで表示
            Toast.makeText(this, "削除しました♪", Toast.LENGTH_SHORT).show()

            //listの再表示
            showWorldClocks()

        }
    }

    companion object {
        private const val TIME_ZONE_SELECT = 1
    }
}
