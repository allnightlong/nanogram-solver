package ru.megadevelopers.nanogram

import groovy.json.JsonSlurper
import ru.megadevelopers.nanogram.gui.NanogramFrame
import ru.megadevelopers.nanogram.model.NanogramBoard

import javax.swing.*

class StarterGui {

    static void main(String[] args) {
        def resource = StarterGui.class.getResource('/source_small.json')

        def result = new JsonSlurper().parse(resource)
        result.data_top.each { Collections.replaceAll(it, '', 0) }
        result.data_left.each { Collections.replaceAll(it, '', 0) }

        def nanogram = new NanogramBoard(result.data_top, result.data_left, result.width, result.height)

        UIManager.setLookAndFeel('javax.swing.plaf.metal.MetalLookAndFeel')
        SwingUtilities.invokeLater({
            NanogramFrame frame = new NanogramFrame(nanogram)
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
            frame.addComponentsToPane(frame.getContentPane())

            frame.pack()
            frame.setLocationRelativeTo(null)
            frame.setVisible(true)
        })
    }
}
