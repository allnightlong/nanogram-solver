package ru.megadevelopers.nanogram.utils


abstract class Bypass {
	static List<List<Integer>> generate(List<Integer> source) {
		BigInteger product = MathUtils.multiply(source)

		def result = []
		product.times {result << []}

		source.size().times {i ->
			product.times { j ->
				result[j][i] = j % source[i]
			}
		}
		result
	}
}
