package com.zen.fintask

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zen.fintask.adapter.MainAdapter
import com.zen.fintask.databinding.ActivityMainBinding
import com.zen.fintask.helper.Helper
import com.zen.fintask.model.UserModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var email: String
    private lateinit var number: String
    private lateinit var recyclerList: ArrayList<UserModel>
    private lateinit var adapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerList = ArrayList()

        initAdapter()

        binding.submit.setOnClickListener {
            email = binding.email.text.toString().trim()
            number = binding.phone.text.toString().trim()

            if (Helper.isNullOrEmpty(email) || !Helper.validateEmail(email)) {
                binding.email.requestFocus()
                binding.email.error = "Invalid Email"
                return@setOnClickListener
            }

            if (Helper.isNullOrEmpty(number) || !Helper.validatePhone(number)) {
                binding.phone.requestFocus()
                binding.phone.error = "Invalid Phone Number"
                return@setOnClickListener
            }


            val newModel = UserModel(email, number)

            if (!Helper.checkForDuplications(recyclerList, newModel)) {
                recyclerList.add(newModel)
                Helper.pushData(recyclerList, this)
                adapter.differ.submitList(recyclerList)
                adapter.notifyDataSetChanged()
                clearViews()
            } else
                Toast.makeText(this, "Data Already Exists!", Toast.LENGTH_SHORT).show()

        }
    }

    private fun clearViews() {
        binding.email.setText("")
        binding.phone.setText("")
    }

    private fun initAdapter() {
        adapter = MainAdapter()
        recyclerList = Helper.getData(this)
        adapter.differ.submitList(recyclerList)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.setHasFixedSize(true)
        binding.recycler.adapter = adapter
    }
}