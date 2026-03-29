package kondotete.bumaye_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kondotete.bumaye_app.R
import kondotete.bumaye_app.model.Client

class ClientAdapter(
    private val _v_on_client_click: (Client) -> Unit
) : ListAdapter<Client, ClientAdapter.ClientViewHolder>(ClientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ClientViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        
        private val _v_textViewNomPrenom: TextView = itemView.findViewById(R.id._v_textViewNomPrenom)
        private val _v_textViewDateCommande: TextView = itemView.findViewById(R.id._v_textViewDateCommande)
        private val _v_textViewDateLivraison: TextView = itemView.findViewById(R.id._v_textViewDateLivraison)
        private val _v_textViewMontant: TextView = itemView.findViewById(R.id._v_textViewMontant)

        fun bind(client: Client) {
            _v_textViewNomPrenom.text = client._v_nom_prenoms
            _v_textViewDateCommande.text = "Commande: ${client._v_date_commande}"
            _v_textViewDateLivraison.text = "Livraison: ${client._v_date_livraison}"

            // Affichage des informations de paiement
            val avance = client._v_avance.toDoubleOrNull() ?: 0.0
            val total = client._v_somme_totale.toDoubleOrNull() ?: 0.0
            _v_textViewMontant.text = "${avance.toInt()} / ${total.toInt()} FCFA"

            itemView.setOnClickListener {
                _v_on_client_click(client)
            }
        }
    }

    private class ClientDiffCallback : DiffUtil.ItemCallback<Client>() {
        override fun areItemsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem._v_id == newItem._v_id
        }

        override fun areContentsTheSame(oldItem: Client, newItem: Client): Boolean {
            return oldItem == newItem
        }
    }
}
