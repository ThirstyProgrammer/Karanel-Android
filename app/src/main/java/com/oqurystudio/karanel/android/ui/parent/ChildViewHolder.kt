package com.oqurystudio.karanel.android.ui.parent

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.ItemChildBinding
import com.oqurystudio.karanel.android.model.Gender
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.util.Helper
import com.oqurystudio.karanel.android.util.defaultEmpty

class ChildViewHolder constructor(private var mViewBinding: ItemChildBinding) : RecyclerView.ViewHolder(mViewBinding.root) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(data: Parent.Child) {
        mViewBinding.apply {
            tvName.text = data.name
            tvAge.text = Helper.getChildAge(data.birthDate.defaultEmpty())
            setupGender(data.gender.defaultEmpty())
            setupStatus(data.status.defaultEmpty())
            tvStatus.visibility = if (data.status.isNullOrBlank()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }

    private fun setupGender(gender: String) {
        when (Gender.getEnum(gender)) {
            Gender.LAKI_LAKI -> {
                mViewBinding.apply {
                    ivAvatar.setImageDrawable(ContextCompat.getDrawable(root.context, R.drawable.il_profile_male))
                    tvGender.apply {
                        text = "Laki-laki"
                        setTextColor(ContextCompat.getColor(mViewBinding.root.context, R.color.primary_color))
                        background = ContextCompat.getDrawable(mViewBinding.root.context, R.drawable.bg_rounded_male)
                    }
                }
            }
            Gender.PEREMPUAN -> {
                mViewBinding.apply {
                    ivAvatar.setImageDrawable(ContextCompat.getDrawable(root.context, R.drawable.il_profile_female))
                    tvGender.apply {
                        text = "Perempuan"
                        setTextColor(ContextCompat.getColor(mViewBinding.root.context, R.color.red_text))
                        background = ContextCompat.getDrawable(mViewBinding.root.context, R.drawable.bg_rounded_female)
                    }
                }
            }
        }
    }

    private fun setupStatus(status: String) {
        when (status) {
            "Stunting" -> {
                mViewBinding.apply {
                    tvStatus.apply {
                        text = status
                        setTextColor(ContextCompat.getColor(mViewBinding.root.context, R.color.red_text))
                        background = ContextCompat.getDrawable(mViewBinding.root.context, R.drawable.bg_rounded_female)
                    }
                }
            }
            else -> {
                mViewBinding.apply {
                    tvStatus.apply {
                        text = status
                        setTextColor(ContextCompat.getColor(mViewBinding.root.context, R.color.green_text))
                        background = ContextCompat.getDrawable(mViewBinding.root.context, R.drawable.bg_rounded_normal)
                    }
                }
            }
        }
    }
}