package ru.megadevelopers.nanogram


import groovy.transform.CompileStatic

import static com.google.common.base.Strings.repeat
import static java.util.Arrays.asList

@CompileStatic
class Line {

    static List<BitSet> candidates(List<Integer> line, int length) {
        List<BitSet> result = []

        int totalFilled = line.sum() as int

        List<String> prep = line.collect { Integer value -> repeat('1', value) }

        for (String r : generateSequence(prep, length - totalFilled + 1)) {
            char[] bits = r.substring(1).toCharArray()
            BitSet bitset = new BitSet(bits.length)
            for (int i = 0; i < bits.length; i++)
                bitset.set(i, bits[i] == '1')
            result.add(bitset)
        }
        result
    }

    static List<String> generateSequence(List<String> ones, int numZeros) {
        if (ones.isEmpty()) {
            return asList(repeat('0', numZeros))
        }

        List<String> result = []
        for (int x = 1; x < numZeros - ones.size() + 2; x++) {
            List<String> skipOne = ones.drop(1)
            for (String tail : generateSequence(skipOne, numZeros - x)) {
                result.add(repeat('0', x) + ones.get(0) + tail)
            }
        }
        return result;
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