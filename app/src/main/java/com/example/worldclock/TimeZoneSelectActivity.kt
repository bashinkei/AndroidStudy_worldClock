package com.example.worldclock

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class TimeZoneSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  タイムゾーン選択画面のレイアウトを指定する
        setContentView(R.layout.activity_time_zone_select)

        //最初に「キャンセルされた」結果を返すように設定しておくと
        //戻るボタンをタップした時などに対応できる
        setResult(Activity.RESULT_CANCELED)

        //たいっむゾーンリストをレイアウトから取得
        val list = findViewById<ListView>(R.id.clockList)
        val adpter = TimeZoneAdpter(this)

        //listにアダプターセット
        list.adapter = adpter

        //リストのタップ時の動作
        list.setOnItemClickListener { _, _, position, _ ->
            //タップした場所のタイムゾーンをリストから取得
            val timeZone = adpter.getItem(position)

            //遷移もとに返す結果
            val result = Intent()
            result.putExtra("timeZone", timeZone)

            //　「OK」の結果返却
            setResult(Activity.RESULT_OK, result)

            //この画面を閉じる
            finish()


        }

    }
}
