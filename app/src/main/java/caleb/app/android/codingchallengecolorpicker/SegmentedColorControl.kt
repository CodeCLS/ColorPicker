package caleb.app.android.codingchallengecolorpicker

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat


class SegmentedColorControl :FrameLayout{

    private lateinit var listener: ControlFeedback
    private var currentSelectedView: ConstraintLayout? = null
    private var clickedColor: Int = Color.WHITE

    private lateinit var tealParentBtn : ConstraintLayout
    private lateinit var greenParentBtn : ConstraintLayout
    private lateinit var orangeParentBtn : ConstraintLayout

    private lateinit var colorOptions: List<Int>

    constructor(context: Context) : super(context){
        init();
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init();
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.layout_segmented_color_control,this)
        initVars();
        initViews()
        setupViews()


    }

    private fun initVars() {
        colorOptions =
            listOfNotNull(
                ContextCompat.getColor(context,R.color.teal),
                ContextCompat.getColor(context,R.color.green) ,
                ContextCompat.getColor(context,R.color.orange)
            )
    }

    private fun setupViews() {
        tealParentBtn.setOnClickListener {
            clickedColor = colorOptions[0]
            updateLayoutSelectedColor(tealParentBtn)

        }
        greenParentBtn.setOnClickListener {
            clickedColor = colorOptions[1]
            updateLayoutSelectedColor(greenParentBtn)
        }
        orangeParentBtn.setOnClickListener {
            clickedColor = colorOptions[2]
            updateLayoutSelectedColor(orangeParentBtn)
        }
    }

    private fun initViews() {
        tealParentBtn = findViewById(R.id.three_way_option_teal_parent)
        greenParentBtn = findViewById(R.id.three_way_option_green_parent)
        orangeParentBtn = findViewById(R.id.three_way_option_orange_parent)
    }



    private fun updateLayoutSelectedColor(p0: View?) {
        if (currentSelectedView != null) {
            currentSelectedView!!.setBackgroundColor(resources.getColor(R.color.background_unselected,null))
        }
        p0?.setBackgroundColor(resources.getColor(R.color.background_selected,null))
        currentSelectedView = p0 as ConstraintLayout
        if (listener != null)
            listener.OnColorChange(clickedColor)


    }
    public fun setListener(listener:ControlFeedback) {
        this.listener = listener
    }


    public interface ControlFeedback{
        fun OnColorChange(color: Int)

    }

}