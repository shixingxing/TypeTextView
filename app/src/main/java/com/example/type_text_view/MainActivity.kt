package com.example.type_text_view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.type_text_view.databinding.ActivityMainBinding
import com.shixingxing.TypeTextView

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TEST_DATA =
            "题主我觉得你大概是被我们的同事三天两头就去问问题要材料搞烦了。。不过我们每年都来，一来就好几个月，在会议室里面天天熬到半夜这样的事情我们也是没有办法的啊！因为作为一个企业，无论是私企还是国企，公司的所有者和经营者一般都是分开的。所谓所有者，就是股东，而经营者就是企业各级管理人员。"
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.content.start.setOnClickListener {
            binding.content.text.mShowTextString = TEST_DATA
            binding.content.text.startTypeTimer()
        }

        binding.content.text.mOnTypeViewListener = object : TypeTextView.OnTypeViewListener {
            override fun onTypeOver() {
            }

            override fun onTypeStart() {
            }

        }
    }

}