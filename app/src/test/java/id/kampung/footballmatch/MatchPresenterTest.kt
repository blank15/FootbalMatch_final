package id.kampung.footballmatch

import com.nhaarman.mockito_kotlin.argumentCaptor
import id.kampung.footballmatch.data.model.MatchModel
import id.kampung.footballmatch.repository.RemoteRepository
import id.kampung.footballmatch.repository.RemoteRepositoryCallback
import id.kampung.footballmatch.view.match.MatchContract
import id.kampung.footballmatch.view.match.MatchPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MatchPresenterTest {


    @Mock
    private lateinit var view: MatchContract

    @Mock
    private lateinit var matchRepository: RemoteRepository

    @Mock
    private lateinit var matchResponse: MatchModel

    private lateinit var matchPresenter: MatchPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        matchPresenter = MatchPresenter(view, matchRepository)
    }


    @Test
    fun getLastMatchLoadedTest() {

        val error = "Error"

        matchPresenter.getLastMatch()

        argumentCaptor<RemoteRepositoryCallback<MatchModel?>>().apply {

            verify(matchRepository).getLastMatch(capture())
            firstValue.onDataError(error)
            firstValue.onDataLoaded(matchResponse)
        }

        Mockito.verify(view).onSuccess(matchResponse.events)
        Mockito.verify(view).onFailed(error)
    }

    @Test
    fun getNextMatchLoadedTest() {

        val error = "Error"

        matchPresenter.getNextMatch()

        argumentCaptor<RemoteRepositoryCallback<MatchModel?>>().apply {

            verify(matchRepository).getNextMatch(capture())
            firstValue.onDataError(error)
            firstValue.onDataLoaded(matchResponse)
        }

        Mockito.verify(view).onSuccess(matchResponse.events)
        Mockito.verify(view).onFailed(error)
    }

}