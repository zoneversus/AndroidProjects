package com.example.cognitusnews.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProductoModel(@SerializedName("prod_desc") @Expose val prodDesc: String?,
                    @SerializedName("prod_titulo") @Expose val prodTitle: String?,
                    @SerializedName("prod_precio") @Expose val prodPrice: String?,
                    @SerializedName("prod_img") @Expose val prodImg: String?,
                    @SerializedName("prod_id") @Expose val prodID: Int?): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(prodDesc)
        parcel.writeString(prodTitle)
        parcel.writeString(prodPrice)
        parcel.writeString(prodImg)
        parcel.writeValue(prodID)
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