package com.zaka.data.repositories

import com.zaka.data.repositories.RepositoriesConstants.KEY_PREFRENCE_TOKEN
import com.zaka.data.repositories.RepositoriesConstants.KEY_PREFRENCE_USER
import com.zaka.data.sources.local.AppPreference
import com.zaka.domain.User

// Created by mohamed abdelnaser on 7/4/19.
class UserRepository (private val appPreference: AppPreference){

    fun getLogedInUser(): User?=
        if (appPreference.getObject(KEY_PREFRENCE_USER, User::class.java) != null)
            appPreference.getObject(KEY_PREFRENCE_USER, User::class.java)
         else null


    fun isLoged():Boolean = getLogedInUser()!=null && !getLogedInUser()!!.token.isNullOrEmpty()

    fun saveUserData(user: User){
        appPreference.saveObject(KEY_PREFRENCE_USER,user)
        appPreference.putString(KEY_PREFRENCE_TOKEN, user.token.toString())
    }





    fun clear(){
        appPreference.saveObject(KEY_PREFRENCE_USER,null)
    }
}