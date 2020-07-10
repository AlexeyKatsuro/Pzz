/*
 * Copyright 2019 LWO LLC
 */

package com.alexeykatsuro.pzz.utils.date

import org.threeten.bp.DateTimeException
import org.threeten.bp.Duration
import org.threeten.bp.Instant

/*
 * Copyright (c) 2007-present, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * An immutable interval of time between two instants.
 *
 *
 * An interval represents the time on the time-line between two [Instant]s.
 * The class stores the start and end instants, with the start inclusive and the end exclusive.
 * The end instant is always greater than or equal to the start instant.
 *
 *
 * The [Duration] of an interval can be obtained, but is a separate concept.
 * An interval is connected to the time-line, whereas a duration is not.
 *
 *
 * Intervals are not comparable. To compare the length of two intervals, it is
 * generally recommended to compare their durations.
 *
 * <h3>Implementation Requirements:</h3>
 * This class is immutable and thread-safe.
 *
 *
 * This class must be treated as a value type. Do not synchronize, rely on the
 * identity hash code or use the distinction between equals() and ==.
 */
data class Interval(val start: Instant, val end: Instant) {

    companion object {

        /**
         * An interval over the whole time-line.
         */
        val ALL = Interval(Instant.MIN, Instant.MAX)

        /**
         * An empty interval (which has both start and end equal to Instant.now())
         */
        val EMPTY = Interval(Instant.now(), Instant.now())

        /**
         * Obtains an instance of {@code Interval} from the start and end instant.
         * <p>
         * The end instant must not be before the start instant.
         *
         * @param startInclusive the start instant, inclusive, MIN_DATE treated as unbounded, not null
         * @param endExclusive the end instant, exclusive, MAX_DATE treated as unbounded, not null
         * @return the half-open interval, not null
         * @throws DateTimeException if the end is before the start
         */
        fun of(startInclusive: Instant, endExclusive: Instant): Interval {
            if (endExclusive.isBefore(startInclusive)) {
                throw DateTimeException("End instant must be equal or after start instant")
            }
            return Interval(startInclusive, endExclusive)
        }

        /**
         * Obtains an instance of {@code Interval} from the start and a duration.
         * <p>
         * The end instant is calculated as the start plus the duration.
         * The duration must not be negative.
         *
         * @param startInclusive the start instant, inclusive, not null
         * @param duration the duration from the start to the end, not null
         * @return the interval, not null
         * @throws DateTimeException if the end is before the start,
         *  or if the duration addition cannot be made
         * @throws ArithmeticException if numeric overflow occurs when adding the duration
         */
        fun of(startInclusive: Instant, duration: Duration): Interval {
            if (duration.isNegative) {
                throw DateTimeException("Duration must not be negative")
            }
            return Interval(startInclusive, startInclusive.plus(duration))
        }
    }

    /**
     * Checks if the range is empty.
     * An empty range occurs when the start date equals the inclusive end date.
     * @return true if the range is empty
     */
    val isEmpty = start == end

    /**
     * Checks if the start of the interval is unbounded.
     *
     * @return true if start is unbounded
     */
    val isUnboundedStart = start == Instant.MIN

    /**
     * Checks if the end of the interval is unbounded.
     *
     * @return true if end is unbounded
     */
    val isUnboundedEnd = end == Instant.MAX

    /**
     * Returns a copy of this range with the specified start instant.
     *
     * @param start the start instant for the new interval, not null
     * @return an interval with the end from this interval and the specified start
     * @throws DateTimeException if the resulting interval has end before start
     */
    fun withStart(start: Instant) = of(start, end)

    /**
     * Returns a copy of this range with the specified end instant.
     *
     * @param end the end instant for the new interval, not null
     * @return an interval with the start from this interval and the specified end
     * @throws DateTimeException if the resulting interval has end before start
     */
    fun withEnd(end: Instant) = of(start, end)

