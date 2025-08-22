package kondotete.bumaye_app.model


import java.io.Serializable
import java.util.*

data class Client(
    val _v_id: String = UUID.randomUUID().toString(),
    var _v_nom_prenoms: String = "",
    var _v_numero_telephone: String = "",

    //  Mesures corporelles
    var _v_epaule: String = "",
    var _v_poitrine: String = "",
    var _v_longueur_taille: String = "",
    var _v_tour_ventrale: String = "",
    var _v_hanche: String = "",
    var _v_longueur_corsage: String = "",
    var _v_ceinture: String = "",
    var _v_longueur_jupe: String = "",
    var _v_longueur_robe: String = "",
    var _v_longueur_manche: String = "",
    var _v_taille_manche: String = "",
    var _v_longueur_pantalon: String = "",
    var _v_longueur_robe_courte: String = "",
    var _v_tour_cuisse: String = "",
    var _v_longueur_genoux: String = "",
    var _v_tour_genoux: String = "",
    var _v_bas: String = "",
    var _v_autres_mesures: String = "",

    //  Suivi commande
    var _v_date_commande: String = "",
    var _v_date_livraison: String = "",

    //  Paiement
    var _v_somme_totale: String = "",
    var _v_avance: String = "",
    var _v_reste: String = "",

    var _v_photo_path: String? = null
) : Serializable {

    fun calculateReste() {
        try {
            val total = _v_somme_totale.toDoubleOrNull() ?: 0.0
            val avance = _v_avance.toDoubleOrNull() ?: 0.0
            _v_reste = (total - avance).toString()
        } catch (e: Exception) {
            _v_reste = "0"
        }
    }
}