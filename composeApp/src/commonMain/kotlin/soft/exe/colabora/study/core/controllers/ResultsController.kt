package soft.exe.colabora.study.core.controllers

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import soft.exe.colabora.study.core.entity.messages.ExamResults
import soft.exe.colabora.study.core.service.ConnectionClient

class ResultsController(private val connectionClient: ConnectionClient) : ViewModel() {

    val results: StateFlow<ExamResults?> = connectionClient.results

    fun closeConnection() {
        connectionClient.closeConnection()
    }

}