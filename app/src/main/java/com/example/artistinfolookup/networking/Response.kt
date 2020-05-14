package com.example.artistinfolookup.networking

import android.os.Parcel
import android.os.Parcelable


data class ArtistInfo(
    val artists: List<Artist>
)

data class ArtistAlbums(
    val album: List<Album>
)


data class Artist(
    val idArtist: String?,
    val strArtist: String?,
    val strGenre: String?,
    val strBiographyEN: String?,
    val strWebsite: String?,
    val strArtistThumb: String?,
    val strArtistLogo: String?,
    val intBornYear: Int?
): Parcelable {
    constructor():this("","","","","","","",0)

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idArtist)
        parcel.writeString(strArtist)
        parcel.writeString(strGenre)
        parcel.writeString(strBiographyEN)
        parcel.writeString(strWebsite)
        parcel.writeString(strArtistThumb)
        parcel.writeString(strArtistLogo)
        parcel.writeValue(intBornYear)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist> {
        override fun createFromParcel(parcel: Parcel): Artist {
            return Artist(parcel)
        }

        override fun newArray(size: Int): Array<Artist?> {
            return arrayOfNulls(size)
        }
    }
}


data class Album(
    val idAlbum: String,
    val strAlbum: String?,
    val intYearReleased: Int?,
    val strGenre: String?,
    val strAlbumThumb: String?,
    val strArtist: String?,
    val strDescriptionEN: String?

)

data class AlbumDetail(
    val album: List<Album>
)

data class Track(
    val intDuration: Int?,
    val strTrack: String?,
    val strTrackThumb: String?,
    val strMusicVid: String?
)

data class TrackList(
    val track: List<Track>
)

data class Video(
    val strTrack: String?,
    val strTrackThumb: String?,
    val strMusicVid: String?
)

data class ArtistVideo(
    val mvids: List<Video>?
)

data class User (
    val uid: String,
    val email: String,
    val username: String,
    val artists: List<Artist>
){
    constructor():this("","","", emptyList())
}