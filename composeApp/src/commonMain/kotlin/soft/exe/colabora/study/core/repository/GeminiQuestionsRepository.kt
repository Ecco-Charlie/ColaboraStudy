package soft.exe.colabora.study.core.repository

import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import soft.exe.colabora.study.BuildKonfig
import soft.exe.colabora.study.core.entity.Question

class GeminiQuestionsRepository(apiKey: String) : QuestionsRepository {

    private val tunnel: GenerativeModel = GenerativeModel(
        modelName = BuildKonfig.GEMINI_MODEL,
        apiKey = apiKey
    )

    override suspend fun getQuestions(prompt: String): List<Question> {
        TODO("Not yet implemented")
    }

}