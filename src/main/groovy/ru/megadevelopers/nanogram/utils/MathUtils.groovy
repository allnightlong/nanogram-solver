package ru.megadevelopers.nanogram.utils


abstract class MathUtils {
	static BigInteger multiply(List<Integer> source) {
		BigInteger product = 1
		source.each {product *= it}
		product
	}
}
