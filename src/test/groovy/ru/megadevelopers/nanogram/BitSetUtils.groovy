package ru.megadevelopers.nanogram

abstract class BitSetUtils {

	static List<BitSet> bitSetList(List<List<Integer>> source) {
		source.collect bitSet
	}
	static Closure<BitSet> bitSet = { List<Integer> source ->
		def set = new BitSet()
		source.eachWithIndex {element, index -> set[index] = element as boolean }
		set
	}
}
