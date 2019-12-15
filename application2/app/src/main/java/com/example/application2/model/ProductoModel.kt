package com.example.application2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ProductoModel(@SerializedName("prod_desc") var description: String?,
                    @SerializedName("prod_titulo") var title: String?,
                    @SerializedName("prod_precio") var price: String?,
                    @SerializedName("prod_img") var image: String?,
                    @SerializedName("prod_id") var id: String?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(title)
        parcel.writeString(price)
        parcel.writeString(image)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductoModel> {
        override fun createFromParcel(parcel: Parcel): ProductoModel {
            return ProductoModel(parcel)
        }

        override fun newArray(size: Int): Array<ProductoModel?> {
            return arrayOfNulls(size)
        }
    }
}

