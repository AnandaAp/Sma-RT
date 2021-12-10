package com.maluku.sma_rt.view.warga

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.maluku.sma_rt.R
import com.maluku.sma_rt.databinding.FragmentAkunWargaBinding
import com.maluku.sma_rt.extentions.UserSession
import com.maluku.sma_rt.view.activity.DashboardWargaActivity
import com.maluku.sma_rt.view.activity.MainActivity
import kotlinx.coroutines.NonCancellable.cancel

class AkunWarga : Fragment() {

    private lateinit var binding: FragmentAkunWargaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = bindingView()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        navigateToKelolaToko()
        navigateToEditProfile()
        logout()
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener{
            val intent = Intent (activity, DashboardWargaActivity::class.java)
            startActivity(intent)
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            val preferences = UserSession(requireActivity())
            preferences.clearSharedPreference()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.custom_dialog_layout, null)
            val dialogAlert = AlertDialog.Builder(activity).setView(dialogView)
            val btnOk = dialogView.findViewById<TextView>(R.id.btn_ok)
            val btnBatal = dialogView.findViewById<TextView>(R.id.btn_batal)
            val dialog = dialogAlert.create()


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

    private fun navigateToKelolaToko() {
        binding.menuKelolatoko.setOnClickListener{
            findNavController().navigate(R.id.action_akunWarga_to_kelolaToko)
        }
    }

    private fun navigateToEditProfile() {
        binding.menuEditprofile.setOnClickListener{
            findNavController().navigate(R.id.action_akunWarga_to_editProfile)
        }
    }

    private fun bindingView(): View {
        binding = FragmentAkunWargaBinding.inflate(layoutInflater)
        return binding.root
    }


}