package caleb.app.android.codingchallengecolorpicker

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.math.*

private const val TAG = "ColorWheel"
class ColorWheel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){


    private var margin: Int = 0
    private var radius: Float = 0f
    private var posXMarker: Float = 500.toFloat()
    private var posYMarker: Float = 500.toFloat()

    private var paint :Paint = Paint()
    private lateinit var sweepShader:Shader
    private var paintOverlay :Paint = Paint()

    private var colorMarker: Int = Color.WHITE
    private var paintBorder :Paint = Paint()

    private fun init() {
        paint = Paint()
        paint.isAntiAlias = true
        paintOverlay = Paint()
        paintOverlay.isAntiAlias = true
        val mColors = intArrayOf(
            Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN,
            Color.BLUE, Color.MAGENTA, Color.RED
        )
        sweepShader = SweepGradient(radius,radius,mColors,floatArrayOf(
            0.000f, 0.166f, 0.333f, 0.499f,
            0.666f, 0.833f, 0.999f))
        paint.shader = (sweepShader)

        val satShader: Shader = RadialGradient(
            radius,radius, radius,
            Color.WHITE, 0x00FFFFFF,
            Shader.TileMode.CLAMP
        )
        paintOverlay.shader = (satShader)

        paintBorder = Paint()
        paintBorder.isAntiAlias = true
        paintBorder.color = Color.WHITE


        postInvalidate()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec,widthMeasureSpec)





    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (radius == 0f){
            radius =width / 2.toFloat()
            margin = resources.getDimensionPixelSize(R.dimen.margin_standard)
            posXMarker = radius
            posYMarker = radius

            init()
        }
        if(canvas != null) {
            canvas.drawCircle(radius, radius, radius-margin,
                paint
            )
            canvas.drawCircle(radius, radius, radius-margin,
                paintOverlay
            )
            canvas.drawCircle(posXMarker,posYMarker,50.toFloat(), paintBorder)
            paintBorder.color = (colorMarker)
            canvas.drawCircle(posXMarker,posYMarker,45.toFloat(), paintBorder)
            paintBorder.color = (Color.WHITE)

        }



    }
    fun setColor(color:Int):Unit{
        this.colorMarker = color
        setCoordinates(calculateCords(color))
        postInvalidate()
    }
    fun setCoordinates(pair: Pair<Float,Float>):Unit{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            posXMarker = pair.first.toFloat()
            posYMarker = pair.second.toFloat()
            //colorMarker = Color.valueOf(Color.CYAN).toArgb(). * posXMarker / width

            postInvalidate()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> updateColorOnMotionEvent(event)
            MotionEvent.ACTION_MOVE -> updateColorOnMotionEvent(event)
            MotionEvent.ACTION_UP -> {
                updateColorOnMotionEvent(event)
            }
        }

        return true
    }

    private fun updateColorOnMotionEvent(event: MotionEvent) {
        var x :Float = event.x
        var y :Float= event.y
        val distance = sqrt((event.x-radius.toDouble()).pow(2.0)+(event.y-radius.toDouble()).pow(2.0))
        if (distance > radius-margin) {
            return
        }
        val color = calculateColor(x.toInt(),y.toInt())
        this.colorMarker = color
        setColor(color)
        setCoordinates(calculateCords(color))


    }


    private fun calculateColor(x:Int,y:Int): Int {
        val legX = x - radius
        val legY = y - radius
        val hypot = hypot(legX.toFloat(), legY.toFloat())
        val hue = (Math.toDegrees(atan2(legY.toDouble(), legX.toDouble())) + 360) % 360
        val saturation = hypot / (radius)
        val i = Color.HSVToColor(floatArrayOf(hue.toFloat(),saturation,255.toFloat()))
        return i
    }
    private fun calculateCords(color:Int): Pair<Float, Float> {
        val hsv = FloatArray(3)
        val i = Color.colorToHSV(color,hsv)

        val legXFromMid = (width / 2.toFloat()) + cos(Math.toRadians(hsv[0].toDouble())) * (radius) * hsv[1]
        val legY = (radius) + sin(Math.toRadians(hsv[0].toDouble())) * (radius)* hsv[1]
        return Pair(legXFromMid.toFloat(),legY.toFloat())
    }


}