package com.example.appointment.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.example.appointment.R
import com.example.appointment.databinding.ActivityPersonalDetailsBinding
import com.example.appointment.model.Employee
import com.example.appointment.model.Profile
import com.example.appointment.model.ResponseModel
import com.example.appointment.model.User
import com.example.appointment.utils.*
import com.example.example.appointment.activities.BaseActivity
import com.google.gson.Gson
import com.zhihu.matisse.Matisse
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalDetailsActivity : BaseActivity(), ItemClickListener {
    val context = this@PersonalDetailsActivity
    lateinit var binding: ActivityPersonalDetailsBinding
    lateinit var user: User
    lateinit var customBottomSheetDialogFragment: CustomBottomSheetDialogFragment
    var imagesList: ArrayList<Uri> = ArrayList()
    var stringImagesList: ArrayList<String> = ArrayList()
    private val TAG = "PersonalDetailsActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        binding.customToolbar.toolbarText.text = "Personal Details"
        binding.customToolbar.backArrowLayout.setOnClickListener {
            onBackPressed()
        }
        checkRunTimePermissions()
        getUserProfile()
        binding.swipeRefreshLayout.setOnRefreshListener {
            getUserProfile()
        }
        binding.updateButton.setOnClickListener {
            onButtonClick()
        }
        binding.imageLayout.setOnClickListener {
            customBottomSheetDialogFragment = BottomSheetAction.newInstance()
            customBottomSheetDialogFragment.show(supportFragmentManager, BottomSheetAction.TAG)
        }
    }

    private fun onButtonClick() {
        val name = binding.nameText.text.toString()
        val email = binding.emailText.text.toString()
        val phone = binding.phoneText.text.toString()
        if (TextUtils.isEmpty(name)) {
            errorView(context, binding.errorLayout, Constants.USERNAME_REQUIRED)
            return
        }
        if (TextUtils.isEmpty(email)) {
            errorView(context, binding.errorLayout, Constants.EMAIL_REQUIRED)
            return
        }
        if (TextUtils.isEmpty(phone)) {
            errorView(context, binding.errorLayout, Constants.PHONE_REQUIRED)
            return
        }
        if (stringImagesList.size == 0) {
            errorView(context, binding.errorLayout, Constants.IMAGES_REQUIRED)
            return
        }
        updateUserProfile(name, email, phone)
    }

    private fun updateUserProfile(name: String, email: String, phone: String) {
        showCustomProgress(context)
        val call =
            getApiInterface().updateUserProfile(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                Common.getUserFromSharedPreferences(context).id.toString(),
                name,
                phone,
                email
            )

        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(
                call: Call<ResponseModel>,
                t: Throwable
            ) {
                hideCustomProgress()
                errorView(context!!, binding.errorLayout, t.message.toString())
            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                hideCustomProgress()
                binding.errorLayout.removeAllViews()

                if (response.body()!!.success) {
                    successView(context, binding.errorLayout, response.body()!!.foundMessage)
                    getUserProfile()
                } else {
                    errorView(context, binding.errorLayout, response.body()!!.foundMessage)

                }
            }
        })
    }

    private fun getUserProfile() {
        binding.swipeRefreshLayout.isRefreshing = true
        val call =
            getApiInterface().userProfile(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                Common.getUserFromSharedPreferences(context).id.toString()
            )

        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(
                call: Call<ResponseModel>,
                t: Throwable
            ) {
                binding.swipeRefreshLayout.isRefreshing = false
                errorView(context!!, binding.errorLayout, t.message.toString())
            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                binding.swipeRefreshLayout.isRefreshing = false
                binding.errorLayout.removeAllViews()

                val data = getResponseData(response.body(), context, binding.errorLayout)
                if (data != null) {
                    try {
                        val gson = Gson()
                        val json = gson.toJson(data)

                        user =
                            gson.fromJson(json, User::class.java)
                        if (user != null) {
                            setUserProfile(user)
                        }
                    } catch (e: Exception) {
                        binding.swipeRefreshLayout.isRefreshing = false

                        e.printStackTrace()
                    }
                }
            }
        })
    }


    companion object {
        fun getIntent(context: Context?): Intent {
            return Intent(context, PersonalDetailsActivity::class.java)
        }
    }

    private fun setUserProfile(user: User) {
        stringImagesList.clear()
        stringImagesList.add(user.image)
        Glide.with(context).load(user.image).into(binding.userImage)
        binding.emailText.setText(user.email)
        binding.phoneText.setText(user.phoneNumber)
        binding.nameText.setText(user.name)
    }

    private fun checkRunTimePermissions() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED

            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED

            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED

            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_MEDIA_LOCATION
            ) != PackageManager.PERMISSION_GRANTED

        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_MEDIA_LOCATION
                ),
                Constants.REQUEST_CODE_LOCATION
            )
            return
        }
    }

    override fun onItemClick(item: String?) {
        if (item.equals(Constants.GALLERY)) {
            galleryIntentSingleImages(Constants.REQUEST_CODE_GALLERY)
        }
        if (item.equals(Constants.CAMERA)) {
            cameraIntent(Constants.REQUEST_CODE_CAMERA, "")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE_GALLERY) {

            imagesList.clear()
            stringImagesList.clear()
            imagesList = Matisse.obtainResult(data) as ArrayList<Uri>

            imagesList.forEach {
                stringImagesList.add(ImageFilePath.getPath(context, it))
            }
            setImage()
        }

        if (resultCode == RESULT_OK && requestCode == Constants.REQUEST_CODE_CAMERA) {
            if (getOutputImageUri() != null) {
                imagesList.clear()
                stringImagesList.clear()
                stringImagesList.add(ImageFilePath.getPath(context, getOutputImageUri()))
                setImage()
            }
        }
    }

    private fun setImage() {

        val image = convertImagesToBase64(stringImagesList)
        val user = User()
        user.image = image[0].replace("\n", "")
        user.id = Common.getUserFromSharedPreferences(context).id
        val json = Gson().toJson(user)
        Log.d(TAG, "setImage: USER $json")
        showCustomProgress(context)

        val call =
            getApiInterface().updateUserProfileImage(
                Constants.CLIENT_SERVICE,
                Constants.AUTH_KEY,
                Common.getUserFromSharedPreferences(context).id.toString(),
                image[0].replace("\n", "")
            )

        call.enqueue(object : Callback<ResponseModel> {
            override fun onFailure(
                call: Call<ResponseModel>,
                t: Throwable
            ) {
                hideCustomProgress()

            }

            override fun onResponse(
                call: Call<ResponseModel>,
                response: Response<ResponseModel>
            ) {
                hideCustomProgress()

                if (response.body()!!.success) {
                    val data = getResponseData(response.body(), context, binding.errorLayout)
                    if (data != null) {
                        try {
                            val gson = Gson()
                            val json = gson.toJson(data)

                            val user =
                                gson.fromJson(json, User::class.java)
                            if (user != null) {
                                Glide.with(context).load(user.image).placeholder(R.drawable.appointment_logo).into(binding.userImage)
                            }
                        } catch (e: Exception) {

                            e.printStackTrace()
                        }
                    }
                    successView(context, binding.errorLayout, response.body()!!.foundMessage)
                } else {
                    errorView(context, binding.errorLayout, response.body()!!.foundMessage)
                }
            }
        })
    }
}