package com.example.infyassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView


class CustomAdapter(context: Context,arrayListDetails:ArrayList<Model>) : BaseAdapter(){

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    private val arrayListDetails:ArrayList<Model> = arrayListDetails

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: ListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.adapter_layout, parent, false)
            listRowHolder = ListRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ListRowHolder
        }

        listRowHolder.tvDesc.text = arrayListDetails.get(position).desc
        //listRowHolder.tvImg.text = arrayListDetails.get(position).img
        listRowHolder.tvTitle.text = arrayListDetails.get(position).title
        return view
    }
}

private class ListRowHolder(row: View?) {
    public val tvDesc: TextView = row?.findViewById<TextView>(R.id.tvDesc) as TextView
    public val tvImg: ImageView = row?.findViewById<ImageView>(R.id.tvImg) as ImageView
    public val tvTitle: TextView = row?.findViewById<TextView>(R.id.tvTitle) as TextView
    public val linearLayout: LinearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout

}