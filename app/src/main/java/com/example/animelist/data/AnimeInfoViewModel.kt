import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animelist.network.Anime
import com.example.animelist.network.AnimeApi
import com.example.animelist.network.retrofit
import kotlinx.coroutines.launch
import retrofit2.create


class AnimeInfoViewModel : ViewModel() {
    private var _anime = MutableLiveData<Anime>()
    val anime: LiveData<Anime> = _anime

    fun getAnime(malId: Int) {
        viewModelScope.launch {
            val response = AnimeApi.retrofitService.getAnime(malId)
            _anime.value = response.data
        }
    }
}