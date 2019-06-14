package ru.megadevelopers.nanogram.model

import ru.megadevelopers.nanogram.model.Line
import spock.lang.Specification

import static ru.megadevelopers.nanogram.model.Line.bitSetList

class LineSpec extends Specification {
    void candidates() {
        when:
            def candidates = Line.candidates([1, 2], 4)
        then:
            candidates == bitSetList([[1, 0, 1, 1]])

        when:
            candidates = Line.candidates([1], 3)
        then:
            candidates == bitSetList([
                    [1, 0, 0],
                    [0, 1, 0],
                    [0, 0, 1]
            ])

        when:
            candidates = Line.candidates([2], 5)
        then:
            candidates == bitSetList([
                    [1, 1, 0, 0, 0],
                    [0, 1, 1, 0, 0],
                    [0, 0, 1, 1, 0],
                    [0, 0, 0, 1, 1]
            ])

        when:
            candidates = Line.candidates([2, 1], 5)
        then:
            candidates == bitSetList([
                    [1, 1, 0, 1, 0],
                    [1, 1, 0, 0, 1],
                    [0, 1, 1, 0, 1]
            ])

    }

}
