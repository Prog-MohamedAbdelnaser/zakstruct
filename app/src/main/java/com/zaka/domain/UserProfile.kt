package com.zaka.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(

	@field:SerializedName("mail")
	val mail: String? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("jobTitle")
	val jobTitle: String? = null,

	@field:SerializedName("samAccountName")
	val samAccountName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("compString")
	val compString: String? = null,

	@field:SerializedName("sn")
	val sn: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("department")
	val department: String? = null,

	@field:SerializedName("userPrincipalName")
	val userPrincipalName: String? = null,

	@field:SerializedName("lastLogon")
	val lastLogon: String? = null,

	@field:SerializedName("mailNickname")
	val mailNickname: String? = null,

	@field:SerializedName("info")
	val info: String? = null,

	@field:SerializedName("manager")
	val manager: String? = null,

	@field:SerializedName("givenName")
	val givenName: String? = null,

	@field:SerializedName("managerPath")
	val managerPath: String? = null,

	@field:SerializedName("departmentAr")
	val departmentAr: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("employeeId")
	val employeeId: String? = null,

	@field:SerializedName("cn")
	val cn: String? = null,

	@field:SerializedName("displayNameAr")
	val displayNameAr: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("jobTitleAr")
	val jobTitleAr: String? = null,

	@field:SerializedName("businessPhone")
	val businessPhone: String? = null,

	@field:SerializedName("userType")
	val userType: String? = null
) : Parcelable
