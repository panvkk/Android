package com.example.superheroes

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.superheroes.model.Hero

@Composable
fun HeroesScreen(heroes: List<Hero>, modifier: Modifier = Modifier)  {
    Scaffold { it ->
        LazyColumn(contentPadding = it) {
            items(heroes) { it ->
                HeroCard(
                    hero = it
                )
            }
        }
    }
}
