package model.dao

import db
import jdbs.BDD
import model.entity.Etudiant

class MatièreDAO (val bdd: BDD = db) {

    fun findById(id: Int): Etudiant? {
        var result: Etudiant? = null
        val sql = "SELECT * FROM etudiant WHERE id = ?"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        requetePreparer.setInt(1, id) // insere la valeur de l'id dans la requete preparer
        val resultatRequete = bdd.executePreparedStatement(requetePreparer)


        if (resultatRequete != null && resultatRequete.next()) {
            val nom = resultatRequete.getString("nom")
            val prenom = resultatRequete.getString("prenom")
            val email = resultatRequete.getString("email")
            val dateNaissance = resultatRequete.getDate("date_naissance")
            result = Etudiant(id,nom,prenom,email,dateNaissance.toLocalDate())
        }


        requetePreparer.close()
        return result
    }

    fun findAll(): MutableList<Etudiant> {
        val result = mutableListOf<Etudiant>()
        val sql = "SELECT * FROM etudiant"
        val requetePreparer = bdd.connectionBDD!!.prepareStatement(sql)
        val resultatRequete = bdd.executePreparedStatement(requetePreparer)


        if (resultatRequete != null) {
            while (resultatRequete.next()) {
                val id = resultatRequete.getInt("id")
                val nom = resultatRequete.getString("nom")
                val prenom = resultatRequete.getString("prenom")
                val email = resultatRequete.getString("email")
                val dateNaissance = resultatRequete.getDate("date_naissance")
                result.add(Etudiant(id,nom,prenom,email,dateNaissance.toLocalDate()))
            }
        }


        requetePreparer.close()
        return result
    }

}