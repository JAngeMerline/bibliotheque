
fun main(){
    
    val livre1 = livre("Le Petit Prince", "Saint-Exupéry", "978-2070612758")
    val livre2 = livre("1984", "George Orwell", "978-0451524935")
    val membre1 = Membre("JOSEPH Merline", 5479 ) 

    println("Etat initial du livre")
    livre1.DisplayLivre()
    livre2.DisplayLivre()

    println("\n EMPRUNT")
    membre1.emprunter(livre1)

    println("\n etat actuel du livre")
    livre1.DisplayLivre()

    println("\n RETOUR")
    membre1.retourner(livre1)

    println("\n etat actuel du livre")
    livre1.DisplayLivre()
}
