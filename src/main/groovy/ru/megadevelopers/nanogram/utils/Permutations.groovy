package ru.megadevelopers.nanogram.utils

abstract class Permutations {
	static def generate(int balls, int bags) {
		generateInternal(balls, bags) as List
	}

	private static generateInternal = {int balls, int bags ->
		if (bags == 0) throw new IllegalStateException()
		if (bags == 1) return [[balls]]
		if (balls == 0) return [Collections.nCopies(bags, 0)]

		Set<List<Integer>> result = []
		for (i in 0..balls - 1) {
			for (j in 0..bags - 1) {
				def subResult = generate(balls - i, bags - 1).collect {
					def permutation = []
					permutation.addAll(it)
					permutation.add(j, i)
					permutation.asImmutable()
				}
				result.addAll(subResult)
			}
		}
		result
	}.memoize()

	static terms= {int balls, int bags ->
		balls.times {

		}
	}
}
