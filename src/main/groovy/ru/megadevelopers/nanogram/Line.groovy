package ru.megadevelopers.nanogram

import groovy.transform.CompileStatic

import static com.google.common.base.Strings.repeat

@CompileStatic
class Line {

    static List<BitSet> candidates(List<Integer> line, int length) {

        int totalFilled = line.sum() as int

        List<String> prepared = line.collect { Integer value -> repeat(Cell.FILLED_CHAR as String, value) }

        def sequence = generateSequences(prepared, length - totalFilled + 1)

        sequence.collect { String binary -> fromString(binary.substring(1)) }
    }

    static List<String> generateSequences(List<String> ones, int numZeros) {
        if (ones.isEmpty()) {
            return [repeat('0', numZeros)]
        }

        List<String> sequences = []
        for (int x = 1; x < numZeros - ones.size() + 2; x++) {
            String head = ones.first()
            List<String> tail = ones.drop(1)

            for (String sequence : generateSequences(tail, numZeros - x)) {
                sequences.add(repeat(Cell.EMPTY_CHAR as String, x) + head + sequence)
            }
        }
        return sequences
    }

    private static BitSet fromString(String binary) {
        BitSet set = new BitSet(binary.length())
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == Cell.FILLED_CHAR) {
                set.set(i)
            }
        }
        return set
    }

    protected static List<BitSet> bitSetList(List<List<Integer>> source) {
        source.collect { toBitSet(it) }
    }

    protected static BitSet toBitSet(List<Integer> list) {
        def set = new BitSet()
        list.eachWithIndex { element, index -> set[index] = element as boolean }
        set
    }
}