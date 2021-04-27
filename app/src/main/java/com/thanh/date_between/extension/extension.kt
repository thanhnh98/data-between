package com.thanh.date_between.extension

import android.app.Activity
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.RotateAnimation
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.CompletableSubject
import java.util.concurrent.TimeUnit

fun Activity.showMessage(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.onClick(f: () -> Unit){
    setOnClickListener {
        f.invoke()
    }
}

fun EditText.text(): String{
    return text.toString()
}

fun EditText.createDebounce(
    composite: CompositeDisposable,
    debounceMillis: Long = 500,
    minCharactersToHandle: Long = 1,
    action: (s: String) -> Unit)
{
    composite.add(
        RxTextView.textChanges(this)
            .debounce(debounceMillis, TimeUnit.MILLISECONDS)
            .filter { str -> str.isNotEmpty() && str.length >= minCharactersToHandle }
            .map(CharSequence::toString)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { res -> action(res) }
    )
}

fun String.isSafe():Boolean{
    return !isNullOrEmpty() && !isNullOrBlank()
}
fun String.toSafeUrl(): String{
    return when {
        this.contains("http://") -> this
        this.contains("https://") -> this
        else -> "https://$this"
    };
}

fun View.fadeIn(duration: Long): Completable {
    val animSubject = CompletableSubject.create()
    alpha = 0f
    return animSubject.doOnSubscribe{
        ViewCompat.animate(this)
            .setDuration(duration)
            .alpha(1f)
            .rotationX(0.5f)
            .rotationY(0.7f)
            .withEndAction {
                animSubject.onComplete()
            }.start()
    }
}

fun View.rotate(duration: Long, time: Int): Completable{
    val animSubject = CompletableSubject.create()
    var animSet = AnimationSet(true)
    var animation = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
    animation.repeatCount = time
    animation.duration = duration
    animation.interpolator = AccelerateDecelerateInterpolator()
    animation.setAnimationListener(object : Animation.AnimationListener{
        override fun onAnimationStart(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
            animSubject.onComplete()
        }

        override fun onAnimationRepeat(p0: Animation?) {
        }

    })

    animSet.addAnimation(animation)

    return animSubject.doOnSubscribe{
        startAnimation(animation)
    }
}