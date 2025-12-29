package soft.exe.colabora.study.core.service

class QuestionsClient(val numOfQuestions: Int) {
    private var currentIndexQuestion: Int = -1

    fun nextQuestion(): Int {
        this.currentIndexQuestion += 1
        return this.currentIndexQuestion
    }

}