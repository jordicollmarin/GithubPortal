package cat.jorcollmar.githubportal.commons.extensions

import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import cat.jorcollmar.githubportal.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

fun ImageView.loadImageCenterCrop(uri: Uri?) {
    Picasso
        .get()
        .load(uri)
        .placeholder(R.drawable.placeholder)
        .into(this, object : Callback {
            override fun onSuccess() {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }

            override fun onError(e: Exception) {
                setImageDrawable(ContextCompat.getDrawable(context, R.drawable.error))
            }
        })
}