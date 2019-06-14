package ru.megadevelopers.nanogram

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@CompileStatic
class Nanogram {

    List<List<Integer>> top
    List<List<Integer>> left

    private int width
    private int height

    private int[][] board
    private Map<String, List<BitSet>> candidatesCache

    void init() {
        board = new int[width][height]
        candidatesCache = [:]
    }


    void print(boolean printBoard) {
        def topOffset = top.collect { List<Integer> lines -> lines.size() }.max()
        def leftOffset = left.collect { List<Integer> lines -> lines.size() }.max()

        printTop(leftOffset, topOffset)
        printLeft(printBoard)
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

    private void printLeft(boolean printBoard) {
        left.eachWithIndex { List<Integer> lines, int row ->
            lines.each { int value ->
                print(toChar(value))
            }

            if (printBoard) {
                width.times { int column ->
                    print(board[column][row] == Cell.FILLED ? '#' : '.')
                }
            }

            println()
        }
    }

    private static char toChar(int input) {
        input ? Character.forDigit(input, 35) : ' ' as char
    }


    private boolean solve() {
        width.times { column ->
            height.times { int row ->
                if (board[column][row] == Cell.NO_VALUE) {

                    board[column][row] = Cell.FILLED
                    if (isValid(column, row) && solve()) {
                        return true
                    }

                    board[column][row] = Cell.EMPTY
                    if (isValid(column, row) && solve()) {
                        return true
                    }

                    board[column][row] = Cell.NO_VALUE
                }
                return false
            }
        }
    }

    private boolean isValid(int column, int row) {
        isValidRow(row)
        isValidColumn(column)
    }

    private boolean isValidColumn(int column) {
        List<Integer> topLine = top[column]
        List<BitSet> candidates = getCandidates(topLine, height)

        List<Integer> currentLine = board[column].toList()
        Line.isValid(currentLine, candidates)
    }

    @CompileDynamic
    private boolean isValidRow(int row) {
        List<Integer> leftLine = left[row]
        List<BitSet> candidates = getCandidates(leftLine, width)

        List<Integer> currentLine = board.collect { int[] rows -> rows[row] }
        Line.isValid(currentLine, candidates)
    }

    private List<BitSet> getCandidates(List<Integer> line, int length) {
        Line.candidates(line, length)
//        String key = "${length}${line.join('')}"
//        candidatesCache.compute(key, { Line.candidates(line, length) })
    }
}
