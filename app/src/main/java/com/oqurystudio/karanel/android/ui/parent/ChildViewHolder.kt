package com.oqurystudio.karanel.android.ui.parent

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.ItemChildBinding
import com.oqurystudio.karanel.android.model.Gender
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.util.defaultEmpty
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class ChildViewHolder constructor(private var mViewBinding: ItemChildBinding) : RecyclerView.ViewHolder(mViewBinding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(data: Parent.Child) {
        mViewBinding.apply {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(data.birthDate.defaultEmpty())
            val months = monthsBetween(date)
            tvName.text = data.name
            tvAge.text = getWording(months, weeksBetween(date))
            setupGender(data.gender.defaultEmpty())
        }
    }

    private fun monthsBetween(d1: Date): Int {
        val calendar = Calendar.getInstance()
        val month2 = 12 * calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH)
        calendar.time = d1
        val month1 = 12 * calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH)
        return abs(month2 - month1)
    }

    private fun weeksBetween(d1: Date): Int {
        val calendar = Calendar.getInstance()
        val week2 = calendar.get(Calendar.WEEK_OF_MONTH)
        calendar.time = d1
        val week1 = calendar.get(Calendar.WEEK_OF_MONTH)
        return abs(week2 - week1)
    }

    private fun getWording(months: Int, weeks: Int): String {
        val year = months / 12
        val modulusMonth = months % 12
        return when {
            months >= 12 -> {
                "$year Tahun $modulusMonth Bulan"

            }
            months > 0 -> {
                "$months Bulan"
            }
            else -> {
                "$weeks Minggu"
            }
        }
    }

    private fun setupGender(gender: String) {
        when (Gender.getEnum(gender)) {
            Gender.LAKI_LAKI -> {
                mViewBinding.tvGender.apply {
                    text = "Laki-laki"
                    setTextColor(ContextCompat.getColor(mViewBinding.root.context, R.color.primary_color))
                    background = ContextCompat.getDrawable(mViewBinding.root.context, R.drawable.bg_rounded_male)
                }
            }
            Gender.PEREMPUAN -> {
                mViewBinding.tvGender.apply {
                    text = "Perempuan"
                    setTextColor(ContextCompat.getColor(mViewBinding.root.context, R.color.red_text))
                    background = ContextCompat.getDrawable(mViewBinding.root.context, R.drawable.bg_rounded_female)
                }
            }
        }

    }
}