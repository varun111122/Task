package com.chiragtask.data.network

import android.os.Parcel
import android.os.Parcelable


open class ApiError() : Parcelable {
    var status: Boolean = false
    var message: String? = null
    var result: Any? = null
    var data: Any? = null
    var statusCode: Int? = null
    var exception: Throwable? = null

    constructor(parcel: Parcel) : this() {
        status = parcel.readByte() != 0.toByte()
        message = parcel.readString()
        statusCode = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (status) 1 else 0)
        parcel.writeString(message)
        parcel.writeValue(statusCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApiError> {
        override fun createFromParcel(parcel: Parcel): ApiError {
            return ApiError(parcel)
        }

        override fun newArray(size: Int): Array<ApiError?> {
            return arrayOfNulls(size)
        }
    }
}

data class ToastData(
    var errorCode: Int? = null,
    var errorString: String? = null
)
