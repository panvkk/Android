package com.example.amphibians.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.tooling.preview.Preview
import com.example.amphibians.model.AmphibianInfo
import com.example.amphibians.ui.components.AmphibianCard
import com.example.amphibians.ui.theme.AmphibiansTheme

@Composable
fun AmphibiansColumn(
    amphibians: List<AmphibianInfo>
) {
    Scaffold(
        topBar = { AmphibiansTopBar() }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            items(amphibians) { amphibian ->
                AmphibianCard(
                    amphibian,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmphibiansTopBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {Text(text = "Amphibians")},
        modifier = modifier
    )
}

@Preview
@Composable
fun AmphibiansColumnPreview() {
    AmphibiansTheme {
        val mockData = List(10) {
            AmphibianInfo(
                title = "First aaosdnfisdn",
                imgSrc = "",
                desc = "s0udifgiusdf si ifna ijsnjd fjnsd j ndif",
                id = "$it"
            )
        }
        AmphibiansColumn(mockData)
    }
}