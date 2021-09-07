package com.example.example.appointment.activities

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.androidnetworking.AndroidNetworking
import com.gmail.samehadar.iosdialog.IOSDialog
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.example.appointment.R
import com.example.appointment.http.APIClient
import com.example.appointment.http.APIInterface
import com.example.appointment.model.ResponseModel
import com.example.appointment.notifications.Singleton
import com.example.appointment.utils.Constants
import com.example.appointment.utils.PreferencesManager
import com.example.appointment.utils.ProgressUtils
import com.example.appointment.views.CustomErrorView
import com.example.appointment.views.CustomSuccessView
import com.example.iosprogressbarforandroid.IOSProgressHUD
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.filter.Filter
import es.dmoral.toasty.Toasty
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


open class BaseActivity : AppCompatActivity() {

    private var mProgress: ProgressUtils? = null
    private var mOutputImageUri: Uri? = null
    private val TAG = "BaseActivity"
    lateinit var iosProgressHUD: IOSProgressHUD
    lateinit var base64Images: ArrayList<String>
    lateinit var base64SingleImage: String
    lateinit var iosDialog: IOSDialog
    fun initToolbar(context: Context, title: String) {
        try {
            supportActionBar!!.title = title
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            mProgress = ProgressUtils(context as Activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun initFastAndroidNetWorking(activity: Activity) {
        AndroidNetworking.initialize(activity)
    }

    open fun getApiInterface(): APIInterface {
        return APIClient.getClient()
            .create(APIInterface::class.java)
    }

    fun hideToolbar() {
        try {
            supportActionBar!!.hide()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    open fun fullScreen() {
        try {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    fun initCustomToolbar(context: Context, title: String, toolbar: Toolbar) {
        try {
            supportActionBar!!.title = title
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            setSupportActionBar(toolbar)
            mProgress = ProgressUtils(context as Activity)
        } catch (e: Exception) {
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

    fun initProgressDialog(activity: Activity?) {
        mProgress = ProgressUtils(activity!!)
    }

    fun showProgress() {
        if (mProgress != null) {
            mProgress!!.showProgress()
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
            .setSpinnerColor(ContextCompat.getColor(context, R.color.loader_spinner_color))
            .setBackgroundColor(ContextCompat.getColor(context, R.color.loader_background_color))
            .setMessageContentGravity(Gravity.END)
            .build()
        iosDialog.show()
    }

    fun hideCustomProgress() {
        if (iosDialog.isShowing) {
            iosDialog.dismiss()
        }
    }

    fun showProgress(message: String) {
        if (mProgress != null) {

            mProgress!!.showProgress(message)
        }
    }

    fun getProgressDialog(): ProgressUtils? {
        return mProgress
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

    open fun showImagePickerDialog(
        type: String?, getmListImagesFromAdapter: MutableList<String>, limit: Int
    ) {
        val choice = arrayOf<CharSequence>("Choose from Gallery", "Capture a photo")
        AlertDialog.Builder(this)
            .setSingleChoiceItems(
                choice, -1
            ) { dialog, which ->
                dialog.dismiss()
                val selectedPosition =
                    (dialog as AlertDialog).listView.checkedItemPosition
                if (selectedPosition == 1) {
                    cameraIntent(Constants.REQUEST_CODE_CAMERA, "")
                } else {
                    if (TextUtils.equals(type, Constants.SINGLE)) galleryIntentSingleImages(1)
                    if (TextUtils.equals(type, Constants.MULTIPLE)) galleryIntentMultipleImages(
                        1,
                        getmListImagesFromAdapter,
                        limit
                    )
                }
            }
            .show()
    }

    open fun cameraIntent(requestCode: Int, type: String?) {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(this.packageManager) != null) {
                val values = ContentValues(1)
                //                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                mOutputImageUri = this.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputImageUri)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                (this as Activity).startActivityForResult(intent, requestCode)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
            }
        } catch (ex: java.lang.Exception) {
            com.example.appointment.utils.NotificationUtils.DeveloperToast(
                this, """
     Select Image Exp
     ${ex.message}
     """.trimIndent()
            )
        }
    }

    fun galleryIntentSingleImages(requestCode: Int) {
        Matisse.from(this@BaseActivity)
            .choose(MimeType.ofAll())
            .countable(true)
            .maxSelectable(1)
            .addFilter(
                com.example.appointment.utils.GifSizeFilter(
                    320,
                    320,
                    5 * Filter.K * Filter.K
                )
            )
            .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(com.example.appointment.utils.MyGlideEngine())
            .forResult(requestCode)
    }

    fun galleryIntentMultipleImages(
        requestCode: Int,
        getmListImagesFromAdapter: MutableList<String>,
        limit: Int
    ) {
//        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(Intent.createChooser(pickPhoto, "Select File"), requestCode);
        /*  val mIntent = Intent(this, PickImageActivity::class.java)
          mIntent.putExtra(PickImageActivity.KEY_LIMIT_MAX_IMAGE, 10)
          mIntent.putExtra(PickImageActivity.KEY_LIMIT_MIN_IMAGE, 1)
          (this as Activity).startActivityForResult(mIntent, PickImageActivity.PICKER_REQUEST_CODE)*/

        /* val options = Options.init()
             .setRequestCode(100) //Request code for activity results
             .setCount(10) //Number of images to restrict selection count
             .setFrontfacing(false) //Front Facing camera on start
             .setSpanCount(4) //Span count for gallery min 1 & max 5
             .setMode(Options.Mode.All) //Option to select only pictures or videos or both
             .setVideoDurationLimitinSeconds(30) //Duration for video recording
             .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT) //Orientation
             .setPath("/pix/images") //Custom Path For media Storage

         Pix.start(this@BaseActivity, options)
 */

        when (getmListImagesFromAdapter.size) {
            0 -> {


                Matisse.from(this@BaseActivity)
                    .choose(MimeType.ofAll())
                    .countable(true)
                    .maxSelectable(limit)
                    .addFilter(
                        com.example.appointment.utils.GifSizeFilter(
                            320,
                            320,
                            5 * Filter.K * Filter.K
                        )
                    )
                    .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(com.example.appointment.utils.MyGlideEngine())
                    .forResult(Constants.REQUEST_CODE_GALLERY)

                /*ImagePicker.Builder(this)
                    .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                    .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                    .
                    .directory(ImagePicker.Directory.DEFAULT)
                    .extension(ImagePicker.Extension.JPG)
                    .allowMultipleImages(true)
                    .enableDebuggingMode(true)
                    .build();*/
            }
            Constants.IMAGES_LIMIT -> {
                Matisse.from(this@BaseActivity)
                    .choose(MimeType.ofAll())
                    .countable(true)
                    .maxSelectable(0)
                    .addFilter(
                        com.example.appointment.utils.GifSizeFilter(
                            320,
                            320,
                            5 * Filter.K * Filter.K
                        )
                    )
                    .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(com.example.appointment.utils.MyGlideEngine())
                    .forResult(Constants.REQUEST_CODE_GALLERY)
            }
            else -> {
                Matisse.from(this@BaseActivity)
                    .choose(MimeType.ofAll())
                    .countable(true)
                    .maxSelectable(limit - getmListImagesFromAdapter.size)
                    .addFilter(
                        com.example.appointment.utils.GifSizeFilter(
                            320,
                            320,
                            5 * Filter.K * Filter.K
                        )
                    )
                    .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(com.example.appointment.utils.MyGlideEngine())
                    .forResult(Constants.REQUEST_CODE_GALLERY)
            }
        }
    }

    open fun getOutputImageUri(): Uri? {
        return mOutputImageUri
    }

    fun isResponseSuccess(
        response: Response<ResponseModel>,
        context: Context,
        showToast: Boolean
    ): Boolean {
        return if (response.isSuccessful) {
            if (response.body() != null) {
                if (!TextUtils.isEmpty(response.body()!!.foundMessage)) {
                    if (showToast) Toasty.success(
                        context,
                        response.body()!!.foundMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                    response.body()!!.success
                } else {
                    response.body()!!.success
                }
            } else {
                Toasty.error(context, "Server response is null", Toast.LENGTH_SHORT).show()
                false
            }
        } else {
            Toasty.error(context, "Server Response is not successful", Toast.LENGTH_SHORT).show()
            false
        }
    }

    fun getResponseData(response: ResponseModel?, context: Context?): Any? {
        if (response != null) {
            if (response.success) {
                if (response.data != null) {
                    return response.data
                }
            } else if (!response.success) {
                Toasty.error(context!!, response.foundMessage, Toasty.LENGTH_SHORT).show()

            }
        } else {
            Toasty.error(context!!, "Response is not successful", Toasty.LENGTH_SHORT).show()
        }
        return null
    }

    fun getResponseData(
        response: ResponseModel?,
        context: Context?,
        layout: LinearLayout
    ): Any? {
        if (response != null) {
            if (response.success) {
                if (response.data != null) {
                    return response.data
                }
            } else if (!response.success) {
                val customErrorView = object : CustomErrorView(context!!, response.foundMessage) {

                }
                layout.removeAllViews()
                layout.addView(customErrorView.getView())
                android.os.Handler().postDelayed({
                    layout.removeAllViews()

                }, 2000)
            }
        } else {
            val customErrorView = object : CustomErrorView(context!!, "response not successful") {

            }
            layout.removeAllViews()
            layout.addView(customErrorView.getView())
            android.os.Handler().postDelayed({
                layout.removeAllViews()

            }, 2000)
        }
        return null
    }

    fun getFastAndroidNetworkingResponseData(
        response: ResponseModel?,
        context: Context?
    ): Any? {
        if (response != null) {
            if (response.success) {
                if (response.data != null) {
                    return response.data
                }
            } else if (!response.success) {
                Toasty.error(context!!, response.foundMessage, Toasty.LENGTH_SHORT).show()

            }
        } else {
            Toasty.error(context!!, "Response is not successful", Toasty.LENGTH_SHORT).show()
        }
        return null
    }

    fun convertImagesToBase64(imagesList: List<String>): List<String> {
        showCustomProgress(this)
        base64Images = ArrayList()

        imagesList.forEach {
            /* val imageStream: InputStream
             try {
                 imageStream = this.contentResolver.openInputStream(it.getUri())
                 val selectedImage = BitmapFactory.decodeStream(imageStream)
                 base64Images.add(encodeImage(selectedImage))
             } catch (e: FileNotFoundException) {
                 e.printStackTrace()
             }*/
            try {
                val bm = BitmapFactory.decodeFile(it)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bm.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                base64Images.add(encodeToString(byteArray, DEFAULT))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        hideCustomProgress()
        return base64Images
    }

    fun convertSingleImageToBase64(image: String): String {

        val bm = BitmapFactory.decodeFile(image)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos) // bm is the bitmap object

        val b: ByteArray = baos.toByteArray()
        val imageString: String = encodeToString(b, DEFAULT)
        base64SingleImage = "image/jpeg;base64,$imageString"


        return base64SingleImage
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

    fun firebaseSubScribeTopic(userId: Int) {
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC + userId)
    }
    fun firebaseUnSubScribeTopic(userId: Int) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC + userId)
    }

    fun addStyleIdListInPreferences(id: Int, list: ArrayList<Int>) {
        PreferencesManager.write(id.toString(), Gson().toJson(list))

    }

    fun createNotification(
        context: Context,
        userId: Long,
        title: String,
        message: String,
        layout: LinearLayout
    ) {
        val TOPIC =
            Constants.TOPIC + userId //topic must match with what the receiver subscribed to
        val NOTIFICATION_TITLE = title
        val NOTIFICATION_MESSAGE = message

        val notification =
            createJSONData(context, TOPIC, NOTIFICATION_TITLE, NOTIFICATION_MESSAGE, layout)
        sendNotification(context, layout, notification)
    }

    private fun createJSONData(
        context: Context,
        TOPIC: String,
        NOTIFICATION_TITLE: String,
        NOTIFICATION_MESSAGE: String,
        layout: LinearLayout
    ): JSONObject {
        val notification = JSONObject()
        val notificationBody = JSONObject()
        try {
            notificationBody.put("title", NOTIFICATION_TITLE)
            notificationBody.put("message", NOTIFICATION_MESSAGE)
            notificationBody.put("image", R.mipmap.ic_launcher)
            notification.put("to", TOPIC)
            notification.put("data", notificationBody)
        } catch (e: JSONException) {
            errorView(context, layout, e.message.toString())
        }
        return notification
    }

    private fun sendNotification(context: Context, layout: LinearLayout, notification: JSONObject) {

        val jsonObjectRequest: JsonObjectRequest =
            object : JsonObjectRequest(Constants.FCM_API, notification,
                { response ->

                        Log.d(TAG, "onResponse: $response")


                },
                {
                    errorView(context, layout, Constants.REQUEST_ERROR)
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params[Constants.AUTHORIZATION] = Constants.SERVER_KEY
                    params[Constants.CONTENT_TYPE_HEADER_FOR_FAN] = Constants.CONTENT_TYPE_JSON
                    return params
                }
            }
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
    }


    private fun getDistanceBetweenTwoPoints(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371; // Radius of the earth in km
        val dLat = deg2rad(lat2-lat1);  // deg2rad below
        val dLon = deg2rad(lon2-lon1);
        val a =
            sin(dLat/2) * sin(dLat/2) +
                    cos(deg2rad(lat1)) * cos(deg2rad(lat2)) *
                    sin(dLon/2) * sin(dLon/2)
        ;
        val c = 2 * atan2(sqrt(a), sqrt(1-a));
        val d = R * c; // Distance in km
        return d;
    }


    private  fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    private  fun rad2deg(rad: Double): Double {
        return rad * 180.0 / Math.PI
    }
    fun convertKmsToMiles(kms: Float): Float {
        return (0.621371 * kms).toFloat()
    }

    fun getTimeFromStringTime(time: String?, format: String?): Date? {
        if (time == null || TextUtils.isEmpty(time)) return null
        try {
            val formatter = SimpleDateFormat(format)
            return formatter.parse(time)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

}