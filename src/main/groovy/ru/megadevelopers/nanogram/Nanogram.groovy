package ru.megadevelopers.nanogram

import ru.megadevelopers.nanogram.utils.Bypass
import ru.megadevelopers.nanogram.utils.MathUtils

class Nanogram {
	int width
	int height
	List<List<Integer>> left
	List<List<Integer>> top

	private void checkRanges() {
		if (left.size() != height) throw new IllegalStateException("height check failed")
		if (top.size() != width) throw new IllegalStateException("width check failed")
	}

	List<Line> getLines(Direction direction) {
		checkRanges()
		def result = []
		if (direction == Direction.HORIZONTAL) {
			height.times {result << new Line(length: width, blocks: left[it])}
		} else {
			width.times {result << new Line(length: height, blocks: top[it])}
		}
		result
	}

	List<BitSet> solve() {
		def direction = chooseSolveDirection()
		def lines = getLines(direction)
		def options = lines*.generateOptions()
		def bypasses = Bypass.generate(options*.size())
		for (bypass in bypasses){
			def solution = []
			bypass.eachWithIndex {it, index -> solution << options[index][it]}
			if (checkSolution(solution, direction)) return solution
		}
		null
	}

	private checkSolution(List<BitSet> solution, Direction solutionDirection) {
		def direction = solutionDirection == Direction.HORIZONTAL ? Direction.VERTICAL : Direction.HORIZONTAL
		def solutionLength = solutionDirection == Direction.HORIZONTAL ? width : height
		def solutionCount = solution.size()
		def canonical = getLines(direction)

		for (int offset in 0..<solutionLength) {
			def bitSet = new BitSet()
			for (int index in 0..<solutionCount) {
				bitSet.set(index, solution[index][offset])
			}
			if (canonical[offset] != Line.fromBitSet(bitSet, solutionCount)) return false
		}
		true
	}

	private Direction chooseSolveDirection() {
		def verticalPermutation = MathUtils.multiply(getLines(Direction.VERTICAL)*.countOptions())
		def horizontalPermutation = MathUtils.multiply(getLines(Direction.HORIZONTAL)*.countOptions())
		horizontalPermutation < verticalPermutation ? Direction.HORIZONTAL : Direction.VERTICAL
	}
}
