package ru.megadevelopers.nanogram.utils

import org.junit.Assert
import org.junit.Test
import ru.megadevelopers.nanogram.utils.Bypass

class BypassTest {
	@Test
	void generate() {
		def source = [1, 2, 3]
		def expected = [[0, 0, 0], [0, 0, 1], [0, 0, 2], [0, 1, 0], [0, 1, 1], [0, 1, 2]]
		def result = Bypass.generate(source)
		Assert.assertEquals expected.sort(), result.sort()
	}
}
