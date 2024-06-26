package allen.town.focus_common.common.prefs.supportv7

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.widget.doAfterTextChanged
import androidx.preference.PreferenceViewHolder
import androidx.preference.SeekBarPreference
import allen.town.focus_common.R
import code.name.monkey.appthemehelper.ThemeStore
import code.name.monkey.appthemehelper.util.TintHelper

class ATESeekBarPreference @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1,
    defStyleRes: Int = -1
) : SeekBarPreference(context, attrs, defStyleAttr, defStyleRes) {

    var unit: String = ""

    init {
        val attributes =
            context.obtainStyledAttributes(attrs, code.name.monkey.appthemehelper.R.styleable.ATESeekBarPreference, 0, 0)

        attributes.getString(code.name.monkey.appthemehelper.R.styleable.ATESeekBarPreference_ateKey_pref_unit)?.let {
            unit = it
        }
        attributes.recycle()
        icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            ThemeStore.accentColor(context), BlendModeCompat.SRC_IN
        )
    }

    override fun onBindViewHolder(view: PreferenceViewHolder) {
        super.onBindViewHolder(view)
        val seekBar = view.findViewById(R.id.seekbar) as SeekBar
        TintHelper.setTintAuto(
            seekBar, // Set MD3 accent if MD3 is enabled or in-app accent otherwise
            ThemeStore.accentColor(context), false
        )
        (view.findViewById(R.id.seekbar_value) as TextView).apply {
            appendUnit(editableText)
            doAfterTextChanged {
                appendUnit(it)
            }
        }
    }

    private fun TextView.appendUnit(editable: Editable?) {
        if (!editable.toString().endsWith(unit)) {
            append(unit)
        }
    }
}
