package soft.exe.colabora.study.core.repository

import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.not_possible_obtain_response
import colaborastudy.composeapp.generated.resources.unknow_error
import dev.shreyaspatil.ai.client.generativeai.GenerativeModel
import dev.shreyaspatil.ai.client.generativeai.type.Content
import dev.shreyaspatil.ai.client.generativeai.type.GenerateContentResponse
import dev.shreyaspatil.ai.client.generativeai.type.content
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.getString
import soft.exe.colabora.study.BuildKonfig
import soft.exe.colabora.study.core.entity.PromptParameters
import soft.exe.colabora.study.core.entity.Question

class GeminiQuestionsRepository(apiKey: String) : QuestionsRepository {

    private val tunnel: GenerativeModel = GenerativeModel(
        modelName = BuildKonfig.GEMINI_MODEL,
        apiKey = apiKey
    )

    override suspend fun getQuestions(prompt: PromptParameters): List<Question> {
        val textPrompt = prompt.buildPrompt().trim()

        val inputContent: Content = content {
            text(textPrompt)
            prompt.referencePhoto?.let { image(it) }
        }
        val res: GenerateContentResponse = tunnel.generateContent(inputContent)
        val promptFeedback = res.promptFeedback
        if (promptFeedback != null) {
            val reason = promptFeedback.blockReason?.name ?: getString(Res.string.unknow_error)
            throw Exception(reason)
        }

        val text = res.text ?: throw Exception(getString(Res.string.not_possible_obtain_response))

        val cleanText = text
            .trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
            .trim()

        val questions: List<Question> = Json.decodeFromString<List<Question>>(cleanText)
        return questions
    }

}