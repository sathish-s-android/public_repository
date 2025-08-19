package com.google.benchmark

import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.MemoryUsageMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.Until
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.example.sheetviewtestapp",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }



    @Test
    fun full() {
        verticalScroll(CompilationMode.Full())
//        horizontalScroll(CompilationMode.Full())
    }

    @OptIn(ExperimentalMetricApi::class)
    private fun verticalScroll(compilationMode: CompilationMode) {
        var firstStart = true
        benchmarkRule.measureRepeated(
            packageName = "com.example.sheetviewtestapp",
            metrics = listOf(

                FrameTimingMetric(),
                MemoryUsageMetric(mode = MemoryUsageMetric.Mode.Max)
            ),
            compilationMode = compilationMode,
            startupMode = null,
            iterations = 4,
            setupBlock = {
                if (firstStart) {
                    startActivityAndWait()
                }
            }
        ) {

            device.wait(Until.hasObject(By.desc("MainScrollList")), 3000)

            val scrollableObject = device.findObject(By.desc("MainScrollList"))//device.findObject(By.scrollable(true))
            if (scrollableObject == null) {
                TestCase.fail("No scrollable view found in hierarchy")
            }
            scrollableObject?.apply {
                repeat(2) {
                    fling(Direction.DOWN)
                }
                repeat(2) {
                    fling(Direction.RIGHT)
                }
                repeat(2) {
                    fling(Direction.UP)
                }
                repeat(2) {
                    fling(Direction.LEFT)
                }
            }
        }
    }


}