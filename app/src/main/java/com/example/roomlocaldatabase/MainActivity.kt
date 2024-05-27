package com.example.roomlocaldatabase

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    // Cara 1 Old
//    private var viewAllDatabase: TextView? = null

    // Cara 2 Now
    // harus menambahkan lateinit untuk insialisasi variable action dari xml
    private lateinit var viewAllRoomDatabase: TextView

    private lateinit var btnAll: Button
    private lateinit var btnInsert: Button

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var btnInsertData: Button

    private lateinit var insertRoomDatabase: LinearLayout

    private lateinit var rvViewAllRoomDatabase: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Turn Off Night Mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Usage Room
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "MyDatabase"
        ).allowMainThreadQueries().build()

        val userDao = db.userDao()

        viewAllRoomDatabase = findViewById(R.id.viewAllRoomDatabase)
        rvViewAllRoomDatabase = findViewById(R.id.rvViewAllRoomDatabase)
        insertRoomDatabase = findViewById(R.id.insertRoomDatabase)

        btnAll = findViewById(R.id.btnAll)
        btnAll.setOnClickListener{
            val users: List<User> = userDao.getAll()


            // efek cara 1
//        viewAllDatabase?.setText(users.toString())

            // efek cara 2 jika inisialisasi variable menggunakan lateinit
            viewAllRoomDatabase.text = users.toString()
            rvViewAllRoomDatabase.visibility = View.GONE
            insertRoomDatabase.visibility = View.GONE
            viewAllRoomDatabase.visibility = View.VISIBLE
        }

        // Value Akan Hilang ketika klik button dilepas
//        btnAll.setOnLongClickListener {
//            insertRoomDatabase.visibility = View.GONE
//            viewAllRoomDatabase.visibility = View.GONE
//            rvViewAllRoomDatabase.visibility = View.VISIBLE
//
//            rvViewAllRoomDatabase.layoutManager = LinearLayoutManager(this)
//            rvViewAllRoomDatabase.setHasFixedSize(true)
//
//            val getDataListUsers: List<User> = userDao.getAll()
//
//            rvViewAllRoomDatabase.adapter = ListDataAdapter(getDataListUsers)
//
//            false
//        }

        btnAll.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                insertRoomDatabase.visibility = View.GONE
                viewAllRoomDatabase.visibility = View.GONE
                rvViewAllRoomDatabase.visibility = View.VISIBLE

                rvViewAllRoomDatabase.layoutManager = LinearLayoutManager(applicationContext)
                rvViewAllRoomDatabase.setHasFixedSize(true)

                val getDataListUsers: List<User> = userDao.getAll()

                rvViewAllRoomDatabase.adapter = ListDataAdapter(getDataListUsers)
                return true
            }
        })

        btnInsert = findViewById(R.id.btnInsert)
        btnInsert.setOnClickListener{
            viewAllRoomDatabase.visibility = View.GONE
            rvViewAllRoomDatabase.visibility = View.GONE
            insertRoomDatabase.visibility = View.VISIBLE

            etFirstName = findViewById(R.id.etFirstName)
            etLastName = findViewById(R.id.etLastName)

            btnInsertData = findViewById(R.id.btnInsertData)
            btnInsertData.setOnClickListener {
                if (etFirstName.text.toString() == "" && etFirstName.text.toString().isEmpty()) {
                    etFirstName.error = "Tidak Boleh Kosong !!"
                } else if (etLastName.text.toString() == "" && etLastName.text.toString().isEmpty()) {
                    etLastName.error = "Tidak Boleh Kosong !!"
                } else {
                    userDao.insertAll(User(firstName = etFirstName.text.toString(), lastName = etLastName.text.toString()))
                }
            }
        }
    }
}