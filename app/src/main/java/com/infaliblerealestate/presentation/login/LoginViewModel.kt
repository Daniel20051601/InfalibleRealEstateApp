package com.infaliblerealestate.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infaliblerealestate.data.remote.Resource
import com.infaliblerealestate.dominio.model.Usuarios
import com.infaliblerealestate.dominio.usecase.SaveUsuarioUseCase
import com.infaliblerealestate.presentation.validation.UsuarioValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val saveUsuarioUseCase: SaveUsuarioUseCase,
    val validator: UsuarioValidator,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<LoginEffect>()
    val effects: SharedFlow<LoginEffect> = _effects

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.userNameChanged -> _state.update { it.copy(userName = event.userName) }
            is LoginUiEvent.passwordChanged -> _state.update { it.copy(password = event.password) }
            is LoginUiEvent.loginModeClicked -> _state.update { it.copy(isRegistering = false) }
            is LoginUiEvent.submitLogin -> onSubmitLogin()
            is LoginUiEvent.registerModeClicked -> onRegisterModeClicked()
            is LoginUiEvent.submitRegistration -> onSubmitRegistration()
            is LoginUiEvent.userMessageShown -> _state.update { it.copy(userMessage = "") }
        }
    }

    fun onSubmitLogin() {
        viewModelScope.launch {
            if (_state.value.userName.isBlank() || _state.value.password.isBlank()) {
                _state.update { it.copy(userMessage = "Debe completar todos los campos") }
                return@launch
            }
            _state.update { it.copy(isLoading = true) }
            val validar = validator.validateCredenciales(_state.value.userName, _state.value.password)
            if (validar.isValid && validar.usuarioId != null) {
                _state.update {
                    it.copy(
                        usuarioId = validar.usuarioId,
                        isLoading = false,
                        userMessage = "Validacion exitosa"
                    )
                }
                _effects.emit(LoginEffect.NavigateHome(validar.usuarioId))
            } else {
                _state.update {
                    it.copy(
                        userMessage = "Error al validar las credenciales",
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onSubmitRegistration() {
        viewModelScope.launch {
            val nombreVal = validator.validateNombre(_state.value.userName)
            val passVal = validator.validatePassword(_state.value.password)

            _state.update {
                it.copy(
                    userNameError = if (!nombreVal.isValid) nombreVal.errorMessage else null,
                    passwordError = if (!passVal.isValid) passVal.errorMessage else null
                )
            }

            if (nombreVal.isValid && passVal.isValid) {
                val usuario =
                    Usuarios(_state.value.usuarioId, _state.value.userName, _state.value.password)
                val id = _state.value.usuarioId ?: 0
                when (val res = saveUsuarioUseCase(id, usuario)) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            userName = "",
                            password = "",
                            userNameError = null,
                            passwordError = null,
                            isRegistering = false,
                            isLoading = false,
                            userMessage = "Usuario registrado correctamente"
                        )
                    }
                    is Resource.Error -> _state.update {
                        it.copy(
                            userMessage = res.message ?: "Error al registrarse",
                            isLoading = false
                        )
                    }
                    else -> _state.update {
                        it.copy(
                            userMessage = "Error desconocido",
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onRegisterModeClicked() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    userName = "",
                    password = "",
                    userNameError = null,
                    passwordError = null,
                    isRegistering = !it.isRegistering,
                    isLoading = false,
                    userMessage = ""
                )
            }
        }
    }
}
