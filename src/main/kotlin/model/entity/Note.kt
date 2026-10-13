package model.entity

import java.time.LocalDate

class Note (
    var id : Int,
    var note : Float,
    var typeEvaluation : String,
    var dateEvaluation : LocalDate
) {
}