package soft.exe.colabora.study.core.entity

data class Question(
    val id: Int,
    val question: String,
    val answers: List<Answer>
)
