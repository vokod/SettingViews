package com.awolity.settingviews

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }

    companion object {
        var layout: Int = 0
    }
}