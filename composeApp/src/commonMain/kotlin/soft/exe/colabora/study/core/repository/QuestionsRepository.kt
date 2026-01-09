package soft.exe.colabora.study.core.repository

import soft.exe.colabora.study.core.entity.PromptParameters
import soft.exe.colabora.study.core.entity.Question

interface QuestionsRepository {

    suspend fun getQuestions(prompt: PromptParameters): List<Question>

}