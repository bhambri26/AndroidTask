package com.example.infyassignment

import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var progress:ProgressBar
    lateinit var listView_details: ListView
    var arrayList_details:ArrayList<Model> = ArrayList();
    //OkHttpClient creates connection pool between client and server
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress = findViewById(R.id.progressBar)
        progress.visibility = View.VISIBLE
        listView_details = findViewById<ListView>(R.id.listView) as ListView
        run("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json")
    }

    fun run(url: String) {
        println("In run for API")
        progress.visibility = View.VISIBLE
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("in onFailure method ")
                progress.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                println("in onResponse")
                var str_response = response.body()!!.string()
                println("before comment /creating json object/")
                //creating json object
                val json_contact:JSONObject = JSONObject(str_response)
                println("Created JSON object $json_contact")
                //creating json array
                var jsonarray_info:JSONArray= json_contact.getJSONArray("rows")
                var i:Int = 0
                var size:Int = jsonarray_info.length()
                arrayList_details= ArrayList();
                for (i in 0.. size-1) {
                    var json_objectdetail:JSONObject=jsonarray_info.getJSONObject(i)
                    var model:Model= Model();
                    model.title=json_objectdetail.getString("title")
                    model.desc=json_objectdetail.getString("description")
                    model.img=json_objectdetail.getString("imageHref")
                    arrayList_details.add(model)
                }

                runOnUiThread {
                    //stuff that updates ui
                    val obj_adapter : CustomAdapter = CustomAdapter(applicationContext,arrayList_details)
                    listView_details.adapter=obj_adapter
                }
                progress.visibility = View.GONE
            }
        })
    }
}