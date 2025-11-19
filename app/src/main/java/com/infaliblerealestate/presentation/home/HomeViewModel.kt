package com.infaliblerealestate.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infaliblerealestate.data.remote.Resource
import com.infaliblerealestate.dominio.model.Usuarios
import com.infaliblerealestate.dominio.usecase.GetUsuarioUseCase
import com.infaliblerealestate.dominio.usecase.GetUsuariosUseCase
import com.infaliblerealestate.dominio.usecase.SaveUsuarioUseCase
import com.infaliblerealestate.presentation.validation.UsuarioValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUsuarioUseCase: GetUsuarioUseCase,
    private val getUsuariosUseCase: GetUsuariosUseCase,
    private val saveUsuarioUseCase: SaveUsuarioUseCase,
    private val validator: UsuarioValidator
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _effects = MutableSharedFlow<HomeEffect>()
    val effects: SharedFlow<HomeEffect> = _effects

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.LoadUser -> loadData(event.usuarioId)
            is HomeUiEvent.UserNameChanged -> _state.update { it.copy(userName = event.userName) }
            is HomeUiEvent.PasswordChanged -> _state.update { it.copy(password = event.password) }
            is HomeUiEvent.Save -> onSave()
            is HomeUiEvent.Logout -> onLogout()
            is HomeUiEvent.UserMessageShown -> _state.update { it.copy(userMessage = null) }
            is HomeUiEvent.showDialogEdit -> onShowDialogEdit()
            is HomeUiEvent.hideDialogEdit -> _state.update { it.copy(showDialog = false) }
        }
    }

    private fun loadData(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingUser = true, isLoadingList = true, usuarioId = id) }

            when (val userRes = getUsuarioUseCase(id)) {
                is Resource.Success -> _state.update {
                    it.copy(
                        userName = userRes.data?.userName.orEmpty(),
                        password = userRes.data?.password.orEmpty(),
                        isLoadingUser = false
                    )
                }
                is Resource.Error -> _state.update {
                    it.copy(
                        userMessage = userRes.message ?: "Error cargando usuario",
                        isLoadingUser = false
                    )
                }
                else -> _state.update { it.copy(isLoadingUser = false) }
            }

            try {
                val lista = getUsuariosUseCase().first()
                val others = lista.filter { u -> u.usuarioId != id }
                _state.update {
                    it.copy(
                        listUsuarios = others,
                        isLoadingList = false
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        userMessage = "Error cargando lista: ${e.message}",
                        isLoadingList = false
                    )
                }
            }
        }
    }

    private fun onSave() {
        viewModelScope.launch {
            val nombreVal = validator.validateNombre(_state.value.userName, _state.value.usuarioId)
            val passVal = validator.validatePassword(_state.value.password)

            _state.update {
                it.copy(
                    userNameError = if (!nombreVal.isValid) nombreVal.errorMessage else null,
                    passwordError = if (!passVal.isValid) passVal.errorMessage else null
                )
            }

            if (nombreVal.isValid && passVal.isValid) {
                val id = _state.value.usuarioId ?: return@launch
                _state.update { it.copy(isLoadingUser = true) }

                val usuario = Usuarios(
                    usuarioId = id,
                    userName = _state.value.userName,
                    password = _state.value.password
                )

                when (val res = saveUsuarioUseCase(id, usuario)) {
                    is Resource.Success -> _state.update {
                        it.copy(
                            userMessage = "Usuario actualizado correctamente",
                            isLoadingUser = false,
                            showDialog = false,
                            userNameError = null,
                            passwordError = null
                        )
                    }
                    is Resource.Error -> _state.update {
                        it.copy(
                            userMessage = res.message ?: "Error al actualizar usuario",
                            isLoadingUser = false
                        )
                    }
                    else -> _state.update {
                        it.copy(
                            userMessage = "Error desconocido",
                            isLoadingUser = false
                        )
                    }
                }
            }
        }
    }

    private fun onShowDialogEdit() {
        _state.update {
            it.copy(
                showDialog = true,
                userNameError = null,
                passwordError = null
            )
        }
    }

    private fun onLogout() {
        viewModelScope.launch {
            _effects.emit(HomeEffect.NavigateLogin)
        }
    }
}
