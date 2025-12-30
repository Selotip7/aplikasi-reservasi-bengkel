package com.my.projekakhir.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.my.projekakhir.R
import com.my.projekakhir.UserPref
import com.my.projekakhir.viewModel.UserViewModel

class ProfilFragment : Fragment(R.layout.fragment_profil) {

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //data
        val tvNama = view.findViewById<TextView>(R.id.tvNama)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val tvNoHp = view.findViewById<TextView>(R.id.tvNoHp)

        //edit
        val ivEditNama = view.findViewById<ImageView>(R.id.ivEditNama)
        val ivEditEmail = view.findViewById<ImageView>(R.id.ivEditEmail)
        val ivEditNoHp = view.findViewById<ImageView>(R.id.ivEditNoHp)

        //SharedPreferences
        val userPref = UserPref(requireContext())

        // Load data dari SharedPreferences
        val savedName = userPref.getName()
        val savedEmail = userPref.getEmail()
        val savedNoHp = userPref.getNoHp()

        Log.d("UserPrefDebug", "Email: ${savedEmail}, NoHp: ${savedNoHp}")


        userViewModel.nama.value = savedName
        userViewModel.email.value = savedEmail
        userViewModel.noHp.value = savedNoHp

        // Observe LiveData supaya UI otomatis update
        userViewModel.nama.observe(viewLifecycleOwner) { newName ->
            tvNama.text = "Nama: $newName"
        }
        userViewModel.email.observe(viewLifecycleOwner) { newEmail ->
            tvEmail.text = "Email: $newEmail"
        }
        userViewModel.noHp.observe(viewLifecycleOwner) { newNoHp ->
            tvNoHp.text = "No Hp: $newNoHp"
        }

        // Listener untuk edit data
        ivEditNama.setOnClickListener {
            showEditDialog("Nama", userViewModel.nama.value ?: "") { namaBaru ->
                userViewModel.nama.value = namaBaru
                userPref.saveName(namaBaru)
            }
        }

        ivEditEmail.setOnClickListener {
            showEditDialog("Email", userViewModel.email.value ?: "") { emailBaru ->
                userViewModel.email.value = emailBaru
                userPref.saveEmail(emailBaru)
            }
        }

        ivEditNoHp.setOnClickListener {
            showEditDialog("No Hp", userViewModel.noHp.value ?: "") { noBaru ->
                userViewModel.noHp.value = noBaru
                userPref.saveNoHp(noBaru)
            }
        }
    }


    private fun showEditDialog(title: String, currentValue: String, onSave: (String) -> Unit) {
        val editText = EditText(requireContext())
        editText.setText(currentValue)

        AlertDialog.Builder(requireContext())
            .setTitle("Edit $title")
            .setView(editText)
            .setPositiveButton("Simpan") { _, _ ->
                val newValue = editText.text.toString()
                onSave(newValue)
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
