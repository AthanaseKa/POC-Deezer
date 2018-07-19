package fr.athanase.deezer.manager

import fr.athanase.deezer.model.json.PlaylistJson
import fr.athanase.deezer.model.realm.Playlist
import fr.athanase.deezer.network.client.PlaylistClient
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Test
import org.mockito.Mockito.*
import rx.Observable

class PlaylistManagerTest {

    @Test
    fun test() {
        val id: Long = 1

        val response = PlaylistJson()
        response.id = 7
        response.nb_tracks = 101
        val mockedManager = spy(PlaylistManager.Companion::class.java)
        val mockedClient = mock(PlaylistClient.Companion::class.java)
        `when`(mockedClient.getPlaylist(id)).thenReturn(Observable.just(response))
        `when`(mockedManager.getClient()).thenReturn(mockedClient)

        var result = Playlist()
        mockedManager.getPlaylistById(id)
                .subscribe {
                    playlist -> result = playlist
                }

        assertThat(result, notNullValue())
        assertEquals(7, result.id)
        assertEquals(101, result.nbTracks)
    }
}