package com.example.superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheroes.model.Hero
import com.example.compose.SuperheroesTheme
import com.example.superheroes.model.HeroesRepository
import com.example.superheroes.ui.theme.Shapes
import com.example.superheroes.ui.theme.Typography

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperheroesTheme {
                SuperheroesApp()
            }
        }
    }
}

@Composable
fun SuperheroesApp(modifier: Modifier = Modifier) {

}

@Composable
fun HeroCard(hero: Hero, modifier: Modifier = Modifier)  {
    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(8.dp)
            .clip(shape = Shapes.medium)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(72.dp)
        ) {
            HeroInformation(
                name = hero.nameRes,
                description = hero.descriptionRes,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
            HeroImage(
                hero.imageRes,
                modifier = Modifier
                    .size(72.dp)
                    .clip(shape = Shapes.small))
        }
    }
}

@Composable
fun HeroInformation(
    @StringRes name: Int,
    @StringRes description: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(name),
            style = Typography.displaySmall
        )
        Text(
            text = stringResource(description),
            style = Typography.bodyLarge
        )
    }
}

@Composable
fun HeroImage(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
fun HeroPreview() {
//    Scaffold { innerPadding ->
//        HeroCard(
//            hero = Hero(
//                nameRes = R.string.hero1,
//                descriptionRes = R.string.description1,
//                imageRes = R.drawable.android_superhero1
//            ),
//            modifier = Modifier.padding(innerPadding)
//        )
//    }
    HeroesScreen(heroes = HeroesRepository.heroes)
}

