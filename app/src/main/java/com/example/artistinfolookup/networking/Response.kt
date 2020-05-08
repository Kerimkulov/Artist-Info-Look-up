package com.example.artistinfolookup.networking



data class ArtistInfo(
    val artists: List<Artist>
)

data class ArtistAlbums(
    val album: List<Album>
)


data class Artist(
    val strArtist: String?,
    val strGenre: String?,
    val strBiographyEN: String?,
    val strWebsite: String?,
    val strArtistThumb: String?,
    val strArtistLogo: String?,
    val intBornYear: Int?
)


data class Album(
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


