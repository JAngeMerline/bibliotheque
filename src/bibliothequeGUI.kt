import javax.swing.*
import javax.swing.table.DefaultTableModel
import java.awt.*
import javax.swing.border.EmptyBorder

class BibliothequeGUI(
    private val bibliotheque: Bibliotheque
) : JFrame() {

    private val champISBN = JTextField()
    private val champIdMembre = JTextField()
    private val tableModel = DefaultTableModel()
    private val tableLivres = JTable(tableModel)
    private val labelMembres = JLabel()

    init {
        title = "📚 Système de Bibliothèque"
        defaultCloseOperation = EXIT_ON_CLOSE
        size = Dimension(1200, 750)
        setLocationRelativeTo(null)
        initComponents()
        actualiserAffichage()
    }

    private fun initComponents() {
        layout = BorderLayout(10, 10)

        val mainPanel = JPanel(BorderLayout())
        mainPanel.border = EmptyBorder(10, 10, 10, 10)

        //TABLEAU DES LIVRES
        // Définir les colonnes
        tableModel.setColumnIdentifiers(arrayOf("№", "Titre", "Auteur", "ISBN", "Statut"))
        tableLivres.setRowHeight(30)
        tableLivres.getTableHeader().setReorderingAllowed(false)
        tableLivres.getTableHeader().setFont(Font("Arial", Font.BOLD, 14))
        tableLivres.setFont(Font("Arial", Font.PLAIN, 12))
        tableLivres.setSelectionBackground(Color(173, 216, 230))
        tableLivres.setSelectionForeground(Color.BLACK)

        // Définir les largeurs des colonnes
        tableLivres.getColumnModel().getColumn(0).preferredWidth = 50
        tableLivres.getColumnModel().getColumn(1).preferredWidth = 300
        tableLivres.getColumnModel().getColumn(2).preferredWidth = 200
        tableLivres.getColumnModel().getColumn(3).preferredWidth = 150
        tableLivres.getColumnModel().getColumn(4).preferredWidth = 100

        val scrollPane = JScrollPane(tableLivres)
        scrollPane.border = BorderFactory.createTitledBorder("📖 Catalogue des livres")
        mainPanel.add(scrollPane, BorderLayout.CENTER)

        //PANNAU D'INFORMATION (MEMBRES)
        val panneauInfo = JPanel(FlowLayout(FlowLayout.LEFT, 10, 5))
        labelMembres.font = Font("Arial", Font.BOLD, 14)
        labelMembres.foreground = Color.BLUE
        panneauInfo.add(labelMembres)
        mainPanel.add(panneauInfo, BorderLayout.NORTH)  // ← Ajouté en haut


        //PANNAU DE CONTRÔLE
        val panneauControle = JPanel(GridLayout(2, 1, 5, 5))
        panneauControle.border = BorderFactory.createTitledBorder("🎮 Actions")

        //Champs de saisie
        val ligneChamps = JPanel(FlowLayout(FlowLayout.CENTER, 15, 10))

        ligneChamps.add(JLabel("ISBN du livre:"))
        champISBN.preferredSize = Dimension(200, 35)
        champISBN.font = Font("Arial", Font.PLAIN, 14)
        ligneChamps.add(champISBN)

        ligneChamps.add(Box.createHorizontalStrut(30))

        ligneChamps.add(JLabel("ID Membre:"))
        champIdMembre.preferredSize = Dimension(100, 35)
        champIdMembre.font = Font("Arial", Font.PLAIN, 14)
        ligneChamps.add(champIdMembre)

        panneauControle.add(ligneChamps)

        // Boutons
        val ligneBoutons = JPanel(FlowLayout(FlowLayout.CENTER, 10, 10))

        val btnEmprunter = JButton("Emprunter")
        val btnRetourner = JButton(" Retourner")
        val btnAjouter = JButton(" Ajouter")
        val btnSupprimer = JButton("Supprimer")
        val btnActualiser = JButton("Actualiser")

        listOf(btnEmprunter, btnRetourner, btnAjouter, btnSupprimer, btnActualiser).forEach {
            it.preferredSize = Dimension(130, 40)
            it.font = Font("Arial", Font.BOLD, 12)
            ligneBoutons.add(it)
        }

        panneauControle.add(ligneBoutons)

        //Actions des boutons
        btnEmprunter.addActionListener { emprunterLivre() }
        btnRetourner.addActionListener { retournerLivre() }
        btnAjouter.addActionListener { ajouterLivre() }
        btnSupprimer.addActionListener { supprimerLivre() }
        btnActualiser.addActionListener { actualiserAffichage() }

        mainPanel.add(panneauControle, BorderLayout.SOUTH)
        add(mainPanel)

        //Activation des champs
        champISBN.isEnabled = true
        champIdMembre.isEnabled = true
        champISBN.requestFocus()
    }


    private fun actualiserAffichage() {
        val livres = bibliotheque.displayLivre()

        tableModel.setRowCount(0)

        if (livres.isNotEmpty()) {
            livres.forEachIndexed { index, livre ->
                val statut = if (livre.estDisponible) "Disponible" else " Emprunté"
                tableModel.addRow(arrayOf<Any>(
                    index + 1,
                    livre.titre,
                    livre.auteur,
                    livre.isbn,
                    statut
                ))
            }
        }

        //AFFICHE LE NOMBRE DE MEMBRES
        labelMembres.text = " Membres inscrits : ${bibliotheque.listerMembres().size}"
    }


    private fun emprunterLivre() {
        val isbn = champISBN.text.trim()
        val idMembre = champIdMembre.text.trim().toIntOrNull()

        if (isbn.isEmpty() || idMembre == null) {
            JOptionPane.showMessageDialog(this,
                "Veuillez entrer un ISBN et un ID membre valides",
                "Erreur", JOptionPane.ERROR_MESSAGE)
            return
        }

        val success = bibliotheque.emprunterLivre(isbn, idMembre)
        if (success) {
            JOptionPane.showMessageDialog(this,
                "✅ Emprunt réussi !",
                "Succès", JOptionPane.INFORMATION_MESSAGE)
        } else {
            JOptionPane.showMessageDialog(this,
                "Emprunt impossible. Vérifiez ISBN, ID membre ou disponibilité",
                "Erreur", JOptionPane.ERROR_MESSAGE)
        }
        actualiserAffichage()
        champISBN.text = ""
        champIdMembre.text = ""
        champISBN.requestFocus()
    }

    private fun retournerLivre() {
        val isbn = champISBN.text.trim()
        val idMembre = champIdMembre.text.trim().toIntOrNull()

        if (isbn.isEmpty() || idMembre == null) {
            JOptionPane.showMessageDialog(this,
                "Veuillez entrer un ISBN et un ID membre valides",
                "Erreur", JOptionPane.ERROR_MESSAGE)
            return
        }

        val success = bibliotheque.retourner(isbn, idMembre)
        if (success) {
            JOptionPane.showMessageDialog(this,
                "📚 Retour réussi !",
                "Succès", JOptionPane.INFORMATION_MESSAGE)
        } else {
            JOptionPane.showMessageDialog(this,
                "Retour impossible. Vérifiez que le membre a emprunté ce livre",
                "Erreur", JOptionPane.ERROR_MESSAGE)
        }
        actualiserAffichage()
        champISBN.text = ""
        champIdMembre.text = ""
        champISBN.requestFocus()
    }

    private fun ajouterLivre() {
        val dialogue = JDialog(this, "Ajouter un livre", true)
        dialogue.layout = GridLayout(0, 2, 10, 10)
        dialogue.setBounds(100, 100, 400, 200)

        val champTitre = JTextField()
        val champAuteur = JTextField()
        val champISBN = JTextField()

        dialogue.add(JLabel("Titre:"))
        dialogue.add(champTitre)
        dialogue.add(JLabel("Auteur:"))
        dialogue.add(champAuteur)
        dialogue.add(JLabel("ISBN:"))
        dialogue.add(champISBN)

        val btnValider = JButton("Ajouter")
        val btnAnnuler = JButton("Annuler")

        btnValider.addActionListener {
            val titre = champTitre.text.trim()
            val auteur = champAuteur.text.trim()
            val isbn = champISBN.text.trim()

            if (titre.isNotEmpty() && auteur.isNotEmpty() && isbn.isNotEmpty()) {
                bibliotheque.addLivre(livre(titre, auteur, isbn))
                actualiserAffichage()
                dialogue.dispose()
                JOptionPane.showMessageDialog(this, "✅ Livre ajouté !")
            } else {
                JOptionPane.showMessageDialog(dialogue, "Tous les champs sont requis")
            }
        }

        btnAnnuler.addActionListener { dialogue.dispose() }

        dialogue.add(btnValider)
        dialogue.add(btnAnnuler)
        dialogue.pack()
        dialogue.setLocationRelativeTo(this)
        dialogue.isVisible = true
    }

    private fun supprimerLivre() {
        val isbn = JOptionPane.showInputDialog(this,
            "Entrez l'ISBN du livre à supprimer :",
            "Supprimer un livre",
            JOptionPane.QUESTION_MESSAGE)

        if (isbn.isNullOrBlank()) {
            return
        }

        val confirmation = JOptionPane.showConfirmDialog(this,
            "Voulez-vous vraiment supprimer le livre avec l'ISBN : $isbn ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION)

        if (confirmation == JOptionPane.YES_OPTION) {
            val success = bibliotheque.removeLivre(isbn)
            if (success) {
                JOptionPane.showMessageDialog(this,
                    "✅ Livre supprimé avec succès !",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE)
            } else {
                JOptionPane.showMessageDialog(this,
                    "Aucun livre trouvé avec cet ISBN",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE)
            }
            actualiserAffichage()
        }
    }
}

fun main() {
    SwingUtilities.invokeLater {
        val bibliotheque = Bibliotheque()
        bibliotheque.initialiserDonneesTest()
        BibliothequeGUI(bibliotheque).isVisible = true
    }
}