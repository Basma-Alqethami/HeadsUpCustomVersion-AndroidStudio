package com.example.headsupcustomversion

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class Game : AppCompatActivity() {

    lateinit var tvTimer: TextView
    lateinit var countDownTimer: CountDownTimer

    lateinit var tvName: TextView
    lateinit var tvTaboo1: TextView
    lateinit var tvTaboo2: TextView
    lateinit var tvTaboo3: TextView

    lateinit var LRotate : LinearLayout
    lateinit var Lgame : LinearLayout

    var list = ArrayList<CelebritiesItem>()
    var gameEnd = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        getAllData()

        LRotate = findViewById(R.id.LRotate)
        Lgame = findViewById(R.id.Lgame)
        tvTimer = findViewById(R.id.tvTimer)
        tvName = findViewById(R.id.tvName)
        tvTaboo1 = findViewById(R.id.tvTaboo1)
        tvTaboo2 = findViewById(R.id.tvTaboo2)
        tvTaboo3 = findViewById(R.id.tvTaboo3)

        LRotate.isVisible = true
        Lgame.isVisible = false

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            LRotate.isVisible = false
            Lgame.isVisible = true
            if(gameEnd == true) {
                startTimer()
                gameEnd = false
            }
            getGame()
            } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            LRotate.isVisible = true
            Lgame.isVisible = false
        }
    }

    fun getGame (){
        if (list.isNotEmpty()){
            var randomIndex = Random.nextInt(0, list.size)
            tvName.text = list[randomIndex].name
            tvTaboo1.text = list[randomIndex].taboo1
            tvTaboo2.text = list[randomIndex].taboo2
            tvTaboo3.text = list[randomIndex].taboo3
            list.removeAt(randomIndex)
        }
        else{
            Toast.makeText(this@Game,"GAME END", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@Game, MainActivity::class.java)
            startActivity(intent)
            }
    }
    // start timer
    private fun startTimer() {
        countDownTimer = object : CountDownTimer(60000,1000){
            //            end of timer
            override fun onFinish() {
                Toast.makeText(this@Game,"TIME END", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@Game, MainActivity::class.java)
                startActivity(intent)
            }

            override fun onTick(millisUntilFinished: Long) {
                var timer = millisUntilFinished
                var s = (timer / 1000) % 60
                var format = String.format("%02d", s)
                if(format == "04") {tvTimer.setTextColor(resources.getColor(R.color.red))}
                tvTimer.setText("Time: " +format)
            }

        }.start()
    }



    fun getAllData() {

        val api = Client().getClient()?.create(API::class.java)

        api?.getData()?.enqueue(object : Callback<Celebrities> {
            override fun onResponse(
                call: Call<Celebrities>,
                response: Response<Celebrities>
            ) {
                for (item in response.body()!!) {
                    Log.d("item", "$item")
                    val pk = item.pk
                    val name = item.name
                    val taboo1 = item.taboo1
                    val taboo2 = item.taboo2
                    val taboo3 = item.taboo3
                    list.add(CelebritiesItem(pk,name,taboo1,taboo2,taboo3))
                }
            }
            override fun onFailure(call: Call<Celebrities>, t: Throwable) {
                Log.d("error", "$t")
            }
        })
    }
}



