package com.example.purpleapp.api

import android.R
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import org.apache.http.HttpStatus
import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL


class ImageDownloaderTask(imageView: ImageView?) :
    AsyncTask<String?, Void?, Bitmap?>() {
    private val imageViewReference: WeakReference<ImageView>?

    init {
        imageViewReference = WeakReference<ImageView>(imageView)
    }

    protected override fun doInBackground(vararg params: String?): Bitmap? {
        return downloadBitmap(params[0]!!)
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        var bitmap = bitmap
        if (isCancelled) {
            bitmap = null
        }
        if (imageViewReference != null) {
            val imageView: ImageView? = imageViewReference.get()
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                } else {
                    val placeholder: Drawable =
                        imageView.getContext().getResources().getDrawable(R.drawable.placeholder)
                    imageView.setImageDrawable(placeholder)
                }
            }
        }
    }

    private fun downloadBitmap(imageUrl: String): Bitmap? {
        var urlConnection: HttpURLConnection? = null
        try {
            val uri = URL(imageUrl)
            urlConnection = uri.openConnection() as HttpURLConnection
            val statusCode: Int = urlConnection.getResponseCode()
            if (statusCode != HttpStatus.SC_OK) {
                return null
            }
            val inputStream: InputStream = urlConnection.getInputStream()
            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            urlConnection!!.disconnect()
            Log.w("ImageDownloader", "Error downloading image from $imageUrl")
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect()
            }
        }
        return null
    }
}