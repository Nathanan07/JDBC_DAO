import jdbs.BDD
import model.dao.EtudiantDAO
import model.entity.Etudiant
import java.time.LocalDate


val db = BDD()
val etudiantDAO = EtudiantDAO()



fun main() {
    db.getConnection()
    val etudiants = etudiantDAO.findAll()
    for (etudiant in etudiants) {
        println("${etudiant.id} => ${etudiant.nom}  ${etudiant.prenom}")
    }

    val test1 = etudiantDAO.findByNom("SONGCHAROENKUL")
    println(" resultat = ${test1?.nom}   ${test1?.prenom}" )




    val test2 = etudiantDAO.findByNom("TRUC")
    println(" resultat = ${test2?.nom}   ${test2?.prenom}" )




    val test3 = etudiantDAO.findByNom("' OR '1'='1")
    println(" resultat = ${test3?.nom}   ${test3?.prenom}" )

    /**
     * Le test1 et test3
     *
     *À cause de la faille de sécurité, la requête peut retourner un étudiant de la base de données, voire le premier résultat trouvé. Le résultat exact dépend de l'implémentation de findByNom et de la base de données.
     *
     * Il s'agit d'une injection SQL (SQL Injection).
     * Cette faille permet à un utilisateur de modifier une requête SQL en insérant du code SQL dans une entrée normalement destinée à contenir une simple valeur.
     *
     */

    var test4 = etudiantDAO.findById(28)
    println("${test4?.nom}")

    var stundent = Etudiant(7,"POLO", "Lupilala", "polo.lupilala@example.com", LocalDate.of(2004,7,8))

    /*
    var test5 = etudiantDAO.save(stundent)
     */

    /*
    var test6 = etudiantDAO.deleteById(13)
     */

    db.close()
}

fun afficherMenu() {


    var choix: String


    do {
        println()
        println("================================")
        println("     Application Evaluation")
        println("================================")
        println("s - Sélectionner un étudiant")
        println("a - Ajouter un étudiant")
        println("q - Quitter")
        println()


        choix = readln().lowercase()


        when (choix) {
            "s" -> selectionnerEtudiant()
            "a" -> ajouterEtudiant()
            "q" -> println("Au revoir !")
            else -> println("Choix invalide")
        }


    } while (choix != "q")
}


fun selectionnerEtudiant() {


    afficherEtudiants()


    println("Sélectionner un étudiant :")
    val id = readln().toInt()


    val etudiant = etudiantDAO.findById(id)


    if (etudiant == null) {
        println("Étudiant introuvable.")
        return
    }


    afficherEtudiant(etudiant)


    afficherMenuEtudiant(etudiant)
}


fun afficherEtudiants() {


    val etudiants = etudiantDAO.findAll()


    println()
    println("Liste des étudiants")
    println("--------------------")


    for (etudiant in etudiants) {
        println("${etudiant.id} => ${etudiant.nom} ${etudiant.prenom}")
    }
}


fun afficherEtudiant(etudiant: Etudiant) {


    println()
    println("Étudiant sélectionné")
    println("--------------------")


    println("Nom : ${etudiant.nom}")
    println("Prénom : ${etudiant.prenom}")
    println("Email : ${etudiant.email}")
    println("Date de naissance : ${etudiant.dateNaissance}")
}


fun afficherMenuEtudiant(etudiant: Etudiant) {


    println()
    println("----------------------------")
    println("1 - Supprimer")
    println("2 - Modifier")
    println("3 - Retour")


    val choix = readln()


    when (choix) {


        "1" -> supprimerEtudiant(etudiant)


        "2" -> modifierEtudiant(etudiant)


        "3" -> println("Retour au menu principal.")


        else -> println("Choix invalide.")
    }
}


fun supprimerEtudiant(etudiant: Etudiant) {


    val id = etudiant.id


    if (id == null) {
        println("Impossible de supprimer cet étudiant.")
        return
    }


    etudiantDAO.deleteById(id)


    println("Étudiant supprimé.")
}


fun modifierEtudiant(etudiant: Etudiant) {


    println("Nom :")
    etudiant.nom = readln()


    println("Prénom :")
    etudiant.prenom = readln()


    println("Email :")
    etudiant.email = readln()


    println("Date de naissance (AAAA-MM-JJ) :")
    etudiant.dateNaissance = LocalDate.parse(readln())


    etudiantDAO.save(etudiant)


    println("Étudiant modifié.")
}


fun ajouterEtudiant() {


    println()
    println("Ajout d'un étudiant")
    println("-------------------")


    println("Nom :")
    val nom = readln()


    println("Prénom :")
    val prenom = readln()


    println("Email :")
    val email = readln()


    println("Date de naissance (AAAA-MM-JJ) :")
    val dateNaissance = LocalDate.parse(readln())


    val etudiant = Etudiant(
        null,
        nom,
        prenom,
        email,
        dateNaissance
    )


    etudiantDAO.save(etudiant)


    println("Étudiant ajouté avec l'id ${etudiant.id}.")
}


fun test() {


//    val test1 = etudiantDAO.findByNom("CARON-NAGEL")
//    println(" resultat = ${test1?.nom}   ${test1?.prenom}" )
//
//    val test2 = etudiantDAO.findByNom("TRUC")
//    println(" resultat = ${test2?.nom}   ${test2?.prenom}" )
//
//    val test3 = etudiantDAO.findByNom("2 OR 1=1")
//    println(" resultat = ${test3?.nom}   ${test3?.prenom}" )


    //Test findById
    var copie = etudiantDAO.findById(1) !!
    println("copie = ${copie.nom}   ${copie.prenom}" )
    //Test save Insertion
    copie.id=null
    copie.prenom="kenny"
    copie.nom="MCCORMICK"
    copie.email = "test@email.com"


    copie = etudiantDAO.save(copie)!!
// Test save Update
    var kenny = etudiantDAO.findById(copie.id!!) !!
    println("kennyT0 = ${kenny.nom}   ${kenny.prenom} ${kenny.email}" )
    kenny.email="kenny.mccormick@southpark.com"
    kenny = etudiantDAO.save(kenny)!!
    println("kennyT1 = ${kenny.nom}   ${kenny.prenom} ${kenny.email}" )


    //test delete
    etudiantDAO.deleteById(copie.id!!)
    val kenny2 = etudiantDAO.findById(copie.id!!)
    println("kennyT3 = ${kenny2?.nom}   ${kenny2?.prenom} ${kenny2?.email}" )
}
