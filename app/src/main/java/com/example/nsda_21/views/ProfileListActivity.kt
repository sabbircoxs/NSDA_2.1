package com.example.nsda_21.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nsda_21.R
import com.example.nsda_21.adapter.ProfileAdapter
import com.example.nsda_21.model.UserProfile
import com.example.nsda_21.viewModel.UserProfileViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class   ProfileListActivity : AppCompatActivity() {

    private lateinit var profileViewModel: UserProfileViewModel
    private lateinit var profileAdapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_list)

        profileViewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.profileRecyclerView)
        profileAdapter = ProfileAdapter()

        recyclerView.adapter = profileAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe profiles from ViewModel
        profileViewModel.getUserProfiles().observe(this, Observer { profiles ->
            if (profiles.isEmpty()) {
                // Show a Toast message if there are no profiles
                Toast.makeText(this, "No profiles available", Toast.LENGTH_SHORT).show()
            } else {
                profileAdapter.submitList(profiles)
            }
        })

        // Set item click listener for details
        profileAdapter.setOnItemClickListener { userProfile ->
            val intent = Intent(this@ProfileListActivity, SingleProfileActivity::class.java)
            intent.putExtra("USER_PROFILE", userProfile)
            startActivity(intent)
        }

        // Set delete click listener
        profileAdapter.setOnDeleteClickListener { userProfile ->
            showDeleteConfirmationDialog(userProfile)
        }

        // Set update click listener
        profileAdapter.setOnUpdateClickListener { userProfile ->
            val intent = Intent(this@ProfileListActivity, UpdateProfileActivity::class.java)
            intent.putExtra("USER_PROFILE", userProfile)
            startActivity(intent)
        }

        // Add profile button click listener
        findViewById<FloatingActionButton>(R.id.addProfileBtn).setOnClickListener {
            val intent = Intent(this@ProfileListActivity, AddProfileActivity::class.java)
            startActivity(intent)
        }
    }

    // Show delete confirmation dialog
    private fun showDeleteConfirmationDialog(userProfile: UserProfile) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Profile")
        builder.setMessage("Are you sure you want to delete this profile?")

        builder.setPositiveButton("Yes") { dialog, which ->
            profileViewModel.deleteUserProfile(userProfile)  // Delete the profile
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()  // Cancel delete operation
        }

        val dialog = builder.create()
        dialog.show()
    }
}