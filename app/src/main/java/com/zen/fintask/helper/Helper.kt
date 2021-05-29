package com.zen.fintask.helper

import android.content.Context
import android.content.SharedPreferences
import android.util.Patterns
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zen.fintask.model.UserModel
import java.util.ArrayList

object Helper {

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("DATABASE", Context.MODE_PRIVATE)
    }

    fun isNullOrEmpty(str: String?): Boolean {
        return null == str || str.isEmpty()
    }

    fun isNullOrEmpty(list: List<*>?): Boolean {
        return null == list || list.isEmpty()
    }


    fun pushData(modelList: ArrayList<UserModel>, context: Context) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString("Data", Gson().toJson(modelList))
        editor.apply()
    }

    fun getData(context: Context): ArrayList<UserModel> {
        val sharedPreferences = getSharedPreferences(context)
        val type = object : TypeToken<ArrayList<UserModel>?>() {}.type
        val modelList =
            Gson().fromJson<ArrayList<UserModel>>(sharedPreferences.getString("Data", ""), type)
        return if (isNullOrEmpty(modelList))
            ArrayList()
        else
            modelList
    }

    fun validateEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePhone(phone: String): Boolean {
        val regex: Regex = "(0/91)?[6-9][0-9]{9}".toRegex()
        return (Patterns.PHONE.matcher(phone).matches() && phone.matches(regex))
    }

    fun checkForDuplications(list: ArrayList<UserModel>, newModel: UserModel): Boolean {
        list.forEach { model ->
            if (model.email.equals(newModel.email, true))
                return true
            else if (model.phone.equals(newModel.phone, true))
                return true
        }
        return false
    }


}