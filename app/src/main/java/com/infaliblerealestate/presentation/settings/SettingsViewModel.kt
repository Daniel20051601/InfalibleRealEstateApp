package com.infaliblerealestate.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infaliblerealestate.dominio.usecase.usuarios.GetUsuarioActualUseCase
import com.infaliblerealestate.dominio.usecase.usuarios.LogOutUseCase
import com.infaliblerealestate.worker.WorkManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val workManagerHelper: WorkManagerHelper,
    private val getUsuarioActualUseCase: GetUsuarioActualUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsUiState())
    val state: StateFlow<SettingsUiState> = _state.asStateFlow()

    init {
        loadUsuario()
    }
    fun onEvent(event: SettingsUiEvent) {
        when (event) {
            is SettingsUiEvent.LoadUsuario -> {
                loadUsuario()
            }
            is SettingsUiEvent.Logout -> {
                logout()
            }
        }
    }

    fun loadUsuario() {
        viewModelScope.launch {
            getUsuarioActualUseCase().collect { usuario ->
                _state.update { state -> state.copy(
                    usuario = usuario
                ) }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            workManagerHelper.cancelSyncWork()
            logOutUseCase()
        }
    }
}