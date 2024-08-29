package es.diusframi.redsystest.ui.commons

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import es.diusframi.redsysinapppayment.ui.theme.RedSysInAppPaymentTheme
import kotlinx.coroutines.delay

@Preview(name = "LightMode", showBackground = true)
@Preview(name = "DarkMode", showBackground = true, uiMode = 32)
@Composable
fun PreviewDotsProgressIndicator(){
    RedSysInAppPaymentTheme(darkTheme = true){
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
            ){
                ElementBouncer(
                    listOfElements = listOf(
                        {modifier -> Circle(modifier = modifier, size = 20.dp) },
                        {modifier -> Circle(modifier = modifier, size = 20.dp) },
                        {modifier -> Circle(modifier = modifier, size = 20.dp) }
                    )
                )
            }
        }
    }
}

/**
 * A composable function that animates a list of elements with a bouncing effect.
 *
 * The `ElementBouncer` function applies a vertical bouncing animation to each element in the provided list.
 * The elements bounce up and down sequentially, creating a lively visual effect.
 *
 * @param modifier The `Modifier` to be applied to the composable.
 * @param listOfElements A list of composable elements to be animated. Each element is a composable function that takes a `Modifier` as a parameter.
 * @param delay The delay between the start of each element's bounce animation in milliseconds. Default is 350ms.
 * @param yOffset The vertical offset in pixels for the bounce effect. Default is 30f.
 *
 * Example usage:
 * ```
 * ElementBouncer(
 *     listOfElements = listOf(
 *         { mod -> Text("Element 1", modifier = mod) },
 *         { mod -> Text("Element 2", modifier = mod) },
 *         { mod -> Text("Element 3", modifier = mod) }
 *     )
 * )
 * ```
 */
@Composable
fun ElementBouncer(
    modifier: Modifier = Modifier,
    listOfElements: List<@Composable (modifier: Modifier) -> Unit>,
    delay: Long = 350,
    yOffset: Float = 30f
){
    val positionYOffSets = remember {
        List(listOfElements.size){ mutableFloatStateOf(0f) }
    }
    val animatedYOffSets = positionYOffSets.map { state ->
        animateFloatAsState(
            targetValue = state.floatValue,
            label = "PositionAnimation",
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    }

    LaunchedEffect(Unit) {
        while(true){
            positionYOffSets.forEachIndexed { index, state ->
                //delay(delay)
                state.floatValue = yOffset
                delay(delay)
                state.floatValue = -yOffset
                delay(delay)
            }
        }
    }

    Row(modifier = modifier){
        listOfElements.forEachIndexed { index, element ->
            element(
                modifier
                    .graphicsLayer {
                        translationY = animatedYOffSets[index].value
                    }
            )
        }
    }
}

/**
 * A simple composable function that displays a static circle.
 *
 * The `Circle` function creates a circular shape with the specified size and color.
 * This can be used for indicators, decorations, or other purposes within the UI.
 *
 * @param modifier The `Modifier` to be applied to the composable.
 * @param size The diameter of the circle in dp. Default is 5dp.
 * @param color The color of the circle. Default is the primary color of the theme.
 *
 * Example usage:
 * ```
 * Circle(
 *     size = 10.dp,
 *     color = Color.Blue
 * )
 * ```
 */
@Composable
fun Circle(
    modifier: Modifier = Modifier,
    size: Dp = 5.dp,
    color: Color = MaterialTheme.colorScheme.primary
){
    Surface(
        color = color,
        shape = CircleShape,
        shadowElevation = 1.dp,
        modifier = modifier
            .padding(horizontal = 8.dp)
            .size(size)
    ) {}
}
