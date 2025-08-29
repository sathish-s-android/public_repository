
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner


@RunWith(ParameterizedRobolectricTestRunner::class)
class ParameterizedJsonTest( private val input: String,
                             private val expected: String) {

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "Input: {0}")
        fun data() = listOf(
            arrayOf<Any>("hello", "HELLO"),
            arrayOf<Any>("world", "WORLD")
        )
    }

    @Test
    fun testUpperCase() {
        Assert.assertEquals(expected, input.uppercase())
    }

}