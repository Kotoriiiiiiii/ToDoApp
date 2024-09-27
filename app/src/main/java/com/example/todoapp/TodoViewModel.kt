import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.todoapp.TodoData

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository = TodoRepository(application)

    // フィルタ状態を保持するLiveData (true: gone, false: not gone)
    private val isGoneFilterActive = MutableLiveData<Boolean>()

    // フィルタ状態を保持する(お気に入り登録)
    private val isStarFilterActive = MutableLiveData<Boolean>()

    // フィルタに応じたデータを取得
    val todoData: LiveData<List<TodoData>> = isGoneFilterActive.switchMap { isGone ->
        if (isGone == true) {
            repository.getGoneTodoData()
        } else {
            repository.getNotGoneTodoData()
        }
    }
    // フィルタに応じたデータ、お気に入り
    val favoriteData: LiveData<List<TodoData>> = isStarFilterActive.switchMap { isStar ->
        if (isStar == true) {
            repository.starData()
        } else {
            //ここわからない
            repository.starData()
        }
    }

    // Goneボタンを押したときのフィルタ設定
    fun showGoneItems() {
        isGoneFilterActive.value = true
    }

    // Neverボタンを押したときのフィルタ設定
    fun showNotGoneItems() {
        isGoneFilterActive.value = false
    }
    fun showFavoriteItems() {
        isStarFilterActive.value = true
    }
}
