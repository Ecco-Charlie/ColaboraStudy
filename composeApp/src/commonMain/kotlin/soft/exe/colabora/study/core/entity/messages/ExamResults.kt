package soft.exe.colabora.study.core.entity.messages

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import soft.exe.colabora.study.core.entity.QuestionResult

@Serializable
@SerialName("EXAM_RESULTS")
data class ExamResults(
    val results: List<QuestionResult>,
    val score: Int,
    val totalNumOfQuestions: Int
) : Message()