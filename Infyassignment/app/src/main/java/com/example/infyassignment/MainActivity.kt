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
    lateinit var lvd: ListView
    var arrayLd:ArrayList<Model> = ArrayList();
    //OkHttpClient creates connection pool between client and server
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress = findViewById(R.id.progressBar)
        progress.visibility = View.VISIBLE
        lvd = findViewById<ListView>(R.id.listView) as ListView
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
                var jresponse = response.body()!!.string()
                println("before comment /creating json object/")
                //creating json object
                val jcontact:JSONObject = JSONObject(jresponse)
                println("Created JSON object $jcontact")
                //creating json array
                var jsonarray:JSONArray= jcontact.getJSONArray("rows")
                var i:Int = 0
                var size:Int = jsonarray.length()
                arrayLd= ArrayList();
                for (i in 0 until size) {
                    var jsonobject:JSONObject=jsonarray.getJSONObject(i)
                    var model:Model= Model()
                    model.title=jsonobject.getString("title")
                    model.desc=jsonobject.getString("description")
                    model.img=jsonobject.getString("imageHref")
                    arrayLd.add(model)
                }

                runOnUiThread {
                    //stuff that updates ui
                    val adapterobj : CustomAdapter = CustomAdapter(applicationContext,arrayLd)
                    lvd.adapter=adapterobj
                }
                progress.visibility = View.GONE
            }
        })
    }
}