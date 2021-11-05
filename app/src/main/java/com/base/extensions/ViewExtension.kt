package com.zaka.base.extensions

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.widget.SwitchCompat

inline fun View.hide(){
    this.visibility=View.GONE
}
inline fun View.show(){
    this.visibility=View.VISIBLE
}

inline fun View.isShow(show: Boolean){
    if (show)
    this.visibility=View.VISIBLE
    else this.visibility=View.GONE

}


inline fun View.invisible(){
    this.visibility=View.INVISIBLE
}
inline fun Context.getColorById(@ColorRes  id:Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(id)
    }else{
        resources.getColor(id)


    }}

fun scrollTo(view: View, scroll: View, duration: Int=1000) {
    val location = IntArray(2)

    view.getLocationOnScreen(location)
    val y = location[1]
    val x=location[0]

    /*  val x = 0
      val y = 0*/

    val xTranslate: ObjectAnimator = ObjectAnimator.ofInt(scroll, "scrollX", x)
    val yTranslate: ObjectAnimator = ObjectAnimator.ofInt(scroll, "scrollY", y)
    val animators = AnimatorSet()
    animators.duration = duration.toLong()
    animators.playTogether(xTranslate, yTranslate)
    animators.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(arg0: Animator?) { // TODO Auto-generated method stub
        }

        override fun onAnimationRepeat(arg0: Animator?) { // TODO Auto-generated method stub
        }

        override fun onAnimationEnd(arg0: Animator?) { // TODO Auto-generated method stub
        }

        override fun onAnimationCancel(arg0: Animator?) { // TODO Auto-generated method stub
        }
    })
    animators.start()
}




inline fun  View.locateView(): Rect? {
    val v=this
    val loc_int = IntArray(2)
    if (v == null) return null
    try {
        v.getLocationOnScreen(loc_int)
    } catch (npe: NullPointerException) {
        return null
    }

    val location = Rect()
    location.left = loc_int[0]
    location.top = loc_int[1]
    location.right = location.left + v.width
    location.bottom = location.top + v.height
    return location
}