package kondotete.bumaye_app.repository


import kondotete.bumaye_app.model.Client

object ClientRepository {
    private val _v_clients_list = mutableListOf<Client>()

    //  Obtenir tous les clients
    fun getAllClients(): List<Client> = _v_clients_list.toList()

    //  Ajouter client
    fun addClient(client: Client) {
        client.calculateReste()
        _v_clients_list.add(client)
    }

    // Modifier client
    fun updateClient(client: Client) {
        client.calculateReste()
        val index = _v_clients_list.indexOfFirst { it._v_id == client._v_id }
        if (index != -1) {
            _v_clients_list[index] = client
        }
    }

    //  Supprimer client
    fun deleteClient(clientId: String) {
        _v_clients_list.removeAll { it._v_id == clientId }
    }

    //  Obtenir client par ID
    fun getClientById(id: String): Client? {
        return _v_clients_list.find { it._v_id == id }
    }

    //  Rechercher clients
    fun searchClients(query: String): List<Client> {
        return _v_clients_list.filter {
            it._v_nom_prenoms.contains(query, ignoreCase = true) ||
                    it._v_numero_telephone.contains(query)
        }
    }
}