class Membre(
    val nom: String,
    val Id: Int
) {
    val emprunts: MutableList<livre> = mutableListOf() 

    fun emprunter(livre: livre): Boolean {
        return if (livre.estDisponible) {
            emprunts.add(livre)
            livre.estDisponible = false
            println("$nom a emprunté ${livre.titre}")
            true
        } else {
            println("${livre.titre} n'est pas disponible")
            false
        }
    }
    fun retourner(livre: livre): Boolean {
        return if (emprunts.contains(livre)) {
            emprunts.remove(livre)
            livre.estDisponible = true
            println("$nom a retourné ${livre.titre}")
            true
        } else {
            println(" $nom n'a pas emprunté ${livre.titre}")
            false
        }
    }
}
