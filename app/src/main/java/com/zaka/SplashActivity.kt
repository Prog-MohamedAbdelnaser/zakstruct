package com.zaka

import android.os.Bundle
import com.base.activity.BaseActivity
import com.zaka.databinding.ActivityMainBinding

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}