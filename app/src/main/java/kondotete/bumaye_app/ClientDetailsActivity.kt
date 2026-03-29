package kondotete.bumaye_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kondotete.bumaye_app.model.Client
import kondotete.bumaye_app.repository.ClientRepository

class ClientDetailsActivity : AppCompatActivity() {
    private var _v_current_client: Client? = null
    
    // Views
    private lateinit var _v_textViewNomPrenomsValue: TextView
    private lateinit var _v_textViewTelephoneValue: TextView
    private lateinit var _v_textViewEpauleValue: TextView
    private lateinit var _v_textViewPoitrineValue: TextView
    private lateinit var _v_textViewLongueurTailleValue: TextView
    private lateinit var _v_textViewTourVentraleValue: TextView
    private lateinit var _v_textViewHancheValue: TextView
    private lateinit var _v_textViewLongueurCorsageValue: TextView
    private lateinit var _v_textViewCeintureValue: TextView
    private lateinit var _v_textViewLongueurJupeValue: TextView
    private lateinit var _v_textViewLongueurRobeValue: TextView
    private lateinit var _v_textViewLongueurMancheValue: TextView
    private lateinit var _v_textViewTailleMancheValue: TextView
    private lateinit var _v_textViewLongueurPantalonValue: TextView
    private lateinit var _v_textViewLongueurRobeCourteValue: TextView
    private lateinit var _v_textViewTourCuisseValue: TextView
    private lateinit var _v_textViewLongueurGenouxValue: TextView
    private lateinit var _v_textViewTourGenouxValue: TextView
    private lateinit var _v_textViewBasValue: TextView
    private lateinit var _v_textViewAutresMesuresValue: TextView
    private lateinit var _v_textViewDateCommandeValue: TextView
    private lateinit var _v_textViewDateLivraisonValue: TextView
    private lateinit var _v_textViewSommeTotaleValue: TextView
    private lateinit var _v_textViewAvanceValue: TextView
    private lateinit var _v_textViewResteValue: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_details)

        Log.d("ClientDetails", "onCreate démarré")
        Toast.makeText(this, "ClientDetailsActivity démarrée", Toast.LENGTH_SHORT).show()

        // Affichage des détails du client

        try {
            _v_current_client = intent.getSerializableExtra("CLIENT_DATA") as? Client
            
            Log.d("ClientDetails", "Client reçu: ${_v_current_client?.toString()}")

            if (_v_current_client == null) {
                // Tentative de récupération par ID si disponible
                val clientId = intent.getStringExtra("CLIENT_ID")
                if (clientId != null) {
                    _v_current_client = ClientRepository.getClientById(clientId)
                    Log.d("ClientDetails", "Client récupéré par ID: ${_v_current_client?.toString()}")
                }
                
                if (_v_current_client == null) {
                    Toast.makeText(this, "Erreur: Aucun client trouvé", Toast.LENGTH_LONG).show()
                    finish()
                    return
                }
            }

            initViews()
            setupToolbar()
            displayClientDetails()
            
        } catch (e: Exception) {
            Log.e("ClientDetails", "Erreur lors de l'initialisation", e)
            Toast.makeText(this, "Erreur lors du chargement: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun initViews() {
        try {
            _v_textViewNomPrenomsValue = findViewById(R.id._v_textViewNomPrenomsValue)
            _v_textViewTelephoneValue = findViewById(R.id._v_textViewTelephoneValue)
            _v_textViewEpauleValue = findViewById(R.id._v_textViewEpauleValue)
            _v_textViewPoitrineValue = findViewById(R.id._v_textViewPoitrineValue)
            _v_textViewLongueurTailleValue = findViewById(R.id._v_textViewLongueurTailleValue)
            _v_textViewTourVentraleValue = findViewById(R.id._v_textViewTourVentraleValue)
            _v_textViewHancheValue = findViewById(R.id._v_textViewHancheValue)
            _v_textViewLongueurCorsageValue = findViewById(R.id._v_textViewLongueurCorsageValue)
            _v_textViewCeintureValue = findViewById(R.id._v_textViewCeintureValue)
            _v_textViewLongueurJupeValue = findViewById(R.id._v_textViewLongueurJupeValue)
            _v_textViewLongueurRobeValue = findViewById(R.id._v_textViewLongueurRobeValue)
            _v_textViewLongueurMancheValue = findViewById(R.id._v_textViewLongueurMancheValue)
            _v_textViewTailleMancheValue = findViewById(R.id._v_textViewTailleMancheValue)
            _v_textViewLongueurPantalonValue = findViewById(R.id._v_textViewLongueurPantalonValue)
            _v_textViewLongueurRobeCourteValue = findViewById(R.id._v_textViewLongueurRobeCourteValue)
            _v_textViewTourCuisseValue = findViewById(R.id._v_textViewTourCuisseValue)
            _v_textViewLongueurGenouxValue = findViewById(R.id._v_textViewLongueurGenouxValue)
            _v_textViewTourGenouxValue = findViewById(R.id._v_textViewTourGenouxValue)
            _v_textViewBasValue = findViewById(R.id._v_textViewBasValue)
            _v_textViewAutresMesuresValue = findViewById(R.id._v_textViewAutresMesuresValue)
            _v_textViewDateCommandeValue = findViewById(R.id._v_textViewDateCommandeValue)
            _v_textViewDateLivraisonValue = findViewById(R.id._v_textViewDateLivraisonValue)
            _v_textViewSommeTotaleValue = findViewById(R.id._v_textViewSommeTotaleValue)
            _v_textViewAvanceValue = findViewById(R.id._v_textViewAvanceValue)
            _v_textViewResteValue = findViewById(R.id._v_textViewResteValue)
            
            Log.d("ClientDetails", "Toutes les vues ont été initialisées avec succès")
        } catch (e: Exception) {
            Log.e("ClientDetails", "Erreur lors de l'initialisation des vues", e)
            throw e
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Détails Client"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun displayClientDetails() {
        _v_current_client?.let { client ->
            try {
                Log.d("ClientDetails", "Affichage des détails pour: ${client._v_nom_prenoms}")
                
                // Affichage des informations personnelles
                _v_textViewNomPrenomsValue.text = client._v_nom_prenoms.ifEmpty { "Non renseigné" }
                _v_textViewTelephoneValue.text = client._v_numero_telephone.ifEmpty { "Non renseigné" }

                // Affichage des informations de mesure
                _v_textViewEpauleValue.text = if (client._v_epaule.isNotEmpty()) "${client._v_epaule} cm" else "-"
                _v_textViewPoitrineValue.text = if (client._v_poitrine.isNotEmpty()) "${client._v_poitrine} cm" else "-"
                _v_textViewLongueurTailleValue.text = if (client._v_longueur_taille.isNotEmpty()) "${client._v_longueur_taille} cm" else "-"
                _v_textViewTourVentraleValue.text = if (client._v_tour_ventrale.isNotEmpty()) "${client._v_tour_ventrale} cm" else "-"
                _v_textViewHancheValue.text = if (client._v_hanche.isNotEmpty()) "${client._v_hanche} cm" else "-"
                _v_textViewLongueurCorsageValue.text = if (client._v_longueur_corsage.isNotEmpty()) "${client._v_longueur_corsage} cm" else "-"
                _v_textViewCeintureValue.text = if (client._v_ceinture.isNotEmpty()) "${client._v_ceinture} cm" else "-"
                _v_textViewLongueurJupeValue.text = if (client._v_longueur_jupe.isNotEmpty()) "${client._v_longueur_jupe} cm" else "-"
                _v_textViewLongueurRobeValue.text = if (client._v_longueur_robe.isNotEmpty()) "${client._v_longueur_robe} cm" else "-"
                _v_textViewLongueurMancheValue.text = if (client._v_longueur_manche.isNotEmpty()) "${client._v_longueur_manche} cm" else "-"
                _v_textViewTailleMancheValue.text = if (client._v_taille_manche.isNotEmpty()) "${client._v_taille_manche} cm" else "-"
                _v_textViewLongueurPantalonValue.text = if (client._v_longueur_pantalon.isNotEmpty()) "${client._v_longueur_pantalon} cm" else "-"
                _v_textViewLongueurRobeCourteValue.text = if (client._v_longueur_robe_courte.isNotEmpty()) "${client._v_longueur_robe_courte} cm" else "-"
                _v_textViewTourCuisseValue.text = if (client._v_tour_cuisse.isNotEmpty()) "${client._v_tour_cuisse} cm" else "-"
                _v_textViewLongueurGenouxValue.text = if (client._v_longueur_genoux.isNotEmpty()) "${client._v_longueur_genoux} cm" else "-"
                _v_textViewTourGenouxValue.text = if (client._v_tour_genoux.isNotEmpty()) "${client._v_tour_genoux} cm" else "-"
                _v_textViewBasValue.text = if (client._v_bas.isNotEmpty()) "${client._v_bas} cm" else "-"
                _v_textViewAutresMesuresValue.text = if (client._v_autres_mesures.isNotEmpty()) client._v_autres_mesures else "-"

                // Affichage des informations de commande
                _v_textViewDateCommandeValue.text = client._v_date_commande.ifEmpty { "Non renseigné" }
                _v_textViewDateLivraisonValue.text = client._v_date_livraison.ifEmpty { "Non renseigné" }

                // Affichage des informations de paiement
                _v_textViewSommeTotaleValue.text = if (client._v_somme_totale.isNotEmpty()) "${client._v_somme_totale} FCFA" else "0 FCFA"
                _v_textViewAvanceValue.text = if (client._v_avance.isNotEmpty()) "${client._v_avance} FCFA" else "0 FCFA"
                _v_textViewResteValue.text = if (client._v_reste.isNotEmpty()) "${client._v_reste} FCFA" else "0 FCFA"
                
                Log.d("ClientDetails", "Détails affichés avec succès")
                Toast.makeText(this, "Détails affichés pour: ${client._v_nom_prenoms}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("ClientDetails", "Erreur lors de l'affichage des détails", e)
                Toast.makeText(this, "Erreur lors de l'affichage: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } ?: run {
            Log.e("ClientDetails", "Client est null")
            Toast.makeText(this, "Erreur: Client non trouvé", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.client_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_edit -> {
                editClient()
                true
            }
            R.id.action_delete -> {
                showDeleteConfirmation()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun editClient() {
        val intent = Intent(this, AddEditClientActivity::class.java)
        intent.putExtra("CLIENT_DATA", _v_current_client)
        startActivity(intent)
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Confirmer suppression")
            .setMessage("Êtes-vous sûr de vouloir supprimer ce client ?")
            .setPositiveButton("Supprimer") { _, _ ->
                deleteClient()
            }
            .setNegativeButton("Annuler", null)
            .show()
    }

    private fun deleteClient() {
        _v_current_client?._v_id?.let { clientId ->
            ClientRepository.deleteClient(clientId)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()
        //  Recharger les données client en cas de mise à jour
        _v_current_client?._v_id?.let { id ->
            _v_current_client = ClientRepository.getClientById(id)
            displayClientDetails()
        }
    }
}
