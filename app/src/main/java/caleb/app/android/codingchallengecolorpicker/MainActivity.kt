package caleb.app.android.codingchallengecolorpicker

import android.app.WallpaperManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.lang.Math.toDegrees
import kotlin.math.atan2
import kotlin.math.hypot


private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var wheel : ColorWheel
    private lateinit var control : SegmentedColorControl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wheel = findViewById(R.id.wheel)
        control = findViewById(R.id.segment_view_colors)
        control.setListener( object : SegmentedColorControl.ControlFeedback {
            override fun OnColorChange(color: Int) {
                wheel.setColor(color)
            }
        })
        wheel.setListener( object : SegmentedColorControl.ControlFeedback {
            override fun OnColorChange(color: Int) {
                control.setColor(color)
            }
        })

    }

    override fun onStart() {
        super.onStart()



    }


}