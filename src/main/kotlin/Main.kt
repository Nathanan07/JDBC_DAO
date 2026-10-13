import jdbs.BDD

val db = BDD()




fun main() {


    db.getConnection()




    db.close()
}
