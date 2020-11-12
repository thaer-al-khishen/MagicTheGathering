

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Ruling(
    @SerializedName("date")
    val date: String,
    @SerializedName("text")
    val text: String
): Serializable