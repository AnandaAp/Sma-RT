package com.maluku.sma_rt.view.warga

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentEditProfileBinding
import com.maluku.sma_rt.databinding.FragmentPengaturanTokoBinding
import com.maluku.sma_rt.extentions.UserSession


class PengaturanToko : Fragment() {


    private lateinit var binding: FragmentPengaturanTokoBinding
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        imagePick()
        tokoProfile()
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_pengaturanToko_to_kelolaToko)
        }
    }


    private fun bindingView(): View {
        binding = FragmentPengaturanTokoBinding.inflate(layoutInflater)
        return binding.root
    }


    private fun imagePick(){
        binding.btnPickprofile.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.profileToko.setImageURI(imageUri)
        }
    }

    private fun tokoProfile() {
        binding.btnUbahtoko.setOnClickListener {
            val preferences = UserSession(requireActivity())
            preferences.clearSharedPreference()
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.custom_dialog_profiletoko)
            val btnSimpan = dialog.findViewById<TextView>(R.id.btn_ok)

            btnSimpan.setOnClickListener {
                dialog.dismiss()
            }


            dialog.show()
        }
    }

}