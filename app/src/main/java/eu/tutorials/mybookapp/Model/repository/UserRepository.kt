package eu.tutorials.mybookapp.Model.repository

import eu.tutorials.mybookapp.View.CredentialPass
import eu.tutorials.mybookapp.View.Credentials
import eu.tutorials.mybookapp.View.RegisterInfo
import eu.tutorials.mybookapp.Model.data.CredentialResponse
import eu.tutorials.mybookapp.Model.data.service
import eu.tutorials.mybookapp.View.Info
import eu.tutorials.mybookapp.Model.data.UserResponse

class UserRepository {
    suspend fun login(credentials: Credentials): CredentialResponse {
        return service.login(credentials)
    }
    suspend fun register( registerInfo: RegisterInfo) {
        service.register(registerInfo)
    }
    suspend fun changeInfo(info: Info, token: String ) {
        service.changeInfo(info, "Bearer ${token}")
    }
    suspend fun changePass(credentialPass: CredentialPass, token: String ) {
        service.changePass(credentialPass, "Bearer ${token}")
    }
    suspend fun getCurrentUser(token: String): UserResponse {
        return service.getCurrentUser("Bearer ${token}")
    }

}