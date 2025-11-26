package com.infaliblerealestate.presentation.carrito

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infaliblerealestate.data.remote.Resource
import com.infaliblerealestate.dominio.usecase.carrito.GetCarritoByIdUseCase
import com.infaliblerealestate.dominio.usecase.propiedades.GetPropiedadUseCase
import com.infaliblerealestate.dominio.usecase.usuarios.GetUsuarioActualUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarritoViewModel @Inject constructor(
    private val getCarritoByIdUseCase: GetCarritoByIdUseCase,
    private val getUsuarioActualUseCase: GetUsuarioActualUseCase,
    private val getPropiedadUseCase: GetPropiedadUseCase
): ViewModel() {
    private val _state = MutableStateFlow(CarritoUiState())
    val state: StateFlow<CarritoUiState> = _state.asStateFlow()

    init {
        loadCarrito()
    }

    fun onEvent(event: CarritoUiEvent) {
        when (event) {
            is CarritoUiEvent.UserMessageShown -> {
                _state.update { it.copy(userMessage = null) }
            }
            is CarritoUiEvent.LoadPropiedad -> {
                loadPropiedad(event.id)
            }
            is CarritoUiEvent.HideSheet -> {
                _state.update {
                    it.copy(showSheet = false)
                }
            }
            is CarritoUiEvent.ShowSheet -> {
                _state.update {
                    it.copy(showSheet = true)
                }
            }
        }
    }

    fun loadCarrito(){
        viewModelScope.launch{
            _state.update { it.copy(isLoading = true) }

            getUsuarioActualUseCase().collect { usuario ->
                _state.update { it.copy(id = usuario?.id) }

                usuario?.id?.let { userId ->
                    when(val carrito = getCarritoByIdUseCase(userId)){
                        is Resource.Success -> {
                            carrito.data?.let {
                                _state.update { state ->
                                    state.copy(
                                        isLoading = false,
                                        carrito = it,
                                        items = it.items
                                    )
                                }
                            }
                        }
                        is Resource.Error -> {
                            _state.update { state ->
                                state.copy(
                                    isLoading = false,
                                    userMessage = carrito.message
                                )
                            }
                        }
                        else -> {
                            _state.update { state ->
                                state.copy(
                                    isLoading = false,
                                    userMessage = "Error desconocido"
                                )
                            }
                        }
                    }
                } ?: run {
                    _state.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun loadPropiedad(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(
                showSheet = true,
                isLoadingSheet = true) }

            when(val propiedad = getPropiedadUseCase(id)){
                is Resource.Success -> {
                    propiedad.data?.let {
                        _state.update { state ->
                            state.copy(
                                showSheet = true,
                                isLoadingSheet = false,
                                propiedad = it,
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _state.update { state ->
                        state.copy(
                            isLoadingSheet = false,
                            userMessage = propiedad.message
                        )
                    }
                }
                else -> {
                    _state.update { state ->
                        state.copy(
                            isLoadingSheet = false,
                            userMessage = "Error desconocido"
                        )
                    }
                }

            }


        }
    }
}

