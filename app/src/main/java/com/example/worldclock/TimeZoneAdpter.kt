package com.example.worldclock

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextClock
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.view.*
import java.util.*

class TimeZoneAdpter(private val context: Context,
                     private val timeZones: Array <String> =
                        TimeZone.getAvailableIDs()) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)
    

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //convertViewがある場合はそれを使い、ない場合は新しく作る
        val view = convertView ?: createView(parent)

        //positionから表示すべきタイムゾーンのIDを取得
        val timeZoneId = getItem(position)
        //タイムゾーンのIDからタイムゾーン取得
        val timeZone = TimeZone.getTimeZone(timeZoneId)

        //tagからviewHolderを取得
        val viewHolder = view.tag as ViewHolder

        //タイムゾーン名をセット
        @SuppressLint("SetTextI18n")
        viewHolder.name.text = "${timeZone.displayName}(${timeZone.id})"

        //タイムゾーンをセット
        viewHolder.clock.timeZone = timeZone.id

        return view
    }

    override fun getItem(position: Int): String {
        return timeZones[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return timeZones.size
    }

    private fun createView(parent: ViewGroup?): View{
        val view = inflater.inflate(R.layout.list_time_zone_row, parent, false)
        view.tag= ViewHolder(view)
        return view
    }

    //一回取得したviewの保存用
    private  class ViewHolder(view: View){
        val name = view.findViewById<TextView>(R.id.timeZone)
        val clock  = view.findViewById<TextClock>(R.id.clock)
    }
}