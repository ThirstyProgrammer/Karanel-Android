package com.oqurystudio.karanel.android.ui.form

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentFormChildBinding
import com.oqurystudio.karanel.android.listener.AlertDialogButtonListener
import com.oqurystudio.karanel.android.util.*
import com.oqurystudio.karanel.android.widget.*
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class FormChildFragment : Fragment() {

    var downloadManagerStatus = -1
    private lateinit var mViewBinding: FragmentFormChildBinding
    private val mViewModel: FormViewModel by viewModels()
    private var isFromParentForm = false
    private var downloadID: Long = 1L
    private var downloadUrl = ""
    private var fileName = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentFormChildBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFromParentForm = FormChildFragmentArgs.fromBundle(arguments as Bundle).isFromParentForm
        mViewModel.parentId = FormChildFragmentArgs.fromBundle(arguments as Bundle).idParent
        mViewModel.childId = FormChildFragmentArgs.fromBundle(arguments as Bundle).childId
        mViewModel.updateParentPayload(FormChildFragmentArgs.fromBundle(arguments as Bundle).parentPayload)
        mViewBinding.apply {
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
            tilNikChild.apply {
                setupEditText(
                    title = "NIK Anak",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
                setupErrorState("Silahkan Masukkan NIK")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan NIK")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.nik = text.toString()
                    mViewModel.updateFormChildState()
                }
            }
            tilName.apply {
                setupEditText(
                    title = "Nama Anak",
                )
                setupErrorState("Silahkan Masukkan Nama Anak")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Nama Anak")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.name = text.toString()
                    mViewModel.updateFormChildState()
                }
            }
            spinnerGender.apply {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_spinner,
                    R.id.tv_spinner,
                    arrayOf("Laki-laki", "Perempuan")
                )
                setupSpinner(title = "Jenis Kelamin", adapter)
                spCustom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                        mViewModel.childPayload.gender =
                            when (adapter?.getItemAtPosition(position).toString()) {
                                "Laki-laki" -> "L"
                                "Perempuan" -> "P"
                                else -> "L"
                            }

                        mViewModel.updateFormChildState()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                }
            }
            tilBirthPlace.apply {
                setupEditText(
                    title = "Tempat Lahir",
                )
                setupErrorState("Silahkan Masukkan Tempat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Tempat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.birthPlace = text.toString()
                    mViewModel.updateFormChildState()
                }
            }
            tilBirthDate.apply {
                setupEditText(
                    title = "Tanggal Lahir",
                    suffixDrawable = R.drawable.ic_calendar
                )
                etCustom.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", Date())
                btnSuffix.setOnClickListener {
                    etCustom.performClick()
                }
                setupErrorState("Silahkan Masukkan Tanggal Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Tanggal Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.birthDate = changeDateFormat(text.toString())
                    mViewModel.updateFormChildState()
                }
            }
            spinnerBirthType.apply {
                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        R.layout.item_spinner,
                        R.id.tv_spinner,
                        arrayOf("Tunggal", "Kembar 1", "Kembar 2", "Kembar 3")
                    )
                setupSpinner(title = "Jenis Kelahiran", adapter)
                spCustom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                        mViewModel.childPayload.birthType = adapter?.getItemAtPosition(position).toString()
                        mViewModel.updateFormChildState()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                }
            }
            tilAgeWhenBirth.apply {
                setupEditText(
                    title = "Usia Kehamilan Saat Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                    indicator = "Minggu"
                )
                setupErrorState("Silahkan Masukkan Usia Kehamilan Ibu Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Usia Kehamilan Ibu Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.ageOfBirth = if (!text.isNullOrBlank()) text.toString().toInt() else 1
                    mViewModel.updateFormChildState()
                }
                etCustom.filters = arrayOf(InputFilter.LengthFilter(3))
            }
            tilBodyWeight.apply {
                setupEditText(
                    title = "Berat Badan Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "Kg"
                )
                setupErrorState("Silahkan Masukkan Berat Badan Anak Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Berat Badan Anak Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.record.weight = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormChildState()
                }
            }
            tilBodyHeight.apply {
                setupEditText(
                    title = "Panjang Badan Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "cm"
                )
                setupErrorState("Silahkan Masukkan Panjang Badan Anak Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Panjang Badan Anak Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.record.height = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormChildState()
                }
            }
            tilHeadCircumference.apply {
                setupEditText(
                    title = "Lingkar Kepala Saat Lahir",
                    inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL,
                    indicator = "cm"
                )
                setupErrorState("Silahkan Masukkan Lingkar Kepala Anak Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Lingkar Kepala Anak Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.record.headCircumference = if (!text.isNullOrBlank()) text.toString().toDouble() else 0.0
                    mViewModel.updateFormChildState()
                }
            }
            spinnerBloodType.apply {
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.item_spinner,
                    R.id.tv_spinner,
                    arrayOf("A", "AB", "B", "O")
                )
                setupSpinner(title = "Golongan Darah", adapter)
                spCustom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapter: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                        mViewModel.childPayload.bloodType = adapter?.getItemAtPosition(position).toString()
                        mViewModel.updateFormChildState()
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {}

                }
            }
            tilChildOrder.apply {
                setupEditText(
                    title = "Urutan Anak",
                    inputType = InputType.TYPE_CLASS_NUMBER,
                )
                setupErrorState("Silahkan Masukkan Urutan Anak Saat Lahir")
                etCustom.doOnTextChanged { text, start, before, count ->
                    if (text.isNullOrBlank()) {
                        setupErrorState("Silahkan Masukkan Urutan Anak Saat Lahir")
                    } else {
                        setupNormalState()
                    }
                    mViewModel.childPayload.childOrder = if (!text.isNullOrBlank()) text.toString().toInt() else 0
                    mViewModel.updateFormChildState()
                }
                etCustom.filters = arrayOf(InputFilter.LengthFilter(2))
            }
            btnSubmit.setOnSafeClickListener {
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                if (mViewModel.childId.isBlank()) {
                    mViewModel.getToken()
                } else {
                    mViewModel.updateChild()
                }
            }
        }
        handleViewModelObserver()
        if (mViewModel.childId.isNotBlank()) mViewModel.getToken()
    }

    private fun changeDateFormat(text: String): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val output = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val data = sdf.parse(text)
        return output.format(data)
    }

    private fun changeBackDateFormat(text: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val output = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val data = sdf.parse(text)
        return output.format(data)
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            if (isFromParentForm) {
                mViewModel.submitParent(it)
            } else {
                when {
                    mViewModel.childId.isNotBlank() -> {
//                        mViewModel.updateChild(it)
                        mViewModel.getChild(it)
                    }
                    mViewModel.parentId.isNotBlank() -> mViewModel.submitChild(token = it)
                    else -> mViewModel.submitChildAsParent(it)
                }
            }
        })
        mViewModel.responseGetChild.observe(viewLifecycleOwner, {
            mViewModel.updateChildPayload(it.data)
            updateField()
        })
        mViewModel.responseSubmitParent.observe(viewLifecycleOwner, {
            mViewModel.parentCode = it.data?.idKarnel.defaultDash()
            mViewModel.parentImgUrl = it.data?.imgUrl.defaultEmpty()
            mViewModel.submitChild(parentId = it.data?.id.defaultEmpty())
        })
        mViewModel.responseSubmitChild.observe(viewLifecycleOwner, {
            if (isFromParentForm) {
                DialogFactory.createDialogCodeTracking(
                    requireContext(),
                    mViewModel.parentCode,
                    object : AlertDialogButtonListener {
                        override fun onPositiveButtonClicked(dialog: Dialog) {
                            val filenameWithExt = mViewModel.parentImgUrl.substringAfterLast("/", "default.pdf")
                            val filename = filenameWithExt.substringBefore(".")
                            download(mViewModel.parentImgUrl, filename)
                            dialog.dismiss()
                        }

                        override fun onNegativeButtonCLicked(dialog: Dialog) {
                        }

                    }
                ).show()
            } else {
                if (mViewModel.childId.isBlank()){
                    makeToast("Data Anak Telah Ditambahkan")
                }else{
                    makeToast("Data Anak Telah Diperbaharui")
                }
                requireActivity().onBackPressed()
            }
        })
        mViewModel.isFormChildCompleted.observe(viewLifecycleOwner, {
            mViewBinding.btnSubmit.isEnabled = it
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            mViewBinding.viewState.handleViewState(it.first, it.second)
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }

    private fun updateField() {
        mViewBinding.apply {
            tilNikChild.etCustom.setText(mViewModel.childPayload.nik)
            tilName.etCustom.setText(mViewModel.childPayload.name)
            val gender = when (mViewModel.childPayload.gender) {
                "L" -> "Laki-laki"
                "P" -> "Perempuan"
                else -> "Laki-laki"
            }
            spinnerGender.spCustom.setSelection(getIndex(spinnerGender.spCustom, gender))
            tilBirthPlace.etCustom.setText(mViewModel.childPayload.birthPlace)
            tilBirthDate.apply {
                etCustom.setText(changeBackDateFormat(mViewModel.childPayload.birthDate))
                val output = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val date = try {
                    output.parse(mViewModel.childPayload.birthDate)
                } catch (e: Exception) {
                    Date()
                }
                etCustom.transformIntoDatePicker(requireContext(), "dd/MM/yyyy", date)
            }
            spinnerBirthType.spCustom.setSelection(getIndex(spinnerBirthType.spCustom, mViewModel.childPayload.birthType))
            if (mViewModel.childPayload.ageOfBirth > 0) {
                tilAgeWhenBirth.etCustom.setText(mViewModel.childPayload.ageOfBirth.toString())
            }
            spinnerBloodType.spCustom.setSelection(getIndex(spinnerBloodType.spCustom, mViewModel.childPayload.bloodType))
            tilChildOrder.etCustom.setText(mViewModel.childPayload.childOrder.toString())
            tilBodyWeight.containerTil.visibility = View.GONE
            tilBodyHeight.containerTil.visibility = View.GONE
            tilHeadCircumference.containerTil.visibility = View.GONE
        }
    }

    private fun getIndex(spinner: Spinner, value: String): Int {
        val totalItem = spinner.count - 1
        for (index in 0..totalItem) {
            if (spinner.getItemAtPosition(index).toString() == value) {
                return index
            }
        }
        return 0
    }

    // DOWNLOAD SECTION

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().unregisterReceiver(onDownloadComplete)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startDownload(downloadUrl, fileName)
        }
    }

    fun download(url: String, filename: String) {
        this.downloadUrl = url
        this.fileName = filename
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            startDownload(url, fileName)
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE
            )
        }
    }

    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                makeToast("ID Card Telah Di Download")
                requireActivity().finish()
            }
        }
    }

    @SuppressLint("Range")
    private fun startDownload(url: String, filename: String) {
        val ext = url.substring(url.lastIndexOf('.') + 1)
        val fileName = "$filename.$ext"
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(fileName)
            .setDescription("Sedang mengunduh")
            .setAllowedOverMetered(true) // on Mobile network
            .setAllowedOverRoaming(true) // on Roaming connection
        val downloadManager: DownloadManager? = requireActivity().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
        downloadID = downloadManager?.enqueue(request) ?: 0 // enqueue puts the download request in the queue.

        var finishDownload = false
        var progress: Int
        while (!finishDownload) {
            val cursor: Cursor? = downloadManager?.query(DownloadManager.Query().setFilterById(downloadID))
            if (cursor != null && cursor.moveToFirst()) {
                downloadManagerStatus = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
                    DownloadManager.STATUS_FAILED -> {
                        finishDownload = true
                    }
                    DownloadManager.STATUS_PAUSED -> {
                    }
                    DownloadManager.STATUS_PENDING -> {
                    }
                    DownloadManager.STATUS_RUNNING -> {
                        val total: Long = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        if (total >= 0) {
                            val downloaded: Long = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            progress = (downloaded * 100L / total).toInt()
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        progress = 100
                        // if you use aysnc task
                        // publishProgress(100);
                        finishDownload = true
                    }
                }
            }
        }
    }

    companion object {
        const val WRITE_EXTERNAL_STORAGE = 99
    }
}