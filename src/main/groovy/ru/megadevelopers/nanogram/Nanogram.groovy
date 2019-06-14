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

    void init() {
        board = new int[height][width]
    }


    void print(boolean printBoard) {
        def topOffset = top.collect { List<Integer> lines -> lines.size() }.max()
        def leftOffset = left.collect { List<Integer> lines -> lines.size() }.max()

        printTop(leftOffset, topOffset)
        printLeft(printBoard)
        println()
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
                    print(board[row][column] == Cell.FILLED ? '#' : '.')
                }
            }

            println()
        }
    }

    private static char toChar(int input) {
        input ? Character.forDigit(input, 35) : ' ' as char
    }


    boolean solve() {
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (board[row][column] == Cell.NO_VALUE) {

                    board[row][column] = Cell.FILLED
                    if (isValid(row, column) && solve()) {
                        return true
                    }

                    board[row][column] = Cell.EMPTY
                    if (isValid(row, column) && solve()) {
                        return true
                    }

                    board[row][column] = Cell.NO_VALUE
                    return false
                }
            }
        }
        return true
    }

    private boolean isValid(int row, int column) {
        isValidRow(row) && isValidColumn(column)
    }

    private boolean isValidRow(int row) {
        List<Integer> leftLine = left[row]
        List<BitSet> candidates = Line.candidates(leftLine, width)

        List<Integer> currentLine = board[row].toList()
        Line.isValid(currentLine, candidates)
    }

    @CompileDynamic
    private boolean isValidColumn(int column) {
        List<Integer> topLine = top[column]
        List<BitSet> candidates = Line.candidates(topLine, height)

        List<Integer> currentLine = board.collect { int[] columns -> columns[column] }
        Line.isValid(currentLine, candidates)
    }

}
