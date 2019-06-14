package ru.megadevelopers.nanogram

import groovy.transform.CompileStatic

@CompileStatic
class Nanogram {

    List<List<Integer>> top
    List<List<Integer>> left

    private int width
    private int height

    private static int[][] board

    void init() {
        board = new int[width][height]
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
            lines.eachWithIndex { int value, int column ->
                print(toChar(value))
            }

            println()
        }
    }

    private static char toChar(int input) {
        input ? Character.forDigit(input, 35) : ' ' as char
    }


    private boolean solve() {
        width.times { int row ->
            height.times { column ->
                if (board[row][column] == Cell.NO_VALUE) {

                    board[row][column] = Cell.EMPTY
                    if (isValid(row, column) && solve()) {
                        return true
                    }

                    board[row][column] = Cell.FILLED
                    if (isValid(row, column) && solve()) {
                        return true
                    }

                    board[row][column] = Cell.NO_VALUE
                }
                return false
            }
        }
    }

    boolean isValid(int row, int column) {
        false
    }
}
