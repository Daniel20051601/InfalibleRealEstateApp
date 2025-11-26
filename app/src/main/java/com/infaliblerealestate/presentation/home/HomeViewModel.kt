package com.infaliblerealestate.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infaliblerealestate.data.remote.Resource
import com.infaliblerealestate.dominio.usecase.propiedades.GetPropiedadUseCase
import com.infaliblerealestate.dominio.usecase.propiedades.GetPropiedadesUseCase
import com.infaliblerealestate.dominio.usecase.usuarios.GetUsuarioActualUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val getPropiedadUseCase: GetPropiedadUseCase,
    val getPropiedadesUseCase: GetPropiedadesUseCase,
    val getUsuarioActualUseCase: GetUsuarioActualUseCase
    ) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        loadLastProperties()
        val categorias = listOf("Casa", "Departamento", "Villa", "Penthouse", "Solar", "Local Comercial")
        viewModelScope.launch {
            _state.update { it.copy(categorias = categorias) }

        }


    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.UserMessageShown -> { _state.update { it.copy(userMessage = null) } }
            is HomeUiEvent.ShowSheet -> { _state.update { it.copy(showSheet = true) } }
            is HomeUiEvent.HideSheet -> { _state.update { it.copy(showSheet = false) } }
            is HomeUiEvent.LoadPropiedad -> {loadProperti(id = event.id)}
            is HomeUiEvent.CategoriaSeleccionada -> { _state.update { it.copy(categoriaSeleccionada = event.categoria) } }
        }
    }

    fun loadLastProperties(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getPropiedadesUseCase().collect { todasLasPropiedades ->
                val ultimasPropiedades = todasLasPropiedades.take(5)
                _state.update {
                    it.copy(
                        isLoading = false,
                        propiedades = ultimasPropiedades
                    )
                }
            }
        }

    }

    fun loadProperti(id: Int){
        viewModelScope.launch {
            _state.update{estate ->
                estate.copy(
                    propiedad = null,
                )
            }
            when(val propiedad = getPropiedadUseCase(id)){
                is Resource.Success -> {
                    propiedad.data?.let {
                        _state.update { state -> state.copy(
                            propiedad = it,
                            userMessage = "",
                            showSheet = true,
                            isLoading = false,
                        )
                        }
                    }
                }
                is Resource.Error -> {
                    _state.update { state -> state.copy(userMessage = "Error al cargar la propiedad") }
                }
                else -> {
                    _state.update { state -> state.copy(userMessage = "Error desconocido") }
                }
            }
        }

    }



}
