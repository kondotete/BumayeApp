package kondotete.bumaye_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kondotete.bumaye_app.databinding.ItemClientBinding
import kondotete.bumaye_app.model.Client

class ClientAdapter(
    private val _v_on_client_click: (Client) -> Unit
) : ListAdapter<Client, ClientAdapter.ClientViewHolder>(ClientDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val binding = ItemClientBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ClientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ClientViewHolder(
        private val _v_binding: ItemClientBinding
    ) : RecyclerView.ViewHolder(_v_binding.root) {

        fun bind(client: Client) {
            _v_binding.apply {
                _v_textViewNomPrenom.text = client._v_nom_prenoms
                _v_textViewDateCommande.text = "Commande: ${client._v_date_commande}"
                _v_textViewDateLivraison.text = "Livraison: ${client._v_date_livraison}"

                // 显示付款信息 - Affichage des informations de paiement
                val avance = client._v_avance.toDoubleOrNull() ?: 0.0
                val total = client._v_somme_totale.toDoubleOrNull() ?: 0.0
                _v_textViewMontant.text = "${avance.toInt()} / ${total.toInt()} FCFA"

                root.setOnClickListener {
                    _v_on_client_click(client)
                }
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
