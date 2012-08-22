package ru.megadevelopers.nanogram

import org.junit.Test

import static org.junit.Assert.assertEquals
import static ru.megadevelopers.nanogram.BitSetUtils.*

class LineTest {
	@Test
	void empty() {
		def expected = bitSetList([[0, 0, 0]])
		def line = new Line(length: 3, blocks: [0])
		assertEquals expected, line.generateOptions()
		line = new Line(length: 3, blocks: [])
		assertEquals expected, line.generateOptions()
	}

	@Test
	void fully_filled() {
		def line = new Line(length: 3, blocks: [3])
		assertEquals bitSetList([[1, 1, 1]]), line.generateOptions()
	}

	@Test
	void partially_filled() {
		def line = new Line(length: 3, blocks: [2])
		assertEquals bitSetList([[0, 1, 1], [1, 1, 0]]), line.generateOptions()
		line = new Line(length: 3, blocks: [1])
		assertEquals bitSetList([[0, 0, 1], [0, 1, 0], [1, 0, 0]]), line.generateOptions()
	}

	@Test
	void several_blocks() {
		def line = new Line(length: 5, blocks: [1, 2])
		assertEquals bitSetList([[0, 1, 0, 1, 1], [1, 0, 0, 1, 1], [1, 0, 1, 1, 0]]), line.generateOptions()
	}

	@Test
	void count_options() {
		assertEquals 1G, new Line(length: 3, blocks: []).countOptions()

		assertEquals 2G, new Line(length: 2, blocks: [1]).countOptions()
		assertEquals 3G, new Line(length: 3, blocks: [1]).countOptions()
		assertEquals 1G, new Line(length: 3, blocks: [1, 1]).countOptions()
	}

	@Test
	void fromBitSet() {
		assertEquals new Line(length: 4, blocks: []), Line.fromBitSet(bitSet([0, 0, 0, 0]),4 )
		assertEquals new Line(length: 4, blocks: [1]), Line.fromBitSet(bitSet([0, 0, 1, 0]), 4)
		assertEquals new Line(length: 3, blocks: [1, 1]), Line.fromBitSet(bitSet([1, 0, 1]), 3)
		assertEquals new Line(length: 4, blocks: [2, 1]), Line.fromBitSet(bitSet([1, 1, 0, 1]), 4)

	}
}
