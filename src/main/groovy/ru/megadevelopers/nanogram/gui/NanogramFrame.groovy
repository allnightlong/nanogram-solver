package ru.megadevelopers.nanogram.gui

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import ru.megadevelopers.nanogram.model.BoardValueChangeEvent
import ru.megadevelopers.nanogram.model.Cell
import ru.megadevelopers.nanogram.model.NanogramBoard

import javax.swing.*
import java.awt.*
import java.util.concurrent.CompletableFuture

class NanogramFrame extends JFrame {

    private NanogramBoard nanogramBoard
    private JTextField[][] fields
    private EventBus eventBus

    NanogramFrame(NanogramBoard nanogramBoard) {
        super()
        setResizable(false)

        this.nanogramBoard = nanogramBoard
        this.fields = new JTextField[nanogramBoard.widthWithOffset][nanogramBoard.heightWithOffset]
        this.eventBus = new EventBus()

        eventBus.register(this)
        this.nanogramBoard.registerEventBus(eventBus)
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
                JTextField field = new JTextField()
                field.editable = false
                board.add(field)

                fields[column][row] = field
            }
        }

        fillFields()
        board
    }

    private void fillFields() {
        for (int row = 0; row < nanogramBoard.heightWithOffset; row++) {
            for (int column = 0; column < nanogramBoard.widthWithOffset; column++) {
                fillField(row, column)
            }
        }
    }

    private void fillField(int row, int column) {
        JTextField field = fields[column][row]
        field.text = getFieldValue(row, column)
    }


    private String getFieldValue(int row, int column) {
        def topOffset = nanogramBoard.topOffset()
        def leftOffset = nanogramBoard.leftOffset()

        if (row < topOffset && column < leftOffset) {
            return null
        }
        if (row >= topOffset && column >= leftOffset) {
            return nanogramBoard.getValue(row - topOffset, column - leftOffset) == Cell.FILLED ? 'X' : ''
        }
        if (row < topOffset) {
            def topColumn = nanogramBoard.top[column - leftOffset].findAll()
            def index = row - topOffset + topColumn.size()
            return index >= 0 ? topColumn[index] : ''
        }
        if (column < leftOffset) {
            def leftRow = nanogramBoard.left[row - topOffset].findAll()
            def index = column - leftOffset + leftRow.size()
            return index >= 0 ? leftRow[index] : ''
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
        CompletableFuture.runAsync {
            nanogramBoard.solve()
            fillFields()
        }
    }

    @Subscribe void onBoardValueChange(BoardValueChangeEvent event) {
        println event
        fillField(event.row + nanogramBoard.topOffset(), event.column + nanogramBoard.leftOffset())
    }
}
