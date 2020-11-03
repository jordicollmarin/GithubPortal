package cat.jorcollmar.githubportal.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DimenRes
import androidx.constraintlayout.widget.ConstraintLayout
import cat.jorcollmar.githubportal.R
import cat.jorcollmar.githubportal.databinding.InfoItemBinding

class InfoItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context) {
    private var binding = InfoItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        attrs?.let {
            val attrsValues = context.obtainStyledAttributes(attrs, R.styleable.InfoItem)

            setUpItem(
                attrsValues.getString(R.styleable.InfoItem_label),
                attrsValues.getString(R.styleable.InfoItem_value)
            )

            attrsValues.recycle()
        }
    }

    fun setUpItem(label: String?, value: String?) {
        label?.let {
            binding.itemLabel.text = label
        } ?: run {
            binding.itemLabel.text = EMPTY_VALUE
        }

        value?.let {
            binding.itemValue.text = value
        } ?: run {
            binding.itemValue.text = EMPTY_VALUE
        }
    }

    companion object {
        private const val EMPTY_VALUE = "-"
    }
}