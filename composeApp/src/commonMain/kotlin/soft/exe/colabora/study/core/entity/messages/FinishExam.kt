package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import soft.exe.colabora.study.core.entity.QuestionAnswer

@Serializable
@SerialName("FINISH_EXAM")
data class FinishExam(
    val questionAnswers: List<QuestionAnswer>
) : Message()
