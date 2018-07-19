package fr.athanase.deezer.model.json

class PlaylistJson {
    var id: Long = 0
    var title: String? = null
    var picture: String? = null
    var nb_tracks: Int = 0
    var description: String? = null
    var tracks: TracksJson? = null
    var creator: CreatorJson? = null
}
