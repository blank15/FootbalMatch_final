package id.kampung.footballmatch

import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import id.kampung.footballmatch.data.model.MatchModel
import id.kampung.footballmatch.data.model.TeamList
import id.kampung.footballmatch.data.model.TeamModel
import id.kampung.footballmatch.repository.RemoteRepository
import id.kampung.footballmatch.repository.RemoteRepositoryCallback
import id.kampung.footballmatch.view.detailMatch.DetailContract
import id.kampung.footballmatch.view.detailMatch.DetailPresenter
import id.kampung.footballmatch.view.match.MatchContract
import id.kampung.footballmatch.view.match.MatchPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailPresenterTest {

    @Mock
    private lateinit var view: DetailContract

    @Mock
    private lateinit var matchRepository: RemoteRepository

    @Mock
    private lateinit var matchResponse: TeamList

    private lateinit var detailPresenter: DetailPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailPresenter = DetailPresenter(view,matchRepository)
    }

    @Test
    fun getLastMatchLoadedTest() {

        val error = "Error"
        val id = "1"
        detailPresenter.getAwayLogo(id)
        argumentCaptor<RemoteRepositoryCallback<TeamList?>>().apply {

            verify(matchRepository).getLogo(eq(id),capture())
            firstValue.onDataError(error)
            firstValue.onDataLoaded(matchResponse)
        }

        Mockito.verify(view).onSuccessLoadAway(matchResponse)
        Mockito.verify(view).onFailed(error)
    }

}