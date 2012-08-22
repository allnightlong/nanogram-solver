package ru.megadevelopers.nanogram.utils

import org.junit.Test
import ru.megadevelopers.nanogram.utils.Permutations

import static org.junit.Assert.assertEquals

class PermutationsTest {
	@Test
	void empty() {
		assertEquals([[0]], Permutations.generate(0, 1))
		assertEquals([[0, 0]], Permutations.generate(0, 2))
	}

	@Test
	void one_ball() {
		assertEquals([[1, 0, 0], [0, 1, 0], [0, 0, 1]], Permutations.generate(1, 3))
	}

	@Test
	void one_basket() {
		assertEquals([[3]], Permutations.generate(3, 1))
	}

	@Test
	void two_balls() {
		assertEquals([[2, 0], [1, 1], [0, 2]], Permutations.generate(2, 2))
		assertEquals([[1, 0, 1], [0, 1, 1], [0, 0, 2], [0, 2, 0], [1, 1, 0], [2, 0, 0]], Permutations.generate(2, 3))
	}

	@Test
	void three_balls() {
		assertEquals([[3, 0], [2, 1], [1, 2], [0, 3]], Permutations.generate(3, 2))
		assertEquals([[3, 0, 0], [1, 1, 1], [0, 1, 2], [1, 0, 2], [0, 2, 1], [0, 3, 0], [0, 0, 3], [1, 2, 0], [2, 0, 1], [2, 1, 0]], Permutations.generate(3, 3))
	}

	@Test
	void terms() {
		assertEquals([[0, 2], [1, 1]], Permutations.terms(2))
	}

}
