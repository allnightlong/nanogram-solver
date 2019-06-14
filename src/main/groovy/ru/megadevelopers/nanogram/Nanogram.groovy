package ru.megadevelopers.nanogram

import groovy.transform.CompileStatic
import ru.megadevelopers.nanogram.utils.Bypass

@CompileStatic
class Nanogram {

    List<List<Integer>> top
    List<List<Integer>> left

    private int width
    private int height

    private void checkRanges() {
        if (left.size() != height) throw new IllegalStateException("height check failed")
        if (top.size() != width) throw new IllegalStateException("width check failed")
    }

    List<Line> getLines(Direction direction) {
        checkRanges()
        def result = []
        if (direction == Direction.HORIZONTAL) {
            height.times { result << new Line(length: width, blocks: left[it]) }
        } else {
            width.times { result << new Line(length: height, blocks: top[it]) }
        }
        result
    }

    List<BitSet> solve() {
        def direction = chooseSolveDirection()
        def lines = getLines(direction)
        def options = lines*.generateOptions()
        def bypasses = Bypass.generate(options*.size())
        for (bypass in bypasses) {
            def solution = []
            bypass.eachWithIndex { it, index -> solution << options[index][it] }
            if (checkSolution(solution, direction)) return solution
        }
        null
    }

    void print() {
        def topOffset = top.collect { List<Integer> lines -> lines.size() }.max()
        def leftOffset = left.collect { List<Integer> lines -> lines.size() }.max()

        printTop(leftOffset, topOffset)
        printLeft()
    }

    private void printTop(int leftOffset, int topOffset) {
        topOffset.times { row ->
            leftOffset.times { print ' ' }
            top.eachWithIndex { value, column ->
                print(toChar(top[column][row]))
            }

            println()
        }
    }

    private void printLeft() {
        left.eachWithIndex { List<Integer> lines, int row ->
            lines.eachWithIndex { int value, int column ->
                print(toChar(value))
            }

            println()
        }
    }

    private char toChar(int input) {
        input ? Character.forDigit(input, 35) : ' ' as char
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
//        def verticalPermutation = MathUtils.multiply(getLines(Direction.VERTICAL)*.countOptions())
//        def horizontalPermutation = MathUtils.multiply(getLines(Direction.HORIZONTAL)*.countOptions())
//        horizontalPermutation < verticalPermutation ? Direction.HORIZONTAL : Direction.VERTICAL
    }
}
