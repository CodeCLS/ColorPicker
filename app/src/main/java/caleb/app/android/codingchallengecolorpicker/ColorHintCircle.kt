package caleb.app.android.codingchallengecolorpicker

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ColorHintCircle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {init{
    init()
}

    private var color: Int = Color.WHITE
    private lateinit var paintBorder :Paint

    private fun init() {
        paintBorder = Paint()
        paintBorder.color = Color.WHITE
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(width/2.toFloat(),width/2.toFloat(),width/2.toFloat(), paintBorder)
        paintBorder.setColor(color as Int)
        canvas?.drawCircle(width/2.toFloat(),width/2.toFloat(),width/2.toFloat()-10, paintBorder)
        paintBorder.setColor(Color.WHITE)

    }
    public fun setColor(color:Int):Unit{
        this.color = color
        postInvalidate()
    }
}