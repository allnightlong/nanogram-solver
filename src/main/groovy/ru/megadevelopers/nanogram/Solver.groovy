package ru.megadevelopers.nanogram

import groovy.json.JsonSlurper

File file = new File('source.json')
JsonSlurper slurper = new JsonSlurper()
def result = slurper.parse(file.newReader())
def transform = {
	it.findAll().collect {it as int}
}
def top = result.data_top.collect(transform)
def left = result.data_left.collect(transform)
int height = result.height
int width = result.width

def nanogram = new Nanogram(width: width, height: height, left: left, top: top)
nanogram.solve()
