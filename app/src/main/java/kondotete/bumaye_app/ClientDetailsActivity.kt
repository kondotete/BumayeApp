package kondotete.bumaye_app

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kondotete.bumaye_app.databinding.ActivityClientDetailsBinding
import kondotete.bumaye_app.model.Client
import kondotete.bumaye_app.repository.ClientRepository

class ClientDetailsActivity : AppCompatActivity() {
    private lateinit var _v_binding: ActivityClientDetailsBinding
    private var _v_current_client: Client? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _v_binding = ActivityClientDetailsBinding.inflate(layoutInflater)
        setContentView(_v_binding.root)

        // 客户详细信息展示 - Affichage des détails du client

        _v_current_client = intent.getSerializableExtra("CLIENT_DATA") as? Client

        setupToolbar()
        displayClientDetails()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = "Détails Client"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun displayClientDetails() {
        _v_current_client?.let { client ->
            _v_binding.apply {
                // Affichage des informations personnelles
                _v_textViewNomPrenomsValue.text = client._v_nom_prenoms
                _v_textViewTelephoneValue.text = client._v_numero_telephone

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

                // 订单信息显示 - Affichage des informations de commande
                _v_textViewDateCommandeValue.text = client._v_date_commande
                _v_textViewDateLivraisonValue.text = client._v_date_livraison

                // 付款信息显示 - Affichage des informations de paiement
                _v_textViewSommeTotaleValue.text = "${client._v_somme_totale} FCFA"
                _v_textViewAvanceValue.text = "${client._v_avance} FCFA"
                _v_textViewResteValue.text = "${client._v_reste} FCFA"
            }
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
            .setTitle("确认删除 - Confirmer suppression")
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
        // 重新加载客户数据以防更新 - Recharger les données client en cas de mise à jour
        _v_current_client?._v_id?.let { id ->
            _v_current_client = ClientRepository.getClientById(id)
            displayClientDetails()
        }
    }
}
