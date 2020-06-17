package edu.ecu.cs.pirateplaces

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class PiratePlace(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var visitedWith: String = "",
    var lastVisited: Date = Date(),
    var hasLocation: Int = 0,
    var latitude: Double = 0.0,
    var longitude: Double = 0.0)
    {

    val photoFileName
        get() = "IMG_$id.jpg"

}

