package com.muzzlyworld.testapp.utils

import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageView
import kotlinx.coroutines.*
import java.net.URL


@Suppress("BlockingMethodInNonBlockingContext")
fun ImageView.loadImage(url: String) {
    val scope = CoroutineScope(Dispatchers.IO)
    val attachListener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) { scope.cancel() }
        override fun onViewAttachedToWindow(v: View?) {}
    }
    addOnAttachStateChangeListener(attachListener)
    scope.launch {
        val bitmap = try {
            BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
        } catch (e: Exception) {
            Log.e("ImageView", "loadImage: $e")
            null
        }
        if(isActive) {
            try {
                withContext(Dispatchers.Main) { bitmap?.let { setImageBitmap(it) } }
            } catch (e: Exception) { Log.e("ImageView", "loadImage: $e") }
        }
        removeOnAttachStateChangeListener(attachListener)
    }
}