package com.infaliblerealestate.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.filled.Villa
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.infaliblerealestate.presentation.util.components.PropiedadChip
import com.infaliblerealestate.presentation.util.components.PropiedadItem
import com.infaliblerealestate.presentation.util.components.SheetPropiedadDetalle
import com.infaliblerealestate.presentation.util.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    usuarioId: String?,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snack = remember { SnackbarHostState() }

    LaunchedEffect(state.userMessage) {
        state.userMessage?.let {
            if(it.isNotBlank()) snack.showSnackbar(it)
            viewModel.onEvent(HomeUiEvent.UserMessageShown)
        }
    }

    HomeScreenContent(
        state,
        viewModel::onEvent,
        snack = snack,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    snack: SnackbarHostState,
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "¿Qué estás buscando\nhoy?",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        modifier = Modifier.padding(top = 0.dp)
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
                ),
                windowInsets = WindowInsets(top = 0.dp)
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snack,
                modifier = Modifier.padding(bottom = 96.dp)
            )
        },
        bottomBar ={
            Spacer(modifier = Modifier.height(90.dp))
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(state.categorias) { categoria ->
                        val icon = when (categoria) {
                            "Casa" -> Icons.Default.Home
                            "Departamento" -> Icons.Default.Business
                            "Villa" -> Icons.Default.Villa
                            "Penthouse" -> Icons.Default.Apartment
                            "Solar" -> Icons.Default.Map
                            else -> Icons.Default.Store
                        }
                        PropiedadChip(
                            icon = icon,
                            text = categoria,
                            onClick = {
                                navController.navigate(
                                    "${Screen.Catalogo.createRoute("")}?categoria=$categoria"
                                )

                            },
                            modifier = Modifier.height(60.dp)
                        )
                    }
                }
            }

            if (state.isLoading) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularWavyProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Cargando...",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                val propiedades = state.propiedades
                if (propiedades.isEmpty()) {
                    item {
                        Text(
                            text = "No se encontraron propiedades para mostrar",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    item {
                        Text(
                            text = "Últimas propiedades",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    items(
                        items = propiedades,
                        key = { propiedad -> propiedad.propiedadId }
                    ) { propiedad ->
                        PropiedadItem(
                            propiedad = propiedad,
                            onClick = { onEvent(HomeUiEvent.LoadPropiedad(propiedad.propiedadId)) },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    }

    if (state.showSheet) {
        SheetPropiedadDetalle(
            propiedad = state.propiedad,
            onDismiss = { onEvent(HomeUiEvent.HideSheet) }
        )
    }
}



