package com.example.appointment.fragments

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.gmail.samehadar.iosdialog.IOSDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.example.appointment.R
import com.example.appointment.model.ResponseModel
import com.example.appointment.utils.Constants
import com.example.appointment.utils.ProgressUtils
import com.example.appointment.views.CustomErrorView
import com.example.appointment.views.CustomSuccessView
import es.dmoral.toasty.Toasty
import java.io.ByteArrayOutputStream
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

open class BaseFragment : Fragment() {
    private var mProgress: ProgressUtils? = null
    lateinit var base64Images: ArrayList<String>

    lateinit var base64DrawableImage: String
    lateinit var base64SingleImage: String
    lateinit var iosDialog: IOSDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }


    fun initProgressDialog(context: Context) {
        mProgress = ProgressUtils(context)
    }


    fun initFastAndroidNetWorking(activity: Activity) {
        AndroidNetworking.initialize(activity)
    }

    fun showProgress() {
        if (mProgress != null) {
            mProgress!!.showProgress()
        }
    }

    fun showProgress(message: String) {
        if (mProgress != null) {

            mProgress!!.showProgress(message)
        }
    }

    fun showCustomProgress(context: Context) {
        /*iosProgressHUD = IOSProgressHUD.create(context)
            .setStyle(IOSProgressHUD.Style.SPIN_INDETERMINATE)
            .setLabel("Please wait")
            .setCancellable(false)
            .setAnimationSpeed(2)
            .setDimAmount(0.5f)
            .setWindowColor(ContextCompat.getColor(context,R.color.loader_color))

            .show()*/
        /*val spinner3 = findViewById<View>(R.id.spinner3) as CamomileSpinner
        spinner3.start()
        spinner3.recreateWithParams(
            this@BaseActivity,
            DialogUtils.getColor(this, R.color.loader_color),
            120,
            true
        )*/
        iosDialog = IOSDialog.Builder(context)
            .setDimAmount(3f)
            .setSpinnerColorRes(R.color.loader_spinner_color)
            .setMessageColorRes(R.color.black)
            .setTitleColorRes(R.color.black)
            .setMessageContent("Please Wait...")
            .setCancelable(false)
            .setBackgroundColor(ContextCompat.getColor(context, R.color.loader_background_color))
            .setMessageContentGravity(Gravity.END)
            .build()
        iosDialog.show()
    }

    fun hideCustomProgress() {
        if (iosDialog.isShowing && iosDialog != null) {
            iosDialog.dismiss()
        }
    }

    fun getProgressDialog(): ProgressUtils? {
        return mProgress
    }

    open fun getApiInterface(): com.example.appointment.http.APIInterface {
        return com.example.appointment.http.APIClient.getClient()
            .create(com.example.appointment.http.APIInterface::class.java)
    }

    fun hideProgress() {
        try {
            if (mProgress != null) {
                mProgress!!.hideProgress()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun initRecyclerView(recyclerView: RecyclerView, context: Context?) {
        val manager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
    }

    fun initGridRecyclerView(recyclerView: RecyclerView, context: Context?, columns: Int) {
        val manager = GridLayoutManager(context, columns)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = manager
    }

    fun convertDrawableImageToBase64(image: Int): String {

        val bm = BitmapFactory.decodeResource(context?.resources, image)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos) // bm is the bitmap object

        val b: ByteArray = baos.toByteArray()
        val imageString: String = Base64.encodeToString(b, Base64.DEFAULT)
        base64DrawableImage = "image/jpeg;base64,$imageString"


        return base64DrawableImage
    }

    fun convertSingleImageToBase64(image: String): String {

        val bm = BitmapFactory.decodeFile(image)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos) // bm is the bitmap object

        val b: ByteArray = baos.toByteArray()
        val imageString: String = Base64.encodeToString(b, Base64.DEFAULT)
        base64SingleImage = "image/jpeg;base64,$imageString"


        return base64SingleImage
    }

    fun getResponseData(response: ResponseModel?, context: Context?): Any? {
        if (response != null) {
            if (response.success) {
                if (response.data != null) {
                    return response.data
                }
            } else if (!response.success) {

                if (context != null) {
                    Toasty.error(context, response.foundMessage, Toasty.LENGTH_SHORT)
                        .show()
                }
            }
        } else {

            if (context != null) {
                Toasty.error(context, "Response is not successful", Toasty.LENGTH_SHORT).show()
            }

        }
        return null
    }

    fun errorView(context: Context, layout: LinearLayout, message: String) {
        val customErrorView =
            object : CustomErrorView(context, message) {

            }
        layout.removeAllViews()
        layout.addView(customErrorView.getView())
        android.os.Handler().postDelayed({
            layout.removeAllViews()

        }, 2000)
    }

    fun successView(context: Context, layout: LinearLayout, message: String) {
        val customSuccessView =
            object : CustomSuccessView(context, message) {

            }
        layout.removeAllViews()
        layout.addView(customSuccessView.getView())
        android.os.Handler().postDelayed({
            layout.removeAllViews()

        }, 2000)
    }

    fun getDistanceBetweenTwoPoints(
        lat1: Double?,
        lon1: Double?,
        lat2: Double,
        lon2: Double
    ): Double {
        val R = 6371; // Radius of the earth in km
        val dLat = deg2rad(lat2 - lat1!!);  // deg2rad below
        val dLon = deg2rad(lon2 - lon1!!);
        val a =
            sin(dLat / 2) * sin(dLat / 2) +
                    cos(deg2rad(lat1)) * cos(deg2rad(lat2)) *
                    sin(dLon / 2) * sin(dLon / 2)
        ;
        val c = 2 * atan2(sqrt(a), sqrt(1 - a));
        val d = R * c; // Distance in km
        return d;
    }


    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }

    fun convertKmsToMiles(kms: Double): Double {
        return (0.621371 * kms)
    }

    fun firebaseSubScribeTopic(userId: Int) {
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC + userId)
    }

    fun firebaseUnSubScribeTopic(userId: Int) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC + userId)
    }

}