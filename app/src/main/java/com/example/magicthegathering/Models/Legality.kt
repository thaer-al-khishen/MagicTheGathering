

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Legality(
    @SerializedName("format")
    val format: String,
    @SerializedName("legality")
    val legality: String
): Serializable