package com.example.artspace

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                ) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

@Composable
fun NavigationButton(
    @StringRes title: Int,
    enable: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.width(110.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(enable) {
                Color(0xFF2b5bab)
            } else {
                Color(0xFF708cba)
            }
        )
    ) { Text(text = stringResource(title)) }
}

@Composable
fun ArtSpaceLayout(
    modifier: Modifier = Modifier,
    initialPageNumber: Int = 1,
    initialEnableInfo: Boolean = false
) {
    var pageNumber by remember { mutableStateOf(initialPageNumber) }
    var enableInfo by remember { mutableStateOf(initialEnableInfo) }

    Box {
        Icon(
            painter = painterResource(R.drawable.info),
            contentDescription = "get_info",
            tint = Color(0xFF7d7d7d),
            modifier = Modifier
                .padding(top = 40.dp, end = 20.dp)
                .size(40.dp)
                .align(Alignment.TopEnd)
                .clickable(onClick = { enableInfo = !enableInfo })
        )
        when(pageNumber) {
            1 ->
                Artwork(
                    artwork = R.drawable.the_wrath_of_th_seas,
                    artworkName = R.string.the_wrath_of_the_seas,
                    artistName = R.string.aivazovskiy,
                    artworkInfo = R.string.the_wrath_of_the_seas_info,
                    yearOfCreating = R.string.the_wrath_of_the_seas_year,
                    modifier = Modifier.align(Alignment.Center),
                    enableInfo = enableInfo
                )
            2 ->
                Artwork(
                    artwork = R.drawable.storm_on_the_sea_at_night,
                    artworkName = R.string.storm_on_the_sea_at_night,
                    artistName = R.string.aivazovskiy,
                    artworkInfo = R.string.storm_on_the_sea_at_night_info,
                    yearOfCreating = R.string.storm_on_the_sea_at_night_year,
                    modifier = Modifier.align(Alignment.Center),
                    enableInfo = enableInfo
                )
            3 ->
                Artwork(
                    artwork = R.drawable.moonlit_landscape_with_a_ship,
                    artworkName = R.string.moonlit_landscape_with_a_ship,
                    artistName = R.string.aivazovskiy,
                    artworkInfo = R.string.moonlit_landscape_with_a_ship_info,
                    yearOfCreating = R.string.moonlit_landscape_with_a_ship_year,
                    modifier = Modifier.align(Alignment.Center),
                    enableInfo = enableInfo
                )
            4 ->
                Artwork(
                    artwork = R.drawable.scream,
                    artworkName = R.string.scream,
                    artistName = R.string.munk,
                    artworkInfo = R.string.scream_info,
                    yearOfCreating = R.string.scream_year,
                    modifier = Modifier.align(Alignment.Center),
                    enableInfo = enableInfo
                )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(30.dp)
        ) {
            NavigationButton(
                onClick = {if(pageNumber != 1) { pageNumber-- } },
                title = R.string.previous,
                enable = if(pageNumber == 1) { false } else { true }
            )
            NavigationButton(
                onClick = {if(pageNumber != 4) { pageNumber++ } },
                title = R.string.next,
                enable = if(pageNumber == 4) { false } else { true }
            )
        }
    }

}

@Composable
fun Artwork(
    enableInfo: Boolean,
    @DrawableRes artwork: Int,
    @StringRes artworkName: Int,
    @StringRes artworkInfo: Int,
    @StringRes artistName: Int,
    @StringRes yearOfCreating: Int,
    modifier: Modifier = Modifier
) {
    if (!enableInfo) {
        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .shadow(16.dp)
                    .background(Color.White)
                    .border(1.dp, Color.LightGray)
            ) {
                Image(
                    painter = painterResource(artwork),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(20.dp)
                )
            }
            Column(
                modifier = Modifier
                    .background(color = Color(0xFFdbdbdb))
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(artworkName),
                    fontWeight = FontWeight.Light,
                    lineHeight = 40.sp,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Row {
                    Text(
                        text = stringResource(artistName),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                    Text(
                        text = stringResource(yearOfCreating),
                        fontWeight = FontWeight.Light,
                    )
                }
            }
        }
    } else {
        ArtworkInfo(
            artwork = artwork,
            artworkName = artworkName,
            artistName = artistName,
            artworkInfo = artworkInfo,
            yearOfCreating = yearOfCreating,
            modifier = Modifier
        )
    }
}
@Composable
fun ArtworkInfo(
    modifier: Modifier = Modifier,
    @DrawableRes artwork: Int,
    @StringRes artworkName: Int,
    @StringRes artworkInfo: Int,
    @StringRes artistName: Int,
    @StringRes yearOfCreating: Int,
) {
    Column {
        Row(modifier = modifier.padding(top = 100.dp)) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(16.dp)
                    .background(Color.White)
                    .border(1.dp, Color.LightGray)
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(artwork),
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Column(
                modifier = Modifier
                    .background(Color(0xFFdbdbdb))
                    .align(Alignment.CenterVertically)
                    .padding(20.dp)
                    .weight(2f)
            ) {
                Text(
                    text = stringResource(artworkName),
                    fontSize = 20.sp,
                )
                Row {
                    Text(
                        text = stringResource(artistName),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(text = stringResource(yearOfCreating))
                }
            }
        }
        Text(
            text = stringResource(artworkInfo),
            modifier = Modifier.padding(20.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        Surface(
            modifier = Modifier.fillMaxHeight()
        ) {
            ArtSpaceLayout()
        }
    }
}