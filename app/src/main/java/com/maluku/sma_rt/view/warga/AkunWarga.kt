package com.maluku.sma_rt.view.warga

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentAkunWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.model.warga.GetMe
import com.maluku.sma_rt.view.activity.MainActivity
import com.maluku.sma_rt.view.viewInterface.WargaInterface
import java.io.File

private const val TAG = "AKUN WARGA"

class AkunWarga : Fragment() {

    private lateinit var binding: FragmentAkunWargaBinding

    private var idWarga: String = ""
    private var nama: String = ""
    private var noHp: String = ""
    private var email: String = ""
    private var gender: String = ""
    private var gambarWarga: String = "default_image"
    private var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return bindingView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataWarga()
        navigateToKelolaToko()
        navigateToEditProfile()
        navigateToChangePassword()
        logout()
    }

    private fun getToken(): String {
        val preferences = UserSession(requireActivity())
        val token = preferences.getValueString(UserSession.SHARED_PREFERENCE_TOKEN_KEY)
        Log.d(TAG,token)
        return token
    }

    private fun bindingView(): View {
        binding = FragmentAkunWargaBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setDataWarga() {
        val userSession = UserSession(requireActivity())
        idWarga = userSession.getValueString(UserSession.SHARED_PREFERENCE_ID_KEY)
        nama = userSession.getValueString(UserSession.SHARED_PREFERENCE_NAME_KEY)
        noHp = userSession.getValueString(UserSession.SHARED_PREFERENCE_PHONE_NUMBER_KEY)
        email = userSession.getValueString(UserSession.SHARED_PREFERENCE_EMAIL_KEY)
        gender = userSession.getValueString(UserSession.SHARED_PREFERENCE_GENDER_KEY)
        password = userSession.getValueString(UserSession.SHARED_PREFERENCE_PASSWORD_KEY)
        gambarWarga = userSession.getValueString(UserSession.SHARED_PREFERENCE_PICTURE_KEY)

        binding.namaWarga.text = nama

        // Firebase Storage
        val storageRef = FirebaseStorage.getInstance().reference.child("user/${gambarWarga}")
        Log.d(ContentValues.TAG,"Adapter get ref image: $storageRef")
        val localFile = File.createTempFile("tempFile","jpg")
        storageRef.getFile(localFile).addOnSuccessListener {
            // Tampilkan gambar dengan Glide
            Glide.with(this)
                .load(localFile.path)
                .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                .into(binding.profileImage)
        }.addOnFailureListener {

        }
    }



    private fun navigateToKelolaToko() {
        binding.menuKelolatoko.setOnClickListener{
            findNavController().navigate(R.id.action_akunWarga_to_kelolaToko)
        }
    }

    private fun navigateToEditProfile() {
        binding.menuEditprofile.setOnClickListener{
            val direction = AkunWargaDirections
                .actionAkunWargaToEditProfile(
                    idWarga, nama, noHp, email, gender, gambarWarga
                )
            findNavController().navigate(direction)
        }
    }

    private fun navigateToChangePassword() {
        binding.menuChangepassword.setOnClickListener{
            val direction = AkunWargaDirections
                .actionAkunWargaToChangePassword(
                    idWarga, nama, noHp, email, gender, gambarWarga, password
                )
            findNavController().navigate(direction)
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            val preferences = UserSession(requireActivity())
            preferences.clearSharedPreference()
            val intent = Intent(requireActivity(), MainActivity::class.java)

            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.custom_dialog_logout)
            val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
            val btnBatal = dialog.findViewById<TextView>(R.id.btn_batal)


            btnOk.setOnClickListener {
                    dialog ->startActivity(intent)
                requireActivity().finish()
            }
            btnBatal.setOnClickListener {
                dialog.dismiss()
            }


            dialog.show()
        }
    }
}