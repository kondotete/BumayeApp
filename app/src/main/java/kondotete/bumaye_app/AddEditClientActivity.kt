package kondotete.bumaye_app

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout
import kondotete.bumaye_app.model.Client
import kondotete.bumaye_app.repository.ClientRepository
import kondotete.bumaye_app.utils.ValidationUtils
import java.text.SimpleDateFormat
import java.util.*

class AddEditClientActivity : AppCompatActivity() {
    private var _v_current_client: Client? = null
    private var _v_is_editing = false
    private val _v_date_format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    
    // Views
    private lateinit var _v_editTextNomPrenoms: EditText
    private lateinit var _v_editTextTelephone: EditText
    private lateinit var _v_editTextDateCommande: EditText
    private lateinit var _v_editTextDateLivraison: EditText
    private lateinit var _v_editTextSommeTotal: EditText
    private lateinit var _v_editTextAvance: EditText
    private lateinit var _v_editTextReste: EditText
    private lateinit var _v_buttonSave: Button
    private lateinit var _v_buttonTakePhoto: Button
    
    // Input layouts
    private lateinit var _v_inputLayoutNomPrenoms: TextInputLayout
    private lateinit var _v_inputLayoutTelephone: TextInputLayout
    private lateinit var _v_inputLayoutDateCommande: TextInputLayout
    private lateinit var _v_inputLayoutDateLivraison: TextInputLayout
    private lateinit var _v_inputLayoutSommeTotal: TextInputLayout
    private lateinit var _v_inputLayoutAvance: TextInputLayout
    
