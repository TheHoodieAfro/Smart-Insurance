package com.example.smart_insurance.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import com.bumptech.glide.Glide
import com.example.smart_insurance.R
import com.example.smart_insurance.databinding.ActivityEditProfileBinding
import com.example.smart_insurance.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import java.io.File
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia

class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var user: User
    private lateinit var file: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = loadUser()

        binding.imageView5.setImageResource(R.drawable.addimg)

        binding.imageButton3.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.imageView5.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))

        }

    }

    private val pickMedia = registerForActivityResult(PickVisualMedia()){

        if (it != null){

            Firebase.storage.reference.child("profileImages").child(user.id)
                .putFile(it).addOnSuccessListener {
                    Firebase.storage.reference.child("profileImages").child(user.id)
                        .downloadUrl.addOnSuccessListener { image ->
                            Firebase.firestore.collection("users").document(user.id).update("profileImage", image.toString())
                            Glide.with(binding.imageView5).load(image).into(binding.imageView5)



                        }
                }

        }

    }

    private fun loadUser(): User {
        val sp = getSharedPreferences("smart_insurance", MODE_PRIVATE)
        val json = sp.getString("user", "NO_USER")
        return Gson().fromJson(json, User::class.java)

    }


}