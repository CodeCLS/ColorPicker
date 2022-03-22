package caleb.app.android.codingchallengecolorpicker

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat


class SegmentedColorControl :FrameLayout{

    private var index: Int = 0
    private lateinit var listener: ControlFeedback
    private var currentSelectedView: ConstraintLayout? = null
    private var clickedColor: Int = Color.WHITE

    private lateinit var tealParentBtn : ConstraintLayout
    private lateinit var greenParentBtn : ConstraintLayout
    private lateinit var orangeParentBtn : ConstraintLayout


    private lateinit var img1 : ImageView
    private lateinit var img2 : ImageView
    private lateinit var img3 : ImageView

    private lateinit var colorOptions: ArrayList<Int>

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
            arrayListOf(
                ContextCompat.getColor(context,R.color.teal),
                ContextCompat.getColor(context,R.color.green) ,
                ContextCompat.getColor(context,R.color.orange)
            )
    }

    private fun setupViews() {
        tealParentBtn.setOnClickListener {
            index = 0
            setColor( colorOptions[0])

            if (listener != null)
                listener.OnColorChange(clickedColor)
        }
        greenParentBtn.setOnClickListener {
            index = 1
            setColor( colorOptions[1])
            if (listener != null)
                listener.OnColorChange(clickedColor)
        }
        orangeParentBtn.setOnClickListener {
            index = 2
            setColor(colorOptions[2])
            if (listener != null)
                listener.OnColorChange(clickedColor)
        }
    }

    private fun initViews() {
        tealParentBtn = findViewById(R.id.three_way_option_teal_parent)
        greenParentBtn = findViewById(R.id.three_way_option_green_parent)
        orangeParentBtn = findViewById(R.id.three_way_option_orange_parent)

        img1 = findViewById(R.id.img_index_one)
        img2 = findViewById(R.id.img_index_two)
        img3 = findViewById(R.id.img_index_three)
    }



    private fun updateLayoutSelectedColor(p0: View?) {
        if (currentSelectedView != null) {
            currentSelectedView!!.setBackgroundColor(resources.getColor(R.color.background_unselected,null))
        }
        p0?.setBackgroundColor(resources.getColor(R.color.background_selected,null))
        currentSelectedView = p0 as ConstraintLayout



    }
    public fun setListener(listener:ControlFeedback) {
        this.listener = listener
    }

    fun setColor(color: Int) {
        if (index == 0) {
            img1.imageTintList = ColorStateList.valueOf(color)
            updateLayoutSelectedColor(tealParentBtn)

        }
        else if(index == 1){
            img2.imageTintList = ColorStateList.valueOf(color)
            updateLayoutSelectedColor(greenParentBtn)

        }
        else if(index == 2){
            img3.imageTintList = ColorStateList.valueOf(color)
            updateLayoutSelectedColor(orangeParentBtn)

        }
        clickedColor = color
        colorOptions[index]= color





    }


    public interface ControlFeedback{
        fun OnColorChange(color: Int)
    }

}