    // Mesures fields
    private lateinit var _v_editTextEpaule: EditText
    private lateinit var _v_editTextPoitrine: EditText
    private lateinit var _v_editTextLongueurTaille: EditText
    private lateinit var _v_editTextTourVentrale: EditText
    private lateinit var _v_editTextHanche: EditText
    private lateinit var _v_editTextLongueurCorsage: EditText
    private lateinit var _v_editTextCeinture: EditText
    private lateinit var _v_editTextLongueurJupe: EditText
    private lateinit var _v_editTextLongueurRobe: EditText
    private lateinit var _v_editTextLongueurManche: EditText
    private lateinit var _v_editTextTailleManche: EditText
    private lateinit var _v_editTextLongueurPantalon: EditText
    private lateinit var _v_editTextLongueurRobeCourte: EditText
    private lateinit var _v_editTextTourCuisse: EditText
    private lateinit var _v_editTextLongueurGenoux: EditText
    private lateinit var _v_editTextTourGenoux: EditText
    private lateinit var _v_editTextBas: EditText
    private lateinit var _v_editTextAutresMesures: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_client)

        // Système de gestion des informations client

        initViews()
        checkIfEditing()
        setupToolbar()
        setupDatePickers()
        setupCalculations()
        setupButtons()

        if (_v_is_editing) {
            populateFields()
        }
    }

    private fun initViews() {
        // Main fields
        _v_editTextNomPrenoms = findViewById(R.id._v_editTextNomPrenoms)
        _v_editTextTelephone = findViewById(R.id._v_editTextTelephone)
        _v_editTextDateCommande = findViewById(R.id._v_editTextDateCommande)
        _v_editTextDateLivraison = findViewById(R.id._v_editTextDateLivraison)
        _v_editTextSommeTotal = findViewById(R.id._v_editTextSommeTotal)
        _v_editTextAvance = findViewById(R.id._v_editTextAvance)
        _v_editTextReste = findViewById(R.id._v_editTextReste)
        _v_buttonSave = findViewById(R.id._v_buttonSave)
        _v_buttonTakePhoto = findViewById(R.id._v_buttonTakePhoto)
        
        // Input layouts
        _v_inputLayoutNomPrenoms = findViewById(R.id._v_inputLayoutNomPrenoms)
        _v_inputLayoutTelephone = findViewById(R.id._v_inputLayoutTelephone)
        _v_inputLayoutDateCommande = findViewById(R.id._v_inputLayoutDateCommande)
        _v_inputLayoutDateLivraison = findViewById(R.id._v_inputLayoutDateLivraison)
        _v_inputLayoutSommeTotal = findViewById(R.id._v_inputLayoutSommeTotal)
        _v_inputLayoutAvance = findViewById(R.id._v_inputLayoutAvance)
        
        // Mesures fields
        _v_editTextEpaule = findViewById(R.id._v_editTextEpaule)
        _v_editTextPoitrine = findViewById(R.id._v_editTextPoitrine)
        _v_editTextLongueurTaille = findViewById(R.id._v_editTextLongueurTaille)
        _v_editTextTourVentrale = findViewById(R.id._v_editTextTourVentrale)
        _v_editTextHanche = findViewById(R.id._v_editTextHanche)
        _v_editTextLongueurCorsage = findViewById(R.id._v_editTextLongueurCorsage)
        _v_editTextCeinture = findViewById(R.id._v_editTextCeinture)
        _v_editTextLongueurJupe = findViewById(R.id._v_editTextLongueurJupe)
        _v_editTextLongueurRobe = findViewById(R.id._v_editTextLongueurRobe)
        _v_editTextLongueurManche = findViewById(R.id._v_editTextLongueurManche)
        _v_editTextTailleManche = findViewById(R.id._v_editTextTailleManche)
        _v_editTextLongueurPantalon = findViewById(R.id._v_editTextLongueurPantalon)
        _v_editTextLongueurRobeCourte = findViewById(R.id._v_editTextLongueurRobeCourte)
        _v_editTextTourCuisse = findViewById(R.id._v_editTextTourCuisse)
        _v_editTextLongueurGenoux = findViewById(R.id._v_editTextLongueurGenoux)
        _v_editTextTourGenoux = findViewById(R.id._v_editTextTourGenoux)
        _v_editTextBas = findViewById(R.id._v_editTextBas)
        _v_editTextAutresMesures = findViewById(R.id._v_editTextAutresMesures)
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

        // Sélecteur de date de commande
        _v_editTextDateCommande.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    _v_editTextDateCommande.setText(
                        _v_date_format.format(calendar.time)
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // Sélecteur de date de livraison
        _v_editTextDateLivraison.setOnClickListener {
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    _v_editTextDateLivraison.setText(
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

        _v_editTextSommeTotal.addTextChangedListener(textWatcher)
        _v_editTextAvance.addTextChangedListener(textWatcher)
    }

    private fun calculateReste() {
        try {
            val total = _v_editTextSommeTotal.text.toString().toDoubleOrNull() ?: 0.0
            val avance = _v_editTextAvance.text.toString().toDoubleOrNull() ?: 0.0
            val reste = total - avance
            _v_editTextReste.setText(reste.toString())
        } catch (e: Exception) {
            _v_editTextReste.setText("0")
        }
    }

    private fun setupButtons() {
        _v_buttonSave.setOnClickListener {
            if (validateForm()) {
                saveClient()
            }
        }

        _v_buttonTakePhoto.setOnClickListener {
            // Prendre une photo du client
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_CAMERA)
            }
        }
    }

    private fun populateFields() {
        _v_current_client?.let { client ->
            _v_editTextNomPrenoms.setText(client._v_nom_prenoms)
            _v_editTextTelephone.setText(client._v_numero_telephone)

            // Remplir tous les champs de mesure
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

    private fun validateForm(): Boolean {
        var isValid = true

        // Validation nom et prénoms
        val nomPrenoms = _v_editTextNomPrenoms.text.toString().trim()
        val nomPrenomsError = ValidationUtils._v_validateNomPrenoms(nomPrenoms)
        if (nomPrenomsError != null) {
            _v_inputLayoutNomPrenoms.error = nomPrenomsError
            isValid = false
        } else {
            _v_inputLayoutNomPrenoms.error = null
        }

        // Validation téléphone
        val telephone = _v_editTextTelephone.text.toString().trim()
        val telephoneError = ValidationUtils._v_validateTelephone(telephone)
        if (telephoneError != null) {
            _v_inputLayoutTelephone.error = telephoneError
            isValid = false
        } else {
            _v_inputLayoutTelephone.error = null
        }

        // Validation dates
        val dateCommande = _v_editTextDateCommande.text.toString().trim()
        val dateLivraison = _v_editTextDateLivraison.text.toString().trim()
        
        val datesError = ValidationUtils._v_validateDates(dateCommande, dateLivraison)
        if (datesError != null) {
            _v_inputLayoutDateLivraison.error = datesError
            isValid = false
        } else {
            _v_inputLayoutDateCommande.error = null
            _v_inputLayoutDateLivraison.error = null
        }

        // Validation montants
        val sommeTotal = _v_editTextSommeTotal.text.toString().trim()
        val avance = _v_editTextAvance.text.toString().trim()

        val sommeTotalError = ValidationUtils._v_validateMontant(sommeTotal, "La somme totale")
        if (sommeTotalError != null) {
            _v_inputLayoutSommeTotal.error = sommeTotalError
            isValid = false
        } else {
            _v_inputLayoutSommeTotal.error = null
        }

        val avanceError = ValidationUtils._v_validateMontant(avance, "L'avance")
        if (avanceError != null) {
            _v_inputLayoutAvance.error = avanceError
            isValid = false
        } else {
            _v_inputLayoutAvance.error = null
        }

        return isValid
    }

    private fun saveClient() {
        _v_current_client?.apply {
            _v_nom_prenoms = _v_editTextNomPrenoms.text.toString().trim()
            _v_numero_telephone = _v_editTextTelephone.text.toString().trim()

            // Sauvegarder toutes les données de mesure
            _v_epaule = _v_editTextEpaule.text.toString().trim()
            _v_poitrine = _v_editTextPoitrine.text.toString().trim()
            _v_longueur_taille = _v_editTextLongueurTaille.text.toString().trim()
            _v_tour_ventrale = _v_editTextTourVentrale.text.toString().trim()
            _v_hanche = _v_editTextHanche.text.toString().trim()
            _v_longueur_corsage = _v_editTextLongueurCorsage.text.toString().trim()
            _v_ceinture = _v_editTextCeinture.text.toString().trim()
            _v_longueur_jupe = _v_editTextLongueurJupe.text.toString().trim()
            _v_longueur_robe = _v_editTextLongueurRobe.text.toString().trim()
            _v_longueur_manche = _v_editTextLongueurManche.text.toString().trim()
            _v_taille_manche = _v_editTextTailleManche.text.toString().trim()
            _v_longueur_pantalon = _v_editTextLongueurPantalon.text.toString().trim()
            _v_longueur_robe_courte = _v_editTextLongueurRobeCourte.text.toString().trim()
            _v_tour_cuisse = _v_editTextTourCuisse.text.toString().trim()
            _v_longueur_genoux = _v_editTextLongueurGenoux.text.toString().trim()
            _v_tour_genoux = _v_editTextTourGenoux.text.toString().trim()
            _v_bas = _v_editTextBas.text.toString().trim()
            _v_autres_mesures = _v_editTextAutresMesures.text.toString().trim()

            _v_date_commande = _v_editTextDateCommande.text.toString().trim()
            _v_date_livraison = _v_editTextDateLivraison.text.toString().trim()

            _v_somme_totale = _v_editTextSommeTotal.text.toString().trim()
            _v_avance = _v_editTextAvance.text.toString().trim()
            _v_reste = _v_editTextReste.text.toString().trim()

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
