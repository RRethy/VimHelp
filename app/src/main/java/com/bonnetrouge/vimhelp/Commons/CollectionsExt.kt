package com.bonnetrouge.vimhelp.Commons

import java.util.regex.Pattern

const val LOW_FUZZY = 1
const val MED_FUZZY = 2

/**
 * Returns a list containing only elements where an elements [filterProperty] fuzzy matches [match]
 */
inline fun <T> Iterable<T>.fuzzyFilter(fuzziness: Int = LOW_FUZZY, match: CharSequence, filterProperty: T.() -> String): List<T> {
    val lazySearchPattern = getFuzzinessPattern(fuzziness, match)
    val filteredList = mutableListOf<T>()
    this.forEach {
        val lazySearchMatcher = lazySearchPattern.matcher(it.filterProperty())
        if (lazySearchMatcher.find()) {
            filteredList.add(it)
        }
    }
    return filteredList
}

fun <T> Iterable<T>.getNext(current: T): T {
    val currentIndex = this.indexOf(current)
    return if (currentIndex == this.count() - 1) current
    else return this.elementAt(currentIndex + 1)
}

/**
 * Returns a fuzzy pattern with a degree of [fuzziness]
 */
fun getFuzzinessPattern(fuzziness: Int, stringMatch: CharSequence): Pattern {
    when (fuzziness) {
        LOW_FUZZY -> return buildFuzziness1Pattern(stringMatch)
        MED_FUZZY -> return buildFuzziness2Pattern(stringMatch)
        else -> return buildFuzziness1Pattern(stringMatch)
    }
}

/**
 * Builds a pattern for [stringMatch] that has a fuzziness factor of 1
 */
fun buildFuzziness1Pattern(stringMatch: CharSequence): Pattern {
    val lazySearchRegexBuilder = StringBuilder()
    lazySearchRegexBuilder.append("^(")
    stringMatch.forEach {
        lazySearchRegexBuilder.append(it)
        lazySearchRegexBuilder.append(".*")
    }
    lazySearchRegexBuilder.append(")")
    return Pattern.compile(lazySearchRegexBuilder.toString(), Pattern.CASE_INSENSITIVE)
}

/**
 * Builds a pattern for [stringMatch] that has a fuzziness factor of 1
 */
fun buildFuzziness2Pattern(stringMatch: CharSequence): Pattern {
    val lazySearchRegexBuilder = StringBuilder()
    lazySearchRegexBuilder.append("(")
    stringMatch.forEach {
        lazySearchRegexBuilder.append(it)
        lazySearchRegexBuilder.append(".*")
    }
    lazySearchRegexBuilder.append(")")
    return Pattern.compile(lazySearchRegexBuilder.toString(), Pattern.CASE_INSENSITIVE)
}
