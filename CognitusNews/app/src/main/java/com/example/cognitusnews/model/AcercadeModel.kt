package com.example.cognitusnews.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AcercadeModel(@SerializedName("nosotros_id") @Expose val nosotrosID: String?,
                    @SerializedName("nosotros_img") @Expose val nosotrosImg: String?,
                    @SerializedName("nosotros_status") @Expose val nosotrosUrl: String?,
                    @SerializedName("nosotros_desc") @Expose val nosotrosDesc: String?)