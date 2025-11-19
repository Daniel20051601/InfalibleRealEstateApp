package com.infaliblerealestate.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.infaliblerealestate.presentation.navigation.Screen
import com.infaliblerealestate.ui.theme.InfalibleRealEstateTheme
import kotlinx.coroutines.flow.collectLatest


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                is LoginEffect.NavigateHome ->
                    navController.navigate(Screen.Home.createRoute(effect.usuarioId))
            }
        }
    }

    LoginBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginBody(
    state: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.userMessage) {
        if (state.userMessage.isNotBlank()) {
            snackBarHostState.showSnackbar(state.userMessage)
            onEvent(LoginUiEvent.userMessageShown)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(
                    text = if (state.isRegistering && !state.isLoading) "Sign in" else if(!state.isRegistering && !state.isLoading) "Log in" else "",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                ) }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularWavyProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )

            } else {
                val scroll = rememberScrollState()
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .navigationBarsPadding()
                        .imePadding()
                        .verticalScroll(scroll),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    OutlinedTextField(
                        value = state.userName,
                        onValueChange = { onEvent(LoginUiEvent.userNameChanged(it)) },
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
                        onValueChange = { onEvent(LoginUiEvent.passwordChanged(it)) },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = state.passwordError != null,
                        supportingText = {
                            state.passwordError?.let { Text(text = it) }
                        }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (state.isRegistering) {
                                onEvent(LoginUiEvent.submitRegistration)
                            } else {
                                onEvent(LoginUiEvent.submitLogin)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = if (state.isRegistering) "Registrar" else "Iniciar Sesion")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (state.isRegistering) "Ya tienes una cuenta?" else "No tienes una cuenta?",
                        modifier = Modifier
                            .clickable { onEvent(LoginUiEvent.registerModeClicked) }
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginBodyPreview(){
    InfalibleRealEstateTheme {
        LoginBody(
            state = LoginUiState(
                userName = "test",
                password = "test",
                isRegistering = false,
                isLoading = false,
            ),
            onEvent = {}
        )
    }
}
