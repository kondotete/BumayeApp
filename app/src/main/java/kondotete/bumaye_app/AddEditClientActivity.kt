package kondotete.bumaye_app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kondotete.bumaye_app.AddEditClientActivity
import kondotete.bumaye_app.model.Client
import kondotete.bumaye_app.repository.ClientRepository
import java.text.SimpleDateFormat
import java.util.*

class AddEditClientActivity : AppCompatActivity() {
    private lateinit var _v_binding: ActivityAddEditClientBinding
    private var _v_current_client: Client? = null
    private var _v_is_editing = false
    private val _v_date_format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _v_binding = ActivityAddEditClientBinding.inflate(layoutInflater)
        setContentView(_v_binding.root)

        // 客户信息管理系统 - Système de gestion des informations client

        checkIfEditing()
        setupToolbar()
        setupDatePickers()
        setupCalculations()
        setupButtons()

        if (_v_is_editing) {
            populateFields()
        }
    }

    private fun checkIfEditing() {
        _v_current_client = intent.getSerializableExtra("CLIENT_DATA") as? Client
        _v_is_editing = _v_current_client != null

        if (!_v_is_editing) {
            _v_current_client = Client()
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            title = if (_v_is_editing) "Modifier Client" else "Nouveau Client"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupDatePickers() {
        val calendar = Calendar.getInstance()

        // 订单日期选择器 - Sélecteur de date de commande
        _v_binding._v_editTextDateCommande.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    _v_binding._v_editTextDateCommande.setText(
                        _v_date_format.format(calendar.time)
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // 交货日期选择器 - Sélecteur de date de livraison
        _v_binding._v_editTextDateLivraison.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    _v_binding._v_editTextDateLivraison.setText(
                        _v_date_format.format(calendar.time)
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupCalculations() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                calculateReste()
            }
        }

        _v_binding._v_editTextSommeTotal.addTextChangedListener(textWatcher)
        _v_binding._v_editTextAvance.addTextChangedListener(textWatcher)
    }

    private fun calculateReste() {
        try {
            val total = _v_binding._v_editTextSommeTotal.text.toString().toDoubleOrNull() ?: 0.0
            val avance = _v_binding._v_editTextAvance.text.toString().toDoubleOrNull() ?: 0.0
            val reste = total - avance
            _v_binding._v_editTextReste.setText(reste.toString())
        } catch (e: Exception) {
            _v_binding._v_editTextReste.setText("0")
        }
    }

    private fun setupButtons() {
        _v_binding._v_buttonSave.setOnClickListener {
            if (validateForm()) {
                saveClient()
            }
        }

        _v_binding._v_buttonTakePhoto.setOnClickListener {
            // 拍摄客户照片 - Prendre une photo du client
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_CAMERA)
            }
        }
    }

    private fun populateFields() {
        _v_current_client?.let { client ->
            _v_binding.apply {
                _v_editTextNomPrenoms.setText(client._v_nom_prenoms)
                _v_editTextTelephone.setText(client._v_numero_telephone)

                // 填充所有测量字段 - Remplir tous les champs de mesure
                _v_editTextEpaule.setText(client._v_epaule)
                _v_editTextPoitrine.setText(client._v_poitrine)
                _v_editTextLongueurTaille.setText(client._v_longueur_taille)
                _v_editTextTourVentrale.setText(client._v_tour_ventrale)
                _v_editTextHanche.setText(client._v_hanche)
                _v_editTextLongueurCorsage.setText(client._v_longueur_corsage)
                _v_editTextCeinture.setText(client._v_ceinture)
                _v_editTextLongueurJupe.setText(client._v_longueur_jupe)
                _v_editTextLongueurRobe.setText(client._v_longueur_robe)
                _v_editTextLongueurManche.setText(client._v_longueur_manche)
                _v_editTextTailleManche.setText(client._v_taille_manche)
                _v_editTextLongueurPantalon.setText(client._v_longueur_pantalon)
                _v_editTextLongueurRobeCourte.setText(client._v_longueur_robe_courte)
                _v_editTextTourCuisse.setText(client._v_tour_cuisse)
                _v_editTextLongueurGenoux.setText(client._v_longueur_genoux)
                _v_editTextTourGenoux.setText(client._v_tour_genoux)
                _v_editTextBas.setText(client._v_bas)
                _v_editTextAutresMesures.setText(client._v_autres_mesures)

                _v_editTextDateCommande.setText(client._v_date_commande)
                _v_editTextDateLivraison.setText(client._v_date_livraison)

                _v_editTextSommeTotal.setText(client._v_somme_totale)
                _v_editTextAvance.setText(client._v_avance)
                _v_editTextReste.setText(client._v_reste)
            }
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // 验证必填字段 - Validation des champs obligatoires
        if (_v_binding._v_editTextNomPrenoms.text.toString().trim().isEmpty()) {
            _v_binding._v_inputLayoutNomPrenoms.error = "Nom et prénoms requis"
            isValid = false
        } else {
            _v_binding._v_inputLayoutNomPrenoms.error = null
        }

        val telephone = _v_binding._v_editTextTelephone.text.toString().trim()
        if (telephone.isEmpty()) {
            _v_binding._v_inputLayoutTelephone.error = "Numéro de téléphone requis"
            isValid = false
        } else if (!telephone.matches(Regex("^[0-9]{8,12}$"))) {
            _v_binding._v_inputLayoutTelephone.error = "Format invalide (8-12 chiffres)"
            isValid = false
        } else {
            _v_binding._v_inputLayoutTelephone.error = null
        }

        val dateCommande = _v_binding._v_editTextDateCommande.text.toString().trim()
        val dateLivraison = _v_binding._v_editTextDateLivraison.text.toString().trim()

        if (dateCommande.isEmpty()) {
            _v_binding._v_inputLayoutDateCommande.error = "Date de commande requise"
            isValid = false
        } else {
            _v_binding._v_inputLayoutDateCommande.error = null
        }

        if (dateLivraison.isEmpty()) {
            _v_binding._v_inputLayoutDateLivraison.error = "Date de livraison requise"
            isValid = false
        } else {
            _v_binding._v_inputLayoutDateLivraison.error = null
        }

        // 验证日期逻辑 - Validation de la logique des dates
        if (dateCommande.isNotEmpty() && dateLivraison.isNotEmpty()) {
            try {
                val commande = _v_date_format.parse(dateCommande)
                val livraison = _v_date_format.parse(dateLivraison)

                if (commande != null && livraison != null && livraison.before(commande)) {
                    _v_binding._v_inputLayoutDateLivraison.error = "Date de livraison doit être >= commande"
                    isValid = false
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Format de date invalide", Toast.LENGTH_SHORT).show()
                isValid = false
            }
        }

        val sommeTotal = _v_binding._v_editTextSommeTotal.text.toString().trim()
        val avance = _v_binding._v_editTextAvance.text.toString().trim()

        if (sommeTotal.isEmpty()) {
            _v_binding._v_inputLayoutSommeTotal.error = "Somme totale requise"
            isValid = false
        } else if (sommeTotal.toDoubleOrNull() == null || sommeTotal.toDouble() < 0) {
            _v_binding._v_inputLayoutSommeTotal.error = "Montant invalide"
            isValid = false
        } else {
            _v_binding._v_inputLayoutSommeTotal.error = null
        }

        if (avance.isEmpty()) {
            _v_binding._v_inputLayoutAvance.error = "Avance requise"
            isValid = false
        } else if (avance.toDoubleOrNull() == null || avance.toDouble() < 0) {
            _v_binding._v_inputLayoutAvance.error = "Montant invalide"
            isValid = false
        } else {
            _v_binding._v_inputLayoutAvance.error = null
        }

        return isValid
    }

    private fun saveClient() {
        _v_current_client?.apply {
            _v_nom_prenoms = _v_binding._v_editTextNomPrenoms.text.toString().trim()
            _v_numero_telephone = _v_binding._v_editTextTelephone.text.toString().trim()

            // 保存所有测量数据 - Sauvegarder toutes les données de mesure
            _v_epaule = _v_binding._v_editTextEpaule.text.toString().trim()
            _v_poitrine = _v_binding._v_editTextPoitrine.text.toString().trim()
            _v_longueur_taille = _v_binding._v_editTextLongueurTaille.text.toString().trim()
            _v_tour_ventrale = _v_binding._v_editTextTourVentrale.text.toString().trim()
            _v_hanche = _v_binding._v_editTextHanche.text.toString().trim()
            _v_longueur_corsage = _v_binding._v_editTextLongueurCorsage.text.toString().trim()
            _v_ceinture = _v_binding._v_editTextCeinture.text.toString().trim()
            _v_longueur_jupe = _v_binding._v_editTextLongueurJupe.text.toString().trim()
            _v_longueur_robe = _v_binding._v_editTextLongueurRobe.text.toString().trim()
            _v_longueur_manche = _v_binding._v_editTextLongueurManche.text.toString().trim()
            _v_taille_manche = _v_binding._v_editTextTailleManche.text.toString().trim()
            _v_longueur_pantalon = _v_binding._v_editTextLongueurPantalon.text.toString().trim()
            _v_longueur_robe_courte = _v_binding._v_editTextLongueurRobeCourte.text.toString().trim()
            _v_tour_cuisse = _v_binding._v_editTextTourCuisse.text.toString().trim()
            _v_longueur_genoux = _v_binding._v_editTextLongueurGenoux.text.toString().trim()
            _v_tour_genoux = _v_binding._v_editTextTourGenoux.text.toString().trim()
            _v_bas = _v_binding._v_editTextBas.text.toString().trim()
            _v_autres_mesures = _v_binding._v_editTextAutresMesures.text.toString().trim()

            _v_date_commande = _v_binding._v_editTextDateCommande.text.toString().trim()
            _v_date_livraison = _v_binding._v_editTextDateLivraison.text.toString().trim()

            _v_somme_totale = _v_binding._v_editTextSommeTotal.text.toString().trim()
            _v_avance = _v_binding._v_editTextAvance.text.toString().trim()
            _v_reste = _v_binding._v_editTextReste.text.toString().trim()

            if (_v_is_editing) {
                ClientRepository.updateClient(this)
                Toast.makeText(this@AddEditClientActivity, "客户信息已更新 - Client modifié", Toast.LENGTH_SHORT).show()
            } else {
                ClientRepository.addClient(this)
                Toast.makeText(this@AddEditClientActivity, "新客户已添加 - Nouveau client ajouté", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        private const val REQUEST_CAMERA = 100
    }
}
