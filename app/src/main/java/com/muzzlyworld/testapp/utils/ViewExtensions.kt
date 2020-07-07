package com.muzzlyworld.testapp.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.annotation.WorkerThread
import kotlinx.coroutines.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.URL


@Suppress("BlockingMethodInNonBlockingContext")
fun ImageView.loadImage(url: String) {
    val scope = CoroutineScope(Dispatchers.IO)
    val attachListener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) { scope.cancel() }
        override fun onViewAttachedToWindow(v: View?) { setImageBitmap(null) }
    }
    addOnAttachStateChangeListener(attachListener)
    scope.launch {
        val bitmap = try {
            val connection = URL(url).openConnection()
            decodeSampledBitmap(connection.getInputStream(), width, height)
        } catch (e: Exception) {
            Log.e("ImageView", "loadImage: $e")
            null
        }
        Log.d("ImageView", "loadImage: ${bitmap == null}")
        if(isActive) {
            try {
                withContext(Dispatchers.Main) { bitmap?.let { setImageBitmap(it) } }
            } catch (e: Exception) { Log.e("ImageView", "loadImage: $e") }
        }
        removeOnAttachStateChangeListener(attachListener)
    }
}

@WorkerThread
private fun decodeSampledBitmap(
    inputStream: InputStream,
    reqWith: Int,
    reqHeight: Int
) : Bitmap? = BitmapFactory.Options().run {
    val baos = ByteArrayOutputStream()
    inputStream.copyTo(baos)

    inJustDecodeBounds = true

    BitmapFactory.decodeStream(inputStream, null, this)

    inSampleSize = calculateInSampleSize(this, reqWith, reqHeight)

    inJustDecodeBounds = false

    val copyStream: InputStream = ByteArrayInputStream(baos.toByteArray())
    BitmapFactory.decodeStream(copyStream, null, this)
}

private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
    val (height: Int, width: Int) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > reqHeight || width > reqWidth) {

        val halfHeight: Int = height / 2
        val halfWidth: Int = width / 2

        while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}