    /**
     * Checks if this interval contains the specified instant.
     * <p>
     * This checks if the specified instant is within the bounds of this interval.
     * If this range has an unbounded start then {@code contains(Instant#MIN)} returns true.
     * If this range has an unbounded end then {@code contains(Instant#MAX)} returns true.
     * If this range is empty then this method always returns false.
     *
     * @param instant the instant, not null
     * @return true if this interval contains the instant
     */
    operator fun contains(instant: Instant) =
        start <= instant && (instant < end || isUnboundedEnd)

    /**
     * Checks if this interval encloses the specified interval.
     * <p>
     * This checks if the bounds of the specified interval are within the bounds of this interval.
     * An empty interval encloses itself.
     *
     * @param other the other interval, not null
     * @return true if this interval contains the other interval
     */
    fun encloses(other: Interval) =
        start <= other.start && other.end <= end

    /**
     * Checks if this interval abuts the specified interval.
     * <p>
     * The result is true if the end of this interval is the start of the other, or vice versa.
     * An empty interval does not abut itself.
     *
     * @param other the other interval, not null
     * @return true if this interval abuts the other interval
     */
    fun abuts(other: Interval) =
        (end == other.start) xor (start == other.end)

    /**
     * Checks if this interval is connected to the specified interval.
     * <p>
     * The result is true if the two intervals have an enclosed interval in common, even if that interval is empty.
     * An empty interval is connected to itself.
     * <p>
     * This is equivalent to {@code (overlaps(other) || abuts(other))}.
     *
     * @param other the other interval, not null
     * @return true if this interval is connected to the other interval
     */
    fun isConnected(other: Interval) =
        this == other || start <= other.end && other.start <= end

    /**
     * Checks if this interval overlaps the specified interval.
     * <p>
     * The result is true if the the two intervals image_pzz_fox some part of the time-line.
     * An empty interval overlaps itself.
     * <p>
     * This is equivalent to {@code (isConnected(other) && !abuts(other))}.
     *
     * @param other the time interval to compare to, null means a zero length interval now
     * @return true if the time intervals overlap
     */
    fun overlaps(other: Interval) =
        other == this || start < other.end && other.start < end

    /**
     * Calculates the interval that is the intersection of this interval and the specified interval.
     * <p>
     * This finds the intersection of two intervals.
     * This throws an exception if the two intervals are not {@linkplain #isConnected(Interval) connected}.
     *
     * @param other the other interval to check for, not null
     * @return the interval that is the intersection of the two intervals
     * @throws DateTimeException if the intervals do not connect
     */
    fun intersection(other: Interval): Interval {
        if (!isConnected(other)) {
            throw DateTimeException("Intervals do not connect: " + this + " and " + other)
        }
        val cmpStart = start.compareTo(other.start)
        val cmpEnd = end.compareTo(other.end)
        return if (cmpStart >= 0 && cmpEnd <= 0) {
            this
        } else if (cmpStart <= 0 && cmpEnd >= 0) {
            other
        } else {
            val newStart = if (cmpStart >= 0) start else other.start
            val newEnd = if (cmpEnd <= 0) end else other.end
            of(newStart, newEnd)
        }
    }

    /**
     * Calculates the interval that is the union of this interval and the specified interval.
     * <p>
     * This finds the union of two intervals.
     * This throws an exception if the two intervals are not {@linkplain #isConnected(Interval) connected}.
     *
     * @param other the other interval to check for, not null
     * @return the interval that is the union of the two intervals
     * @throws DateTimeException if the intervals do not connect
     */
    fun union(other: Interval): Interval {
        if (!isConnected(other)) {
            throw DateTimeException("Intervals do not connect: $this and $other")
        }
        val cmpStart = start.compareTo(other.start)
        val cmpEnd = end.compareTo(other.end)
        return if (cmpStart >= 0 && cmpEnd <= 0) {
            other
        } else if (cmpStart <= 0 && cmpEnd >= 0) {
            this
        } else {
            val newStart = if (cmpStart >= 0) other.start else start
            val newEnd = if (cmpEnd <= 0) other.end else end
            of(newStart, newEnd)
        }
    }

