package ru.megadevelopers.nanogram

import com.google.common.base.Stopwatch
import groovy.json.JsonSlurper
import ru.megadevelopers.nanogram.model.NanogramBoard

def resource = getClass().getResource('/source_small.json')

def result = new JsonSlurper().parse(resource)
result.data_top.each { Collections.replaceAll(it, '', 0) }
result.data_left.each { Collections.replaceAll(it, '', 0) }

def nanogram = new NanogramBoard(result.data_top, result.data_left, result.width, result.height)

def stopwatch = Stopwatch.createStarted()
nanogram.solve()
def elapsed = stopwatch.toString()

nanogram.print(true)
println "compute in ${elapsed}"