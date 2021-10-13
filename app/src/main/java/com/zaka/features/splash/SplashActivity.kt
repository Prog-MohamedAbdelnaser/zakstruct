package com.zaka.features.splash

import android.os.Bundle
import com.base.activity.BaseActivity
import com.zaka.databinding.ActivityMainBinding
import com.zaka.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}