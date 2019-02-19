package com.awolity.settingviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Toast
import com.awolity.settingsviews.RadiogroupSetting
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

        ss.setOnCheckedChangedListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this@MainActivity, "SwitchSetting switched: $isChecked", Toast.LENGTH_LONG).show() })

       rs.setListener(object: RadiogroupSetting.RadiogroupSettingListener{
            override fun OnRadioButtonClicked(selected: Int) {
                Toast.makeText(this@MainActivity, "RadioGroupSetting clicked. Selected radioButton: $selected", Toast.LENGTH_LONG).show()
            }
        })

        es.setSeekBarListener(object:SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Toast.makeText(this@MainActivity, "SeekbarSetting progress changed: $progress", Toast.LENGTH_LONG).show()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        btn_change_label.setOnClickListener {
            bs.setTitle(Constants.titles[Random.nextInt(Constants.titles.size)])
            ss.setTitle(Constants.titles[Random.nextInt(Constants.titles.size)])
            rs.setTitle(Constants.titles[Random.nextInt(Constants.titles.size)])
            es.setTitle(Constants.titles[Random.nextInt(Constants.titles.size)])
        }

        btn_change_description.setOnClickListener {
            bs.setDescription(Constants.descriptions[Random.nextInt(Constants.descriptions.size)])
            ss.setDescription(Constants.descriptions[Random.nextInt(Constants.descriptions.size)])
            rs.setDescription(Constants.descriptions[Random.nextInt(Constants.descriptions.size)])
            es.setDescription(Constants.descriptions[Random.nextInt(Constants.descriptions.size)])
        }

        sw_enabled.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            bs.isEnabled = sw_enabled.isChecked
            ss.isEnabled = sw_enabled.isChecked
            rs.isEnabled = sw_enabled.isChecked
            es.isEnabled = sw_enabled.isChecked
        }

        sw_checkable.setOnCheckedChangeListener { _: CompoundButton, _: Boolean ->
            bs.isCheckable = sw_checkable.isChecked
        }

        btn_change_icon.setOnClickListener{
            bs.setIcon(Constants.icons[Random.nextInt(Constants.icons.size)])
            ss.setIcon(Constants.icons[Random.nextInt(Constants.icons.size)])
            rs.setIcon(Constants.icons[Random.nextInt(Constants.icons.size)])
            es.setIcon(Constants.icons[Random.nextInt(Constants.icons.size)])
        }

        btn_change_title_color.setOnClickListener{
            bs.setTitleTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            ss.setTitleTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            rs.setTitleTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            es.setTitleTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
        }

        btn_change_description_color.setOnClickListener{
            bs.setDescriptionTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            ss.setDescriptionTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            rs.setDescriptionTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            es.setDescriptionTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
        }

        btn_change_disabled_color.setOnClickListener{
            bs.setDisabledTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            ss.setDisabledTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            rs.setDisabledTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
            es.setDisabledTextColor(resources.getColor(Constants.colors[Random.nextInt(Constants.colors.size)]))
        }
    }
}
