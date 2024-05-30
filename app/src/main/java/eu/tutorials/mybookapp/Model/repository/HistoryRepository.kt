package eu.tutorials.mybookapp.Model.repository

import eu.tutorials.mybookapp.ViewModel.HistoryViewModel
import eu.tutorials.mybookapp.Model.data.HistoryResponse
import eu.tutorials.mybookapp.Model.data.service

class HistoryRepository {
    suspend fun getHistoryByToken(token: String): HistoryResponse {
        return service.getHistoryByToken("Bearer ${token}")
    }
    suspend fun addEventToHistory(eventBody: HistoryViewModel.EventBody, token:String) {
        service.addEventToHistory(eventBody,"Bearer ${token}")
    }
}