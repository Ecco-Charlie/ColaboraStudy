package soft.exe.colabora.study.core.entity

import colaborastudy.composeapp.generated.resources.Res
import colaborastudy.composeapp.generated.resources.base_prompt
import colaborastudy.composeapp.generated.resources.text_image_prompt
import colaborastudy.composeapp.generated.resources.text_prompt
import org.jetbrains.compose.resources.getString

data class PromptParameters(
    val numOfQuestions: Int,
    val description: String,
    val difficulty: Int,
    val totalTime: Int,
    val referencePhoto: ByteArray?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as PromptParameters

        if (numOfQuestions != other.numOfQuestions) return false
        if (difficulty != other.difficulty) return false
        if (totalTime != other.totalTime) return false
        if (description != other.description) return false
        if (!referencePhoto.contentEquals(other.referencePhoto)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = numOfQuestions
        result = 31 * result + difficulty
        result = 31 * result + totalTime
        result = 31 * result + description.hashCode()
        result = 31 * result + (referencePhoto?.contentHashCode() ?: 0)
        return result
    }

    suspend fun buildPrompt(): String {
        return getString(
            resource = Res.string.base_prompt,
            this.numOfQuestions,
            this.description,
            this.difficulty,
            (this.totalTime / this.totalTime),
            getString(if (this.referencePhoto != null) Res.string.text_image_prompt else Res.string.text_prompt)
        )
    }

}