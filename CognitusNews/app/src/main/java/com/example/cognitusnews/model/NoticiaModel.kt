package com.example.cognitusnews.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NoticiaModel (@SerializedName("not_id") @Expose val notID: String?,
                    @SerializedName("not_img") @Expose val notImg: String?,
                    @SerializedName("not_url") @Expose val notUrl: String?,
                    @SerializedName("not_titulo") @Expose val notTitulo: String?,
                    @SerializedName("not_fecha") @Expose val notFecha: String?,
                    @SerializedName("not_desc") @Expose val notDesc: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(notID)
        parcel.writeString(notImg)
        parcel.writeString(notUrl)
        parcel.writeString(notTitulo)
        parcel.writeString(notFecha)
        parcel.writeString(notDesc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoticiaModel> {
        override fun createFromParcel(parcel: Parcel): NoticiaModel {
            return NoticiaModel(parcel)
        }

        override fun newArray(size: Int): Array<NoticiaModel?> {
            return arrayOfNulls(size)
        }
    }
}

