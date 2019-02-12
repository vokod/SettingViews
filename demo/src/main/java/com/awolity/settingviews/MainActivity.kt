package com.awolity.settingviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bs.setOnClickListener {
            Toast.makeText(this@MainActivity, "ButtonSetting clicked", Toast.LENGTH_LONG).show()
            if (bs.isCheckable) {
                if (bs.checked) {
                    bs.uncheck()
                } else {
                    bs.check()
                }
            }
        }

        btn_change_label.setOnClickListener {
            bs.setLabel(Constants.labels[Random.nextInt(Constants.labels.size)])
        }

        btn_change_description.setOnClickListener {
            bs.setDescription(Constants.descriptions[Random.nextInt(Constants.descriptions.size)])
        }

        sw_enabled.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            bs.isEnabled = sw_enabled.isChecked
        }

        sw_checkable.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            bs.isCheckable = sw_checkable.isChecked
        }

        btn_change_icon.setOnClickListener{
            bs.setIcon(Constants.icons[Random.nextInt(Constants.icons.size)])
        }

        btn_change_title_color.setOnClickListener{
            bs.setLabelColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
        }

        btn_change_description_color.setOnClickListener{
            bs.setDescriptionColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
        }

        btn_change_disabled_color.setOnClickListener{
            bs.setDisabledColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
        }

        btn_change_background_color.setOnClickListener{
            bs.setBackgroundColor( resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
        }
    }
}
