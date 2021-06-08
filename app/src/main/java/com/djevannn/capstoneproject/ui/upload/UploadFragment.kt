package com.djevannn.capstoneproject.ui.upload

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.djevannn.capstoneproject.R
import com.djevannn.capstoneproject.databinding.FragmentUploadBinding
import org.koin.android.viewmodel.ext.android.viewModel


class UploadFragment : Fragment() {

    // private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!
    private val uploadViewModel: UploadViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()

        binding.btnUpload.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
//                intent.type="application/pdf"
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(
                Intent.createChooser(
                    intent,
                    "Select a file"
                ), 111
            )
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val uri =
                data!!.data as Uri //The uri with the location of the file
            // pdfView.fromUri(selectedFile).load() // Show the selected file
//             val pdf = getStringPdf(uri!!)
            // upload goes here
//            UploadUtility(requireActivity()).uploadFile(uri)
            binding.progressBar.visibility = View.VISIBLE
            try {
                uploadViewModel.upload(uri)
                Toast.makeText(
                    context,
                    "Berhasil Upload",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    context,
                    e.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.appBarMain.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = ""
        binding.appBarMain.tvAppTitle.text =
            getString(R.string.title_upload)
    }
}