    /**
     * Calculates the smallest interval that encloses this interval and the specified interval.
     * <p>
     * The result of this method will {@linkplain #encloses(Interval) enclose}
     * this interval and the specified interval.
     *
     * @param other the other interval to check for, not null
     * @return the interval that spans the two intervals
     */
    fun span(other: Interval): Interval {
        val cmpStart = start.compareTo(other.start)
        val cmpEnd = end.compareTo(other.end)
        val newStart = if (cmpStart >= 0) other.start else start
        val newEnd = if (cmpEnd <= 0) other.end else end
        return of(newStart, newEnd)
    }

    /**
     * Checks if this interval is after the specified instant.
     * <p>
     * The result is true if this instant starts after the specified instant.
     * An empty interval behaves as though it is an instant for comparison purposes.
     *
     * @param instant the other instant to compare to, not null
     * @return true if the start of this interval is after the specified instant
     */
    fun isAfter(instant: Instant) = start > instant

    /**
     * Checks if this interval is before the specified instant.
     * <p>
     * The result is true if this instant ends before the specified instant.
     * Since intervals do not include their end points, this will return true if the
     * instant equals the end of the interval.
     * An empty interval behaves as though it is an instant for comparison purposes.
     *
     * @param instant the other instant to compare to, not null
     * @return true if the start of this interval is before the specified instant
     */
    fun isBefore(instant: Instant) = end <= instant && start < instant

    /**
     * Checks if this interval is after the specified interval.
     * <p>
     * The result is true if this instant starts after the end of the specified interval.
     * Since intervals do not include their end points, this will return true if the
     * instant equals the end of the interval.
     * An empty interval behaves as though it is an instant for comparison purposes.
     *
     * @param interval the other interval to compare to, not null
     * @return true if this instant is after the specified instant
     */
    fun isAfter(interval: Interval) = start >= interval.end && interval != this

    /**
     * Checks if this interval is before the specified interval.
     * <p>
     * The result is true if this instant ends before the start of the specified interval.
     * Since intervals do not include their end points, this will return true if the
     * two intervals abut.
     * An empty interval behaves as though it is an instant for comparison purposes.
     *
     * @param interval the other interval to compare to, not null
     * @return true if this instant is before the specified instant
     */
    fun isBefore(interval: Interval) = end <= interval.start && interval != this

    /**
     * Obtains the duration of this interval.
     * <p>
     * An {@code Interval} is associated with two specific instants on the time-line.
     * A {@code Duration} is simply an amount of time, separate from the time-line.
     *
     * @return the duration of the time interval
     * @throws ArithmeticException if the calculation exceeds the capacity of {@code Duration}
     */
    fun toDuration(): Duration = Duration.between(start, end)

    /**
     * Checks if this interval is equal to another interval.
     * <p>
     * Compares this {@code Interval} with another ensuring that the two instants are the same.
     * Only objects of type {@code Interval} are compared, other types return false.
     *
     * @param obj the object to check, null returns false
     * @return true if this is equal to the other interval
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other is Interval) {
            return start == other.start && end == other.end
        }
        return false
    }

    /**
     * A hash code for this interval.
     *
     * @return a suitable hash code
     */
    override fun hashCode() = start.hashCode() xor end.hashCode()

    /**
     * Outputs this interval as a {@code String}, such as {@code 2007-12-03T10:15:30/2007-12-04T10:15:30}.
     * <p>
     * The output will be the ISO-8601 format formed by combining the
     * {@code toString()} methods of the two instants, separated by a forward slash.
     *
     * @return a string representation of this instant, not null
     */
    override fun toString() = "$start / $end"
}
