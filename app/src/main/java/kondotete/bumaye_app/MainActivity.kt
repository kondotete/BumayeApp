package kondotete.bumaye_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import kondotete.bumaye_app.adapter.ClientAdapter
import kondotete.bumaye_app.model.Client
import kondotete.bumaye_app.repository.ClientRepository

class MainActivity : AppCompatActivity() {

    private lateinit var _v_adapter: ClientAdapter
    
    // Views
    private lateinit var _v_recyclerViewClients: RecyclerView
    private lateinit var _v_editTextSearch: TextInputEditText
    private lateinit var _v_fabAddClient: FloatingActionButton
    private lateinit var _v_textViewEmptyState: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        _v_setupRecyclerView()
        _v_setupSearch()
        _v_setupFab()

        //Chargement des données initiale
        _v_loadClients()
    }

    private fun initViews() {
        _v_recyclerViewClients = findViewById(R.id._v_recyclerViewClients)
        _v_editTextSearch = findViewById(R.id._v_editTextSearch)
        _v_fabAddClient = findViewById(R.id._v_fabAddClient)
        _v_textViewEmptyState = findViewById(R.id._v_textViewEmptyState)
    }

    private fun _v_setupRecyclerView() {
        _v_adapter = ClientAdapter { client -> _v_showClientDetails(client) }

        _v_recyclerViewClients.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = _v_adapter
        }
    }

    private fun _v_setupSearch() {
        _v_editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.isEmpty()) {
                    _v_loadClients()
                } else {
                    _v_filterClients(query)
                }
            }
        })
    }

    private fun _v_setupFab() {
        _v_fabAddClient.setOnClickListener {
            _v_addNewClient()
        }
    }

    private fun _v_loadClients() {
        val clients = ClientRepository.getAllClients()
        _v_adapter.submitList(clients)

        // Mise à jour de la vue d'état vide
        if (clients.isEmpty()) {
            _v_textViewEmptyState.visibility = View.VISIBLE
            _v_recyclerViewClients.visibility = View.GONE
        } else {
            _v_textViewEmptyState.visibility = View.GONE
            _v_recyclerViewClients.visibility = View.VISIBLE
        }
    }

    //Fonction de filtrage des clients
    private fun _v_filterClients(query: String) {
        val filteredClients = if (query.isEmpty()) {
            ClientRepository.getAllClients()
        } else {
            ClientRepository.searchClients(query)
        }
        _v_adapter.submitList(filteredClients)
    }

    private fun _v_showClientDetails(client: Client) {
        Log.d("MainActivity", "Ouverture des détails pour: ${client._v_nom_prenoms}")
        Log.d("MainActivity", "Client ID: ${client._v_id}")
        
        val intent = Intent(this, ClientDetailsActivity::class.java)
        intent.putExtra("CLIENT_DATA", client)
        intent.putExtra("CLIENT_ID", client._v_id) // Fallback par ID
        startActivity(intent)
    }

    private fun _v_addNewClient() {
        val intent = Intent(this, AddEditClientActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        _v_loadClients() // (Recharger la liste des clients)
    }
}
