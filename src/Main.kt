
fun main(){
    // creer 2 livres
    val livre1 = livre("Le Petit Prince", "Saint-Exupéry", "978-2070612758")
    val livre2 = livre("1984", "George Orwell", "978-0451524935")
    val membre1 = Membre("JOSEPH Merline", 5479 ) //crrer un membre

    // affichage initiale
    println("Etat initial du livre")
    livre1.DisplayLivre()
    livre2.DisplayLivre()

    // membre1 emprunte un livre
    println("\n EMPRUNT")
    membre1.emprunter(livre1)

    // affichage apres EMPRUNT
    println("\n etat actuel du livre")
    livre1.DisplayLivre()

    // membre1 RETOURNE le livre EMPRUNTER
    println("\n RETOUR")
    membre1.retourner(livre1)

    // affichage apres RETOUR
    println("\n etat actuel du livre")
    livre1.DisplayLivre()


// Prochaine fonctionnalite nap gen pou nou creer se kreye fonctionnalite (recherche, historique, dates d'emprunt)
    // Sauvegarder les données dans un fichier

}
