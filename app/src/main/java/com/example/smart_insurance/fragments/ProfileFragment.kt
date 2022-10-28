package com.example.smart_insurance.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.smart_insurance.R
import com.example.smart_insurance.views.ConfigureGroup
import com.example.smart_insurance.views.EditProfile
import com.example.smart_insurance.databinding.FragmentProfileBinding
import com.example.smart_insurance.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ProfileFragment(private val user: User)  : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var listener: OnItemClickListener? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.textView6.text = user.name + " " + user.lassName

        Firebase.storage.reference.child("profileImages").child(user.profileImage!!).downloadUrl.addOnSuccessListener {
            Glide.with(binding.imageView3).load(it).into(binding.imageView3)
        }.addOnFailureListener{
            binding.imageView3.setImageResource(R.drawable.handsomesquid)
        }


        binding.button5.setOnClickListener {
            val intent = Intent(this.context, EditProfile::class.java)
            startActivity(intent)
        }

        binding.button7.setOnClickListener {
            val intent = Intent(this.context, ConfigureGroup::class.java)
            startActivity(intent)
        }

        binding.button13.setOnClickListener {
            listener?.logOut()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) = ProfileFragment(user)
    }

    interface OnItemClickListener{
        fun logOut()
    }

    fun setListener(listener: OnItemClickListener){
        this.listener = listener
    }
}