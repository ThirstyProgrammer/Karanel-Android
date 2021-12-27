package com.oqurystudio.karanel.android.ui.home

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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.oqurystudio.karanel.android.R
import com.oqurystudio.karanel.android.databinding.FragmentHomeParentBinding
import com.oqurystudio.karanel.android.listener.AlertDialogButtonListener
import com.oqurystudio.karanel.android.listener.OnItemClickListener
import com.oqurystudio.karanel.android.model.Parent
import com.oqurystudio.karanel.android.network.ViewState
import com.oqurystudio.karanel.android.ui.MainActivity
import com.oqurystudio.karanel.android.ui.parent.ChildAdapter
import com.oqurystudio.karanel.android.util.*
import com.oqurystudio.karanel.android.util.Constant.Companion.WRITE_EXTERNAL_STORAGE
import com.oqurystudio.karanel.android.widget.DialogFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeParentFragment : Fragment(), OnItemClickListener {

    var downloadManagerStatus = -1
    private lateinit var mViewBinding: FragmentHomeParentBinding
    private lateinit var mAdapter: ChildAdapter
    private val mViewModel: HomeParentViewModel by viewModels()
    private var downloadID: Long = 1L
    private var downloadUrl = ""
    private var fileName = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mViewBinding = FragmentHomeParentBinding.inflate(inflater)
        return mViewBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.getString(Constant.Extras.PARENT_ID)
        requireActivity().registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onItemClicked(v: View, position: Int) {
        val toParentActivity = HomeParentFragmentDirections.actionHomeParentFragmentToParentActivity(mAdapter.getItem(position).id.toString())
        findNavController().navigate(toParentActivity)
    }

    override fun onResume() {
        super.onResume()
        if (!mViewModel.token.value.isNullOrBlank()) {
            mViewModel.getParent(mViewModel.token.value.toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = ChildAdapter()
        mAdapter.setOnItemClickListener(this)
        mViewBinding.apply {
            val gridLayoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            rvChilds.apply {
                layoutManager = gridLayoutManager
                adapter = mAdapter
            }
            btnAddNewChild.setOnSafeClickListener {
                findNavController().navigate(R.id.action_homeParentFragment_to_formChildActivity)
            }
        }
        mViewModel.getToken()
        handleViewModelObserver()
    }

    private fun handleViewModelObserver() {
        mViewModel.token.observe(viewLifecycleOwner, {
            mViewModel.getParent(it)
        })
        mViewModel.response.observe(viewLifecycleOwner, {
            if (it.data != null) setupView(it.data)
        })
        mViewModel.viewState.observe(viewLifecycleOwner, {
            if (it.first == ViewState.UNAUTHORIZED) {
                mViewModel.signOut()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                mViewBinding.viewState.handleViewState(it.first, it.second)
            }
        })

        mViewModel.error.observe(viewLifecycleOwner, {
            mViewBinding.viewState.setErrorMessage(it)
        })
    }

    private fun setupView(data: Parent.Data) {
        mViewBinding.apply {
            tvMotherName.text = data.motherName
            tvFatherName.text = data.fatherName
            tvAddress.text = data.address
            btnDownload.setOnSafeClickListener {
                val filenameWithExt = data.imgUrl.defaultEmpty().substringAfterLast("/", "default.pdf")
                val filename = filenameWithExt.substringBefore(".")
                download(data.imgUrl.defaultEmpty(), filename)
            }
            btnDownload.visibility = if (data.imgUrl.isNullOrBlank()) {
                View.GONE
            } else {
                View.VISIBLE
            }
            if(checkIfThereIsStuntingChildren(data.children)){
                val number = data.posyandu?.phoneNumber ?: "+6283291317367"
                DialogFactory.createDialogStunting(
                    requireContext(),
                    number,
                    object : AlertDialogButtonListener {
                        override fun onPositiveButtonClicked(dialog: Dialog) {
                            val smsIntent = Intent(Intent.ACTION_VIEW)
                            smsIntent.type = "vnd.android-dir/mms-sms"
                            smsIntent.putExtra("address", number)
//                            smsIntent.putExtra("sms_body", "Body of Message")
                            startActivity(smsIntent)
                            dialog.dismiss()
                        }

                        override fun onNegativeButtonCLicked(dialog: Dialog) {
                        }

                    }
                ).show()
            }
        }
        mAdapter.setData(data.children ?: arrayListOf())
    }

    private fun checkIfThereIsStuntingChildren(data: List<Parent.Child>?): Boolean {
        if (data.isNullOrEmpty()) {
            return false
        } else {
            data.forEach {
                when (it.status.defaultEmpty().lowercase()) {
                    "stunting", "pendek", "sangat pendek" -> return true
                }
            }
            return false
        }
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
}