package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import soft.exe.colabora.study.core.entity.QuestionAnswer

@Serializable
@SerialName("EXAM_FINISHED")
data class ExamFinished(
    val questionAnswers: List<QuestionAnswer>
) : Message()
