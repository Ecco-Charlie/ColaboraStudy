package soft.exe.colabora.study.core.service

class QuestionsClient(private val numOfQuestions: Int) {

    private var currentQuestion: Int = -1

    fun nextQuestion(): Int {
        this.currentQuestion += 1
        return this.currentQuestion
    }

}