package ru.megadevelopers.nanogram

import groovy.transform.EqualsAndHashCode
import ru.megadevelopers.nanogram.utils.Permutations

@EqualsAndHashCode
class Line {
	int length
	List<Integer> blocks

	static Line fromBitSet(BitSet source, int length) {
		Line line = new Line(length: length, blocks: [])
		int j = 1
		for (int i = source.nextSetBit(0); i >= 0; i = source.nextSetBit(i + 1)) {
			while (source[++i]) {++j}
			line.blocks << j
			j = 1
		}
		line.checkRanges()
		line
	}

	private int countBalls() {
		blocks ? length - (blocks.sum() + blocks.size() - 1) : length
	}

	private int countBags() {
		blocks.size() + 1
	}

	private checkRanges() {
		if (blocks == null) throw new IllegalStateException('empty blocks')
		if (length <= 1) throw new IllegalStateException('line length fail')
		if (countBalls() < 0) throw new IllegalStateException('whitespace check fail')
		blocks = blocks.findAll()
	}

	private expandPermutation = { permutation ->
		def result = new BitSet(length)
		int from, to = 0
		blocks.eachWithIndex {block, index ->
			from = to + permutation[index]
			if (index != 0) from++
			to = from + block
			result.set(from, to)
		}
		result
	}

	List<BitSet> generateOptions() {
		checkRanges()
		def balls = countBalls()
		def bags = countBags()
		def permutations = Permutations.generate(balls, bags)
		permutations.collect(expandPermutation)
	}


	BigInteger countOptions() {
		checkRanges()
		def n = countBalls()
		def m = countBags()
		c(n, n + m - 1)
	}

	private BigInteger c(int k, int n) {
		def result = 1G
		if (k < n - k) return c(n - k, n)
		if (n == k) return result
		for (int i in n..(k + 1)) {
			result *= i
		}
		result / fact(n - k)
	}

	private fact = {int n-> n <= 1 ? 1G : n * fact.call(n - 1)}.memoize()
}
