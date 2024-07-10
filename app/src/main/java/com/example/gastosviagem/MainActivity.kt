package com.example.gastosviagem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.gastosviagem.ui.theme.GastosViagemTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GastosViagemTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    calculate()
                }
            }
        }
    }
}

@Composable
fun calculate() {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val totalValue = remember { mutableStateOf(0f) }
    val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.purple_200)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {

        val distance = remember { mutableStateOf("") }
        TextField(
            value = distance.value,
            onValueChange = { distance.value = it },
            label = { Text("Distância") },
            keyboardOptions = keyboardOptions
        )

        Text(text = "Total de quilômetros?",
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        val price = remember { mutableStateOf("") }
        TextField(
            value = price.value,
            onValueChange = { price.value = it },
            label = { Text("Preço") },
            keyboardOptions = keyboardOptions
        )

        Text(text = "Preço por litro",
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        val autonomy = remember { mutableStateOf("") }
        TextField(
            value = autonomy.value,
            onValueChange = { autonomy.value = it },
            label = { Text("Autonomia") },
            keyboardOptions = keyboardOptions
        )

        Text(
            text = "KMs por litro",
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "SEU GASTO TOTAL SERÁ?",
            style = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "R$ ${"%.2f".format(totalValue.value)}",
            style = TextStyle(
                color = Color.Red,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(2f, 2f),
                    blurRadius = 5f
                )
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isValid(distance.value, price.value, autonomy.value)) {
                    totalValue.value =
                        (distance.value.toFloat() * price.value.toFloat()) / autonomy.value.toFloat()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Por favor, preencha todos os campos corretamente.",
                            actionLabel = "Entendi"
                        )
                    }
                }
            }

        )
        {
            Text("CALCULAR")
        }

        SnackbarHost(hostState = snackbarHostState)
    }
}

fun isValid(distance: String, price: String, autonomy: String): Boolean {
    return distance.isNotEmpty() &&
            price.isNotEmpty() &&
            autonomy.isNotEmpty() &&
            autonomy.toFloat() != 0f
}

@Preview
@Composable
fun calculatePreview() {
    GastosViagemTheme {
        calculate()
    }
}