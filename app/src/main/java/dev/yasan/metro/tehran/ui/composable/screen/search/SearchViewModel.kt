package dev.yasan.metro.tehran.ui.composable.screen.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yasan.kit.core.DispatcherProvider
import dev.yasan.kit.core.Resource
import dev.yasan.metro.tehran.R
import dev.yasan.metro.tehran.domain.repository.station.StationRepository
import dev.yasan.metro.tehran.model.tehro.Station
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [SearchScreen].
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val stationRepository: StationRepository
) : ViewModel() {

    companion object {
        private const val TAG = "SearchViewModel"
        private const val SEARCH_QUERY_MIN_LENGTH = 1
    }

    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> get() = _loading

    private var _results = MutableLiveData<Resource<List<Station>>>(Resource.Initial())
    val results: LiveData<Resource<List<Station>>> get() = _results

    /**
     * Search stations by query. Unlike everywhere else, this method never posts [Resource.Loading].
     * This is done to prevent the UI from flickering when the user is typing.
     * Loading state is posted on [_loading] instead.
     */
    fun search(query: String) {
        Log.d(TAG, "search: $query")
        viewModelScope.launch(dispatchers.io) {
            _loading.postValue(true)
            if (query.trim().length < SEARCH_QUERY_MIN_LENGTH) {
                _results.postValue(Resource.Success(emptyList()))
                _loading.postValue(false)
            } else {
                try {
                    _results.postValue(
                        Resource.Success(
                            stationRepository.searchStations(
                                complete = true,
                                query = query
                            )
                        )
                    )
                    _loading.postValue(false)
                } catch (e: Exception) {
                    _results.postValue(Resource.Error(messageResourceId = R.string.error))
                    _loading.postValue(false)
                }
            }
        }
    }

}
