package com.example.coroutines

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        GlobalScope.launch(Dispatchers.IO) {
//            val time = measureTimeMillis { // ıt is taking 6 seconds to finish, we dont have to wait that long.
//                val answer1 = networkCall1()
//                val answer2 = networkCall2()
//                Log.d(TAG,"Answer1 is $answer1")
//                Log.d(TAG,"Answer2 is $answer2")
//            }
            //  Log.d(TAG,"Requests took $time ms.")


            // Solution 1
            //It is taking 3 seconds. But bad practice
//            val time = measureTimeMillis {
//                var answer1: String? = null
//                var answer2: String? = null
//                val job1 = launch { answer1 = networkCall1() }
//                val job2 = launch { answer2 = networkCall2() }
//                job1.join()
//                job2.join()
//
//            }

            val time = measureTimeMillis {
                var answer1 = async { networkCall1() }
                var answer2 = async { networkCall2() }
                Log.d(TAG, "Answer1 is ${answer1.await()}")
                Log.d(TAG, "Answer2 is ${answer2.await()}")
            }

            Log.d(TAG, "Requests took $time ms.")

        }
    }

    suspend fun networkCall1(): String {
        delay(3000L)
        return "Answer 1"
    }

    suspend fun networkCall2(): String {
        delay(3000L)
        return "Answer 2"
    }
}