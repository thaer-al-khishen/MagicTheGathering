
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.magicthegathering.Base.Resource
import com.example.magicthegathering.Utils.IRxSchedulers

class CardsViewModel @ViewModelInject constructor(private val api: CardsAPIService, private val schedulers: IRxSchedulers, @Assisted state: SavedStateHandle) :
    BaseViewModel() {

    private val _cards: MutableLiveData<Resource<List<Card>>> = MutableLiveData()
    val card: LiveData<Resource<List<Card>>>
        get() = _cards

    init {
        getAllCards()
    }

    fun getAllCards() {
        addToDisposable(api.getAllCards()

            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())

            .doFinally {
            }
            .doOnError{
                _cards.postValue(Resource(it,null))}

            .subscribe({ response ->
                _cards.postValue(Resource(null, response.cards)) }, {})
        )

    }

}