package com.example.todoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoapp.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    lateinit var db : AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        db = AppDatabase.getInstance(this.applicationContext)

        val id: Int = intent.getIntExtra("ID",0)

        val tappedTodo = db.todoDataDao().getDataById(id)

        binding.titleEditText.setText(tappedTodo.title)

        binding.editButton.setOnClickListener{
            val newTitle = binding.titleEditText.text.toString()

            tappedTodo.title = newTitle

            db.todoDataDao().updata(tappedTodo)

            // MainActivityへ移動
            finish()
        }




        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }


}