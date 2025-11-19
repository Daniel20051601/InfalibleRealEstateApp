package com.infaliblerealestate.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import com.infaliblerealestate.dominio.model.Usuarios
import com.infaliblerealestate.presentation.navigation.Screen
import com.infaliblerealestate.ui.theme.InfalibleRealEstateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    usuarioId: Int?,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snack = remember { SnackbarHostState() }

    LaunchedEffect(usuarioId) {
        usuarioId?.let { viewModel.onEvent(HomeUiEvent.LoadUser(it)) }
    }

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                HomeEffect.NavigateLogin -> navController.navigate(Screen.Login.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            }
        }
    }

    LaunchedEffect(state.userMessage) {
        state.userMessage?.let {
            if (it.isNotBlank()) snack.showSnackbar(it)
            viewModel.onEvent(HomeUiEvent.UserMessageShown)
        }
    }
    HomeScreenContent(
        state,
        viewModel::onEvent,
        snack = snack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
    snack: SnackbarHostState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Home",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { onEvent(HomeUiEvent.Logout) }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = "Cerrar sesion",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snack) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Bienvenido:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onEvent(HomeUiEvent.showDialogEdit) }
            ) {
                Text(
                    text = state.userName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Usuarios registrados:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.listUsuarios) { usuario ->
                    OutlinedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {  }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = usuario.userName,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
    if (state.showDialog) {
        AlertDialog(
            title = {
                Text("Editar informacion")
            },
            text = {
                Column(
                    modifier = Modifier.imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = state.userName,
                        onValueChange = { onEvent(HomeUiEvent.UserNameChanged(it)) },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = state.userNameError != null,
                        supportingText = {
                            state.userNameError?.let { Text(text = it) }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.password,
                        onValueChange = { onEvent(HomeUiEvent.PasswordChanged(it)) },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = state.passwordError != null,
                        supportingText = {
                            state.passwordError?.let { Text(text = it) }
                        }
                    )
                }
            },
            onDismissRequest = {
                onEvent(HomeUiEvent.hideDialogEdit)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(HomeUiEvent.Save)
                    }
                ) {
                    Text("Guardar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    InfalibleRealEstateTheme() {
        HomeScreenContent(
            state = HomeUiState(
                userName = "Daniel",
                listUsuarios = listOf(
                    Usuarios(1, "Maria", ""),
                    Usuarios(2, "Pedro", "")
                )
            ),
            onEvent = {},
            snack = remember { SnackbarHostState() }
        )
    }
}
