package ru.megadevelopers.nanogram.gui

import ru.megadevelopers.nanogram.model.NanogramBoard

import javax.swing.*
import java.awt.*

class NanogramFrame extends JFrame {

    private NanogramBoard nanogramBoard
    private JTextField[][] fields

    NanogramFrame(NanogramBoard nanogramBoard) {
        super()
        setResizable(false)

        this.nanogramBoard = nanogramBoard
        this.fields = new JTextField[nanogramBoard.widthWithOffset][nanogramBoard.heightWithOffset]
    }

    void addComponentsToPane(final Container pane) {
        pane.add(boardPanel(), BorderLayout.NORTH)
        pane.add(controlsPanel(), BorderLayout.SOUTH)
    }

    private JPanel boardPanel() {
        GridLayout baseLayout = new GridLayout(nanogramBoard.heightWithOffset, nanogramBoard.widthWithOffset)

        final JPanel board = new JPanel()
        board.setLayout(baseLayout)


        for (int row = 0; row < nanogramBoard.heightWithOffset; row++) {
            for (int column = 0; column < nanogramBoard.widthWithOffset; column++) {
                JTextField field = new JTextField(getFieldValue(row, column))
                field.editable = false
                board.add(field)

                fields[column][row] = field
            }
        }
        board
    }

    private String getFieldValue(int row, int column) {
        def topOffset = nanogramBoard.topOffset()
        def leftOffset = nanogramBoard.leftOffset()

        if (row < topOffset && column < leftOffset) {
            return null
        }
        if (row >= topOffset && column >= leftOffset) {
            return nanogramBoard.getValue(row - topOffset, column - leftOffset) ?: ''
        }
        if (row < topOffset) {
            return nanogramBoard.top[column - leftOffset].findAll()[row]
        }
        if (column < leftOffset) {
            return nanogramBoard.left[row - topOffset].findAll()[column]
        }

        throw new IllegalStateException()
    }

    private JPanel controlsPanel() {
        JPanel controls = new JPanel()
        JButton solveButton = new JButton('Solve')
        controls.add(solveButton)
        solveButton.addActionListener {
            solve()
        }
        controls
    }

    private void solve() {

    }

}
