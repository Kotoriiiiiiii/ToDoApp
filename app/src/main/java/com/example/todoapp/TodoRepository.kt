import android.app.Application
import androidx.lifecycle.LiveData
import com.example.todoapp.AppDatabase
import com.example.todoapp.TodoData
import com.example.todoapp.TodoDataDao

class TodoRepository(application: Application) {

    private val todoDao: TodoDataDao

    init {
        val database = AppDatabase.getInstance(application)
        todoDao = database.todoDataDao()
    }

    fun getGoneTodoData(): LiveData<List<TodoData>> = todoDao.getGoneTodoData()

    fun getNotGoneTodoData(): LiveData<List<TodoData>> = todoDao.getNotGoneTodoData()

    fun starData(): LiveData<List<TodoData>> = todoDao.starData()
}
