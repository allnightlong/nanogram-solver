package ru.megadevelopers.nanogram.model

import groovy.transform.Canonical
import groovy.transform.CompileStatic

@CompileStatic
@Canonical
class BoardValueChangeEvent {
    int row
    int column

    int value
}
