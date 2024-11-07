package com.example.nsda_21.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nsda_21.model.UserProfile
import com.example.nsda_21.R

class SingleProfileActivity : AppCompatActivity() {
    private lateinit var userProfile: UserProfile
    private lateinit var nameText: TextView
    private lateinit var emailText: TextView
    private lateinit var dobText: TextView
    private lateinit var districtText: TextView
    private lateinit var mobileText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_profile)
        userProfile = intent.getSerializableExtra("USER_PROFILE") as UserProfile

        nameText = findViewById(R.id.nameTextView)
        emailText = findViewById(R.id.emailTextView)
        dobText = findViewById(R.id.dobTextView)
        districtText = findViewById(R.id.districtTextView)
        mobileText = findViewById(R.id.mobileTextView)

        populateFields()
    }

    private fun populateFields() {
        nameText.text = userProfile.name
        emailText.text = userProfile.email
        dobText.text = userProfile.dob
        districtText.text = userProfile.district
        mobileText.text = userProfile.mobile
    }
}