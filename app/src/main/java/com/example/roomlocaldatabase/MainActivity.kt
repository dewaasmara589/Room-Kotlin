package com.example.roomlocaldatabase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomlocaldatabase.ForeignKeys.ReportCard
import com.example.roomlocaldatabase.ForeignKeys.UserWithReport

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

    private lateinit var btnRole: Button

    private lateinit var insertRoomDatabase: LinearLayout

    private lateinit var rvViewAllRoomDatabase: RecyclerView

    private var idUser : String? = ""
    private var selectedRoles: String? = ""

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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // TODO 1 Use Room
//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "MyDatabase"
//        ).allowMainThreadQueries().build()
//
        // TODO Initialize Room
//        val userDao = db.userDao()

        // TODO Initialize Singleton Room
        val userDao = AppDatabase.getDatabase(applicationContext)?.userDao()

        viewAllRoomDatabase = findViewById(R.id.viewAllRoomDatabase)
        rvViewAllRoomDatabase = findViewById(R.id.rvViewAllRoomDatabase)
        insertRoomDatabase = findViewById(R.id.insertRoomDatabase)

        btnAll = findViewById(R.id.btnAll)
        btnAll.setOnClickListener{
            val users: List<User> = userDao!!.getAll()
            val reportCard: List<ReportCard> = userDao.getAllReportCard()


            // efek cara 1
//        viewAllDatabase?.setText(users.toString())

            // efek cara 2 jika inisialisasi variable menggunakan lateinit
            viewAllRoomDatabase.text = users.toString() + "\n" + reportCard.toString()
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

        // TODO 2 Use Room from AppDatabase

        btnAll.setOnLongClickListener(object: View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                insertRoomDatabase.visibility = View.GONE
                viewAllRoomDatabase.visibility = View.GONE
                rvViewAllRoomDatabase.visibility = View.VISIBLE

                rvViewAllRoomDatabase.layoutManager = LinearLayoutManager(applicationContext)
                rvViewAllRoomDatabase.setHasFixedSize(true)

                val getDataListUserswithReportCard: List<UserWithReport> = userDao!!.getAllDataUserJoinReportCard()
                rvViewAllRoomDatabase.adapter = ListDataAdapter(getDataListUserswithReportCard)

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
                    userDao!!.insertAll(User(firstName = etFirstName.text.toString(), lastName = etLastName.text.toString()))

                    etFirstName.setText("")
                    etFirstName.clearFocus()
                    etLastName.setText("")
                    etLastName.clearFocus()

                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            }
        }

        // ToDo Initialize Spinner with default value for Choose User
        val spUser = findViewById<Spinner>(R.id.spUser)
        val llRole : LinearLayout = findViewById(R.id.llRole)
        llRole.visibility = View.GONE

        val combineData = mutableListOf<String>()

        val dataUser: List<User> = userDao!!.getAll()

        combineData.add(0, "Choose User")

        if (spUser != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, combineData)

            spUser.adapter = adapter

            for (dt in dataUser) {
                combineData.add(dt.firstName.toString() + " " + dt.lastName)
            }

            adapter.notifyDataSetChanged()

            spUser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d("TAG", "CombineData : ${combineData[position]}")
                    if (combineData[position] == "Choose User"){
                        llRole.visibility = View.GONE
                    } else {
                        var index = position
                        index--

                        Log.d("TAG", "id User selected : ${dataUser[index].uid.toString()}")

                        idUser = dataUser[index].uid.toString()
                        llRole.visibility = View.VISIBLE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    idUser = ""
                }
            }

            adapter.notifyDataSetChanged()
        }

        // ToDo Initialize Spinner with default value from first array String for choose Role
        val spRole = findViewById<Spinner>(R.id.spRole)
        val roles  = resources.getStringArray(R.array.Role)

        if (spRole != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)
            spRole.adapter = adapter

            spRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedRoles = roles[position]
                    Log.d("TAG", "Role selected : $selectedRoles")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }

        btnRole = findViewById(R.id.btnRole)
        btnRole.setOnClickListener {
            if (idUser=="") Toast.makeText(applicationContext, "Select UserId", Toast.LENGTH_SHORT).show()

            userDao.insertAllReportCard(ReportCard(userId = idUser?.toIntOrNull(), role = selectedRoles))

            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }
}