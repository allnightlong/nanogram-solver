package ru.megadevelopers.nanogram.model

import com.google.common.eventbus.EventBus
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic

@CompileStatic
class NanogramBoard {

    static int DELEAY = 100

    List<List<Integer>> top
    List<List<Integer>> left

    int width
    int height

    private int[][] board

    private EventBus eventBus

    NanogramBoard(List<List<Integer>> top, List<List<Integer>> left, int width, int height) {
        this.top = top
        this.left = left
        this.width = width
        this.height = height

        this.board = new int[height][width]
    }

    void print(boolean printBoard) {
        printTop(leftOffset(), topOffset())
        printLeft(printBoard)
        println()
    }

    int topOffset() {
        top.collect { List<Integer> lines -> lines.size() }.max()
    }

    int getHeightWithOffset() {
        height + topOffset()
    }

    int leftOffset() {
        left.collect { List<Integer> lines -> lines.size() }.max()
    }

    int getWidthWithOffset() {
        width + leftOffset()
    }

    int getValue(int row, int column) {
        board[row][column]
    }

    void setValue(int row, int column, int value) {
        board[row][column] = value

        eventBus.post(new BoardValueChangeEvent(row, column, value))
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
                    print(getValue(row, column) == Cell.FILLED ? 'X' : '.')
                }
            }

            println()
        }
    }

    private static char toChar(int input) {
        input ? Character.forDigit(input, 35) : ' ' as char
    }


    boolean solve() {
        Thread.sleep(DELEAY)
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                if (getValue(row, column) == Cell.NO_VALUE) {

                    setValue(row, column, Cell.FILLED)
                    if (isValid(row, column) && solve()) {
                        return true
                    }

                    setValue(row, column, Cell.EMPTY)
                    if (isValid(row, column) && solve()) {
                        return true
                    }

                    setValue(row, column, Cell.NO_VALUE)
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

    void registerEventBus(EventBus eventBus) {
        this.eventBus = eventBus
    }

}
