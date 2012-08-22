package ru.megadevelopers.nanogram

import org.junit.Test

import static org.junit.Assert.assertEquals
import static ru.megadevelopers.nanogram.BitSetUtils.bitSetList

class NanogramTest {
	def left = [[1, 1], [2]]
	def top = [[2], [1], [1]]
	def nanogram = new Nanogram(width: 3, height: 2, left: left, top: top)

	@Test
	void lines() {
		assertEquals left, nanogram.getLines(Direction.HORIZONTAL).blocks
		assertEquals top, nanogram.getLines(Direction.VERTICAL).blocks
	}

	@Test
	void solve() {
		assertEquals(bitSetList([[1, 0, 1], [1, 1, 0]]), nanogram.solve())
	}
}
