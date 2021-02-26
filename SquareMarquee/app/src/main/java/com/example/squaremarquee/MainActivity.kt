package com.example.squaremarquee

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.squaremarquee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this,
            R.layout.activity_main
        )
        setContentView(binding.root)

        binding.icLogo.setOnClickListener{
            binding.haloView.visibility = when(binding.haloView.visibility) {
                View.VISIBLE -> View.INVISIBLE
                View.INVISIBLE -> View.VISIBLE
                else -> View.INVISIBLE
            }
        }
    }
}