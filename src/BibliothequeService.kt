
interface BibliothequeService { //roles
    fun addLivre(livre: livre)
    fun removeLivre(isbn: String):Boolean
    fun searchLivre(livre: String): List<livre>
    fun displayLivre(): List<livre>
    fun displayLivreDisponible(): List<livre>
    fun emprunterLivre(isbn:String, idMember: Int): Boolean
    fun retourner(isbn: String, idMember: Int): Boolean
}

class Bibliotheque : BibliothequeService {
    private val livres = mutableListOf<livre>()
    private val membres = mutableListOf<Membre>()
    override fun addLivre(livre: livre) {
        livres.add(livre)
        println("\n added livre")
    }

    override fun removeLivre(isbn: String): Boolean {
        val livre = livres.find { it.isbn == isbn }
        return if (livre != null) {
            livres.remove(livre)
            println("\n removed livre ${livre.titre}")
            true
        } else {
            println("\n Livre not found")
            false
        }
    }
    override fun searchLivre(livre: String): List<livre> {
        return livres.filter { it.titre.contains(livre, ignoreCase = true) }
    }

    override fun displayLivre(): List<livre> {
     return livres.toList()
    }

    override fun  displayLivreDisponible(): List<livre> {
        return livres.filter { it.estDisponible }
    }


    override fun emprunterLivre(isbn: String, idMember: Int): Boolean {
        val livre = livres.find { it.isbn == isbn }
        val membre= membres.find{it.Id==idMember}

        return if (livre!= null && membre!= null && livre.estDisponible ) {
            membre.emprunter(livre)
        } else{
            println("Emprunt indisponible")
            false
        }
    }

    override fun retourner(isbn: String, idMember: Int): Boolean {
        val livre= livres.find { it.isbn == isbn }
        val membre = membres.find { it.Id==idMember}
        return if (livre != null && membre != null && !livre.estDisponible) {
            membre.retourner(livre)
        } else{
            println("Retour impossible")
            false
        }
    }

    fun addMembre(membre: Membre) {
        membres.add(membre)
    }

    fun listerMembres(): List<Membre> {
        return membres.toList()
    }

    fun initialiserDonneesTest() {
        val livresInit = listOf(
            livre("Le Petit Prince", "Saint-Exupéry", "978-2070612758"),
            livre("1984", "George Orwell", "978-0451524935"),
            livre("Harry Potter à l'école des sorciers", "J.K. Rowling", "978-2070584628"),
            livre("L'Étranger", "Albert Camus", "978-2070360024"),
            livre("Les Misérables", "Victor Hugo", "978-2253096337"),
            livre("Le Comte de Monte-Cristo", "Alexandre Dumas", "978-2253005568"),
            livre("Madame Bovary", "Gustave Flaubert", "978-2070413117"),
            livre("Voyage au bout de la nuit", "Louis-Ferdinand Céline", "978-2070360055"),
            livre("Bel-Ami", "Guy de Maupassant", "978-2070447549"),
            livre("Le Rouge et le Noir", "Stendhal", "978-2080705095"),
            livre("Germinal", "Émile Zola", "978-2070413070"),
            livre("La Peste", "Albert Camus", "978-2070360420"),
            livre("Le Grand Meaulnes", "Alain-Fournier", "978-2070361762"),
            livre("L'Appel de la forêt", "Jack London", "978-2070336579"),
            livre("Orgueil et Préjugés", "Jane Austen", "978-2080711911"),
            livre("Le Meilleur des mondes", "Aldous Huxley", "978-2070368228"),
            livre("La Ferme des animaux", "George Orwell", "978-2070368229"),
            livre("Les Trois Mousquetaires", "Alexandre Dumas", "978-2253005582"),
            livre("Notre-Dame de Paris", "Victor Hugo", "978-2253096344"),
            livre("Le Silence de la mer", "Vercors", "978-2253012351")
        )

        livresInit.forEach { addLivre(it) }

        addMembre(Membre("Merline", 1))
        addMembre(Membre("Bernide", 2))
        addMembre(Membre("Alice", 1))
        addMembre(Membre("Bob", 2))
        addMembre(Membre("Charlie", 3))
        addMembre(Membre("Diana", 4))
        addMembre(Membre("Emma", 5))
        addMembre(Membre("Felix", 6))
        addMembre(Membre("Gabrielle", 7))
        addMembre(Membre("Hugo", 8))
        addMembre(Membre("Iris", 9))
        addMembre(Membre("Julien", 10))
        addMembre(Membre("Karima", 11))
        addMembre(Membre("Lucas", 12))
        addMembre(Membre("Manon", 13))
        addMembre(Membre("Nicolas", 14))
        addMembre(Membre("Oceane", 15))
        addMembre(Membre("Pierre", 16))
        addMembre(Membre("Quentin", 17))
        addMembre(Membre("Rachel", 18))
        addMembre(Membre("Samuel", 19))
        addMembre(Membre("Tatiana", 20))
    }


}