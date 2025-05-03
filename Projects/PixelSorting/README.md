# PixelSorting for Android

PixelSorting is an image processing technique that rearranges pixels based on specific criteria to create eye-catching glitch art effects. This Android implementation of the algorithm lets you sort pixels by various properties—such as brightness, hue, red, green, or blue values—with adjustable intensity.

## Overview

PixelSorting for Android is designed to:
- Process images by sorting pixels along rows or columns.
- Offer configurable effect levels to control the “strength” of the glitch art effect.
- Demonstrate modern image manipulation techniques using Kotlin.

## Features

- **Multiple Sorting Criteria:**  
  Sort pixels by brightness, hue, or individual color channels (red, green, blue).

- **Adjustable Effect Level:**  
  Modify the intensity of the pixel sorting effect by changing a parameter that defines how much of the row or column is affected.

- **Flexible Orientation:**  
  Choose between sorting pixels by rows or by columns to achieve subtly different artistic outcomes.

- **Modular Design:**  
  The core functions, such as `getKeyValue` and `pixelSort`, are built to be easily integrated and extended in Android applications.

## How It Works

1. **Pixel Extraction:**  
   The algorithm extracts pixels from the image either row-wise or column-wise.

2. **Computing the Key Value:**  
   Each pixel’s value is computed using a helper function (`getKeyValue`) based on the selected sorting key (brightness, hue, etc.).

3. **Segment Sorting:**  
   Depending on the specified effect level, only portions of the row or column (or even specific segments defined by thresholds) are sorted. Lower effect levels yield a more subtle effect, while higher levels produce a stronger, more pronounced transformation.

4. **Applying the Sorted Segments:**  
   The sorted segments are then written back to produce the resulting glitch-art image.
