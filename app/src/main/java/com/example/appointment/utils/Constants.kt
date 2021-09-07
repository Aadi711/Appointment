package com.example.appointment.utils

class Constants {

    companion object {

        // API AND URL
        const val SHOP_ID = "shop_id"
        const val SIZE = "size"
        const val STYLE_ID = "style_id"
        const val BASE_URL = "https://appointment.megaxtudio.com/appointment/index.php/"
        const val AUTH_URL = "Auth/"
        const val BASE_URL_IMAGES = "https://appointment.megaxtudio.com/appointment/"
        const val LOGIN_API =  AUTH_URL + "login"
        const val SIGNUP_API = AUTH_URL + "signup"
        const val SEND_EMAIL_CODE_API = AUTH_URL + "sendemail_code"
        const val SEND_OTP_CODE_API = AUTH_URL + "check_mail_code"
        const val RESET_PASSWORD_API = AUTH_URL + "reset_password"
        const val STORES_API = AUTH_URL + "stores"
        const val STORES_WITH_EMPLOYEE_API = AUTH_URL + "store_with_their_employee"
        const val USER_PROFILE_API = AUTH_URL + "user_profile"
        const val UPDATE_USER_PROFILE_API = AUTH_URL + "update_user_profile"
        const val UPDATE_USER_PROFILE_IMAGE_API = AUTH_URL + "update_profile_image"
        const val EMPLOYEE_PROFILE_API = AUTH_URL + "employee_profile"
        const val APPOINTMENT_API = AUTH_URL + "appointments"
        const val ORDER_API = AUTH_URL + "order"
        const val HELP_API = AUTH_URL + "help"
        const val FCM_API = "https://fcm.googleapis.com/fcm/send"
        const val PHONE_REGEX = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$"
        const val YES = "yes"
        const val TOPIC = "/topics/"
        const val MAP_ZOOM = 13.0f
        const val IMAGES = "Images"
        const val IMAGES_REQUIRED = "Image Required"
        const val IMAGE = "Image"
        const val IMAGE_SMALL = "image"
        const val USER = "user"
        const val POSITION = "Position"
        const val SHOW_ON_WEB = "showOnWeb"
        var SINGLE = "Single"
        const val RC_SIGN_IN = 1
        const val CLIENT_ID =
            "363591548378-gp1tj7v5qiud8sbu2i2h10u6upc1ro1l.apps.googleusercontent.com"
        const val PHONE_VALIDATION_ERROR = "Phone Pattern should be \n 'xxx-xxx-xxxx'"
        const val EMAIL_REQUIRED = "Email Required"
        const val EMAIL_VALIDATION_ERROR = "Email Pattern not match \n 'name@example.com'"
        const val COMMENT_REQUIRED = "Comment Required"
        const val COMMENT_SEND = "Comment Send Successfully"
        const val PASSWORD_REQUIRED = "Password Required"
        const val TIME_SLOT_REQUIRED = "Please Select Time Slot First"
        const val PASSWORD_NOT_MATCH_ERROR = "Password Doesn't Match"
        const val OTP_REQUIRED = "OTP Required"
        const val OTP_COMPLETE_REQUIRED = "Complete OTP Required"
        const val PASSWORD_DONT_MATCH = "Password Doesn't Match"
        const val NEW_PASSWORD_REQUIRED = "New Password Required"
        const val OLD_PASSWORD_REQUIRED = "Old Password Required"
        const val USERNAME_REQUIRED = "Username Required"
        const val NATIONALITY_REQUIRED = "Nationality Required"
        const val SHOP_NAME_REQUIRED = "Shop Name Required"
        const val STYLE_NAME_REQUIRED = "Style Name Required"
        const val STYLE_DESCRIPTION_REQUIRED = "Style Description Required"
        const val STYLE_DESCRIPTION_FIRST_CHAR_ERROR = "Description should not start from digit or integer "
        const val RATING_REQUIRED = "Rating Required"
        const val WRONG_USER_ERROR = "Wrong user"
        const val RATING_SUCCESS = "Rating given Successfully"
        const val ACCOUNT_CREATED = "Account Created Successfully"
        const val USER_SIGN_SUCCESSFULLY = "User Sign-in Successfully"
        const val USER_NOTIFICATION = "commented on your hairstyle"
        const val EMAIL_SEND_SUCCESSFULLY = "Email send successfully"
        /*
     * SHARED PREFERENCES CONSTANTS
     */
        const val USER_TAG_SP = "USER_TAG_SP"

        /*
     * MODEL CONSTANTS
     *
     /
     */
        const val USER_TAG_MODEL = "USER_TAG_MODEL"

        // REQUEST CODES
        const val MULTIPLE = "Multiple"
        const val IMAGES_LIMIT = 10
        const val STYLES_IMAGES_LIMIT = 5
        const val REQUEST_CODE_CAMERA = 101
        const val REQUEST_CODE_GALLERY = 100
        const val REQUEST_CODE_INTENT = 3
        const val REQUEST_CODE_LOCATION = 100
        const val LOCATION = "location"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val NO_PLUS_ICON = "noplusicon"
        const val STORES = "stores"

        // API's PARAMETERS
        const val PREFERENCE_NAME = "pref1"
        const val USERNAME = "name"
        const val DESCRIPTION = "description"
        const val DESCRIPTION_REQUIRED = "Description Required"
        const val SHOP_NAME = "shop_name"
        const val BLOG = "blog"
        const val ALL_SHOPS = "All Shops"
        const val NEAREST = "Nearest"
        const val ACTIVITY_NAME = "activity_name"
        const val ADDRESS = "address"
        const val PHOTOS = "photos"
        const val DAYS = "days"
        const val OTP = "code"
        const val STATUS = "status"
        const val CANCELLED = "cancelled"
        const val CONFIRMED = "confirmed"
        const val COMPLETED = "completed"
        const val PENDING = "pending"
        const val PHONE = "phone"
        const val PHONE_REQUIRED = "Phone Required"
        const val LAT = "lat"
        const val LONG = "long"
        const val EMAIL = "email"
        const val STORE_ID = "store_id"
        const val NEW_PASSWORD = "new_password"
        const val CODE = "code"
        const val NATIONALITY = "nationality"
        const val USER_TYPE = "user_type"
        const val TOKEN = "token"
        const val TOKEN_CAPITAL = "Token"
        const val PASSWORD = "password"
        const val OLD_PASSWORD = "old_password"
        const val TYPE = "type"
        const val ID = "id"
        const val EMPLOYEE_ID = "employee_id"
        const val EMPLOYEE_NAME = "employee_name"
        const val DATE = "date"
        const val TIME = "time"
        const val DATE_REQUIRED = "Please Select Date From Calendar"
        const val APPOINTMENT_DETAIL = "appointment_detail"
        const val EMPLOYEE = "employee"
        const val BOOKED = "booked"
        const val TODAY = "today"
        const val PREVIOUS = "previous"
        const val UPCOMING = "up coming"
        const val USER_ID = "user_id"
        const val COMMENT = "comment"
        const val RATING = "rating"
        const val OPEN = "open"
        const val CLOSE = "close"
        const val CLOSED = "closed"
        const val ZERO_TIMING = "00:00"
        const val GALLERY = "Gallery"
        const val CAMERA = "Camera"

        // HEADERS
        const val CLIENT_SERVICE_HEADER = "ClientService: "
        const val CLIENT_SERVICE_HEADER_FOR_FAN = "ClientService"
        const val CLIENT_SERVICE = "ListC@rlIsT(;)*&-#\$%"
        const val AUTH_KEY = "#\$%%^^S@fTy!@).(@^S@fTy!@).(@"
        const val AUTH_KEY_HEADER = "AuthKey: "
        const val AUTH_KEY_HEADER_FOR_FAN = "AuthKey"
        const val CONTENT_TYPE = "application/x-www-form-urlencoded"
      /*  const val SERVER_KEY =
            "key=" + "AAAAVKe-vdo:APA91bGucn7JzTPdvwQymzwSHB4e3VH1ILFSGwWse1ex98UeLWs-Pzv2swxuND99boump0Fnp4hu-MR0NTQ33xbsFVX8-TmUJsCUR-KsOvMYiaTe-4bdwo5QPpp87crxw81BUtoS5lz8"
*/
        const val SERVER_KEY =
            "key=" + "AAAAlRggi20:APA91bF9Xo7TfrfkShyejNMPl6meCMvPAMJSpuIN7vB5X8dWr5U9h67altPaUOFyyMVGZ0dkLVAsD5mJXXErKx-cEZ8wL4lwF7zyh35aQwpn6aYaNAkJuUkBpWoS_mrTVIop60L8HfDN"
        const val CONTENT_TYPE_JSON = "application/json"
        const val CONTENT_TYPE_HEADER = "Content-Type: "
        const val CONTENT_TYPE_HEADER_FOR_FAN = "Content-Type"
        const val AUTHORIZATION = "Authorization"
        const val SOCIAL_KEY_GOOGLE = "google"
        const val SOCIAL_KEY = "social_key"
        const val SOCIAL_TOKEN = "social_token"
        const val REQUEST_ERROR = "Request error"
    }
}