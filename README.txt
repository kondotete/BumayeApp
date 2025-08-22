# Bumaye App 
## Description
Bumaye App est une application Android développée pour la maison de haute couture "Maison BUMAYE". Elle permet la gestion complète des mesures des clients, incluant l'enregistrement, la consultation, la modification et la suppression des fiches clients.

##  - Choix de Design

### Architecture
- **Pattern**: Repository Pattern pour la gestion des données
- **UI**: XML layouts avec Material Design 3
- **Navigation**: Activity-based navigation avec Intent

### Choix Techniques
1. **Stockage en mémoire**: Utilisation d'une liste mutable dans un objet singleton (ClientRepository)
2. **Validation**: Système de validation robuste avec messages d'erreur en temps réel
3. **Interface utilisateur**: Design moderne avec Material Design Cards et couleurs cohérentes

### Fonctionnalités Implémentées

#### ✅ Fonctionnalités Principales
1. Ajout de clients: Formulaire complet avec tous les champs requis
2. Liste des clients: Affichage avec informations essentielles (nom, dates, paiement)
3. Détails du client: Vue complète de toutes les mesures enregistrées
4. Modification: Édition de tous les champs d'un client existant
5. Suppression: Suppression avec confirmation (optionnel)
6. Recherche: Recherche par nom ou numéro de téléphone

#### ✅ Validations Appliquées
- Nom et prénoms obligatoires (minimum 2 caractères)
- Numéro de téléphone obligatoire (8-12 chiffres uniquement)
- Dates obligatoires avec validation de cohérence (livraison >= commande)
- Montants obligatoires et positifs
- Calcul automatique du reste à payer
- Messages d'erreur contextuels

#### ✅ Fonctionnalités Bonus
- Interface de prise de photo (structure prête)
- Recherche en temps réel
- Design responsive et moderne