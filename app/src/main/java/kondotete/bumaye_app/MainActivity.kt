package kondotete.bumaye_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kondotete.bumaye_app.adapter.ClientAdapter
import kondotete.bumaye_app.databinding.ActivityMainBinding
import kondotete.bumaye_app.repository.ClientRepository

class MainActivity : AppCompatActivity() {
    private lateinit var _v_binding: ActivityMainBinding
    private lateinit var _v_client_adapter: ClientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _v_binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_v_binding.root)

        setupRecyclerView()
        setupSearchView()
        setupFab()
        loadClients()

        // 欢迎来到Bumaye应用 - Bienvenue dans l'application Bumaye
    }

    private fun setupRecyclerView() {
        _v_client_adapter = ClientAdapter { client ->
            val intent = Intent(this, ClientDetailsActivity::class.java)
            intent.putExtra("CLIENT_DATA", client)
            startActivity(intent)
        }

        _v_binding._v_recyclerViewClients.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = _v_client_adapter
        }
    }

    private fun setupSearchView() {
        _v_binding._v_editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()
                if (query.isEmpty()) {
                    loadClients()
                } else {
                    searchClients(query)
                }
            }
        })
    }

    private fun setupFab() {
        _v_binding._v_fabAddClient.setOnClickListener {
            val intent = Intent(this, AddEditClientActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadClients() {
        val clients = ClientRepository.getAllClients()
        _v_client_adapter.submitList(clients)

        // 更新空状态视图 - Mise à jour de la vue d'état vide
        if (clients.isEmpty()) {
            _v_binding._v_textViewEmptyState.visibility = View.VISIBLE
            _v_binding._v_recyclerViewClients.visibility = View.GONE
        } else {
            _v_binding._v_textViewEmptyState.visibility = View.GONE
            _v_binding._v_recyclerViewClients.visibility = View.VISIBLE
        }
    }

    private fun searchClients(query: String) {
        val clients = ClientRepository.searchClients(query)
        _v_client_adapter.submitList(clients)
    }

    override fun onResume() {
        super.onResume()
        loadClients()
    }
}
