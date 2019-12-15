package com.example.tareas.model



import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TareasModel (
    @SerializedName("tarea_subtit") @Expose val tareaSubtit: String?=null,
    @SerializedName("usrtar_nota") @Expose val usrTareaNota: String?=null,
    @SerializedName("usrtar_horas") @Expose val usrTareaHoras: String?=null,
    @SerializedName("tarea_titulo") @Expose val tareaTitulo: String?=null,
    @SerializedName("usrtar_id") @Expose val usrTareaId: String?=null,
    @SerializedName("tarea_hrs") @Expose val tareaHoras: String?=null,
    @SerializedName("usrtar_fin") @Expose val usrTareaFin: String?=null,
    @SerializedName("tarea_desc") @Expose val tareaDesc: String?=null,
    @SerializedName("usrtar_status") @Expose val usrTareaStatus: String?=null):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(tareaSubtit)
        parcel.writeString(usrTareaNota)
        parcel.writeString(usrTareaHoras)
        parcel.writeString(tareaTitulo)
        parcel.writeString(usrTareaId)
        parcel.writeString(tareaHoras)
        parcel.writeString(usrTareaFin)
        parcel.writeString(tareaDesc)
        parcel.writeString(usrTareaStatus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TareasModel> {
        override fun createFromParcel(parcel: Parcel): TareasModel {
            return TareasModel(parcel)
        }

        override fun newArray(size: Int): Array<TareasModel?> {
            return arrayOfNulls(size)
        }
    }
}

