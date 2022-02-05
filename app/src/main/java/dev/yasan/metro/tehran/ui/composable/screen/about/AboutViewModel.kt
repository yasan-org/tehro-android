package dev.yasan.metro.tehran.ui.composable.screen.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yasan.kit.library.util.DispatcherProvider
import dev.yasan.kit.library.util.Resource
import dev.yasan.metro.tehran.R
import dev.yasan.metro.tehran.data.db.entity.DatabaseInformation
import dev.yasan.metro.tehran.data.db.entity.Stat
import dev.yasan.metro.tehran.data.repo.dbinfo.DatabaseInformationRepository
import dev.yasan.metro.tehran.data.repo.stat.StatRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * ViewModel for [AboutScreen].
 *
 * @see loadDatabaseInformation
 */
@HiltViewModel
class AboutViewModel @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val databaseInformationRepository: DatabaseInformationRepository,
    private val statRepository: StatRepository
) : ViewModel() {

    companion object {
        private const val TAG = "AboutViewModel"
    }

    init {
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch(dispatchers.io) {
            loadDatabaseInformation()
            loadStats()
        }
    }

    private var _databaseInformation =
        MutableLiveData<Resource<DatabaseInformation>>(Resource.Initial())
    val databaseInformation: LiveData<Resource<DatabaseInformation>> get() = _databaseInformation

    private suspend fun loadDatabaseInformation() {
        _databaseInformation.postValue(Resource.Loading())
        val data = databaseInformationRepository.getInformation()
        if (data != null) {
            _databaseInformation.postValue(Resource.Success(data = data))
        } else {
            _databaseInformation.postValue(Resource.Error(messageResourceId = R.string.failed_to_load_data))
        }
    }

    private suspend fun loadStats() {
        _stats.postValue(Resource.Loading())
        val data = statRepository.getStatistics()
        if (data.isNotEmpty()) {
            _stats.postValue(Resource.Success(data = data))
        } else {
            _stats.postValue(Resource.Error(messageResourceId = R.string.failed_to_load_data))
        }
    }

    private var _stats = MutableLiveData<Resource<List<Stat>>>(Resource.Initial())
    val stats: LiveData<Resource<List<Stat>>> get() = _stats

}