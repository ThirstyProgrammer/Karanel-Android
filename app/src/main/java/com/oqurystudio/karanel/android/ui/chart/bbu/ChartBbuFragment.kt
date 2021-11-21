package com.oqurystudio.karanel.android.ui.chart.bbu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentBbuBinding
import com.oqurystudio.karanel.android.model.Chart
import com.oqurystudio.karanel.android.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChartBbuFragment : Fragment() {

    private lateinit var mViewBinding: FragmentBbuBinding
    private val mViewModel: ChartBbuViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentBbuBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            mViewModel.childId = ChartBbuFragmentArgs.fromBundle(arguments as Bundle).childId.defaultEmpty()
            mViewModel.parentId = ChartBbuFragmentArgs.fromBundle(arguments as Bundle).parentId
        }
        mViewBinding.apply {
            btnBack.setOnSafeClickListener {
                requireActivity().onBackPressed()
            }
        }
        handleViewModelObserver()
        mViewModel.getToken()
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.getBbu(it)
        })
        mViewModel.response.observe(viewLifecycleOwner, {
            if (it.data != null) {
                setupView(it.data)
            }
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }

    private fun setupView(data: Chart.Data) {
        mViewBinding.apply {
            tvWeight.text = ": ${data.weight} Kg"
            tvAge.text = ": ${data.age}"
            tvStatus.text = ": ${data.status}"
            if (table.childCount > 1) {
                table.removeViews(1, (table.childCount - 1))
            }
            data.records?.forEachIndexed { index, record ->
                if (index % 2 == 0) {
                    table.addView(initRowEven(record))
                } else {
                    table.addView(initRowOdd(record))
                }
            }
        }
    }

    private fun goToFormProgress(record: Chart.Record) {
        if (mViewModel.parentId.isBlank()) {
            findNavController().navigate(
                R.id.action_ChartBbuFragment_to_formProgressFragment2,
                bundleOf(
                    "childId" to mViewModel.childId,
                    "parentId" to mViewModel.parentId,
                    "recordId" to record.id
                )
            )
        } else {
            findNavController().navigate(
                R.id.action_ChartBbuFragment2_to_formProgressFragment,
                bundleOf(
                    "childId" to mViewModel.childId,
                    "parentId" to mViewModel.parentId,
                    "recordId" to record.id
                )
            )
        }
    }

    private fun initRowOdd(record: Chart.Record): TableRow {
        val tableRow = TableRow(requireContext())
        val layoutParams = TableRow.LayoutParams()
        layoutParams.weight = 1F
        tableRow.layoutParams = layoutParams
        tableRow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.table_row_odd))
        tableRow.addView(initAge(record.month.toString()))
        tableRow.addView(initStatus(record.status.defaultDash()))
        tableRow.addView(initBB(record.weight.toString()))
        tableRow.addView(initAction { goToFormProgress(record) })
        return tableRow
    }

    private fun initRowEven(record: Chart.Record): TableRow {
        val tableRow = TableRow(requireContext())
        val layoutParams = TableRow.LayoutParams()
        layoutParams.weight = 1F
        tableRow.layoutParams = layoutParams
        tableRow.addView(initAge(record.month.toString()))
        tableRow.addView(initStatus(record.status.defaultDash()))
        tableRow.addView(initBB(record.weight.toString()))
        tableRow.addView(initAction { goToFormProgress(record) })
        return tableRow
    }

    private fun initAge(age: String): TextView {
        val textView = TextView(requireContext())
        val layoutParams = TableRow.LayoutParams()
        layoutParams.column = 1
        layoutParams.weight = 0.1F
        textView.text = age
        textView.setPadding(ViewUtil.dpToPx(8))
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey_color))
        textView.layoutParams = layoutParams
        return textView
    }

    private fun initStatus(status: String): TextView {
        val textView = TextView(requireContext())
        val layoutParams = TableRow.LayoutParams()
        layoutParams.column = 2
        layoutParams.weight = 0.7F
        textView.text = status
        textView.setPadding(ViewUtil.dpToPx(8))
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey_color))
        textView.layoutParams = layoutParams
        return textView
    }

    private fun initBB(text: String): TextView {
        val textView = TextView(requireContext())
        val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        layoutParams.column = 3
        layoutParams.weight = 0.1F
        textView.text = text
        textView.setPadding(ViewUtil.dpToPx(8))
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey_color))
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.layoutParams = layoutParams
        return textView
    }

    private fun initAction(action: () -> Unit): TextView {
        val textView = TextView(requireContext())
        val layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        layoutParams.column = 4
        layoutParams.weight = 0.1F
        textView.text = "Edit"
        textView.setOnSafeClickListener {
            action()
        }
        textView.setPadding(ViewUtil.dpToPx(8))
        textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
        textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        textView.layoutParams = layoutParams
        return textView
    }
}