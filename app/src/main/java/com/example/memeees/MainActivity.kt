package com.example.memeees

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    val url ="https://jsonplaceholder.typicode.com/posts"
    var currentmemeurl : String?= null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme()
    }

    fun loadmeme(){
        progressbar.visibility = View.VISIBLE
        val queue= Volley.newRequestQueue(this)
        val url ="https://meme-api.herokuapp.com/gimme"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val currentmemeurl = response.getString("url")
                Glide.with(this).load(currentmemeurl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressbar.visibility = View.GONE
                        return false
                    }
                }
                ).into(memeImageView)
            },
            { error ->
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        )
/*
val reques= StringRequest(Request.Method.GET, url,
Response.Listener { response ->
Log.e("Error", response.toString())

},Response.ErrorListener{ })
*/
    queue.add(jsonObjectRequest)
    }

    fun nextmeme(view: View) {
        loadmeme()
    }
    fun sharememe(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey checkout this cool meme, i got from Reddit $currentmemeurl")
        val chooser = Intent.createChooser(intent, "Share this meme using....")
        startActivity(chooser)
    }
}