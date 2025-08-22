package kondotete.bumaye_app.repository


import kondotete.bumaye_app.model.Client

object ClientRepository {
    private val _v_clients_list = mutableListOf<Client>()

    // 获取所有客户 - Obtenir tous les clients
    fun getAllClients(): List<Client> = _v_clients_list.toList()

    // 添加客户 - Ajouter client
    fun addClient(client: Client) {
        client.calculateReste()
        _v_clients_list.add(client)
    }

    // 更新客户 - Modifier client
    fun updateClient(client: Client) {
        client.calculateReste()
        val index = _v_clients_list.indexOfFirst { it._v_id == client._v_id }
        if (index != -1) {
            _v_clients_list[index] = client
        }
    }

    // 删除客户 - Supprimer client
    fun deleteClient(clientId: String) {
        _v_clients_list.removeAll { it._v_id == clientId }
    }

    // 根据ID获取客户 - Obtenir client par ID
    fun getClientById(id: String): Client? {
        return _v_clients_list.find { it._v_id == id }
    }

    // 搜索客户 - Rechercher clients
    fun searchClients(query: String): List<Client> {
        return _v_clients_list.filter {
            it._v_nom_prenoms.contains(query, ignoreCase = true) ||
                    it._v_numero_telephone.contains(query)
        }
    }
}