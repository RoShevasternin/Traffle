package com.slotscity.official.game.actors.sweet_bonanza.slot6x5

/** SlotMatrix6x5
 *
 * 6 slots 5 rows
 * aN..eN - SlitItem index
 *
 * */
data class Matrix6x5(
    val resultMatrix6x5: ResultMatrix6x5? = null,

    val a1: Int,
    val a2: Int,
    val a3: Int,
    val a4: Int,
    val a5: Int,

    val b1: Int,
    val b2: Int,
    val b3: Int,
    val b4: Int,
    val b5: Int,

    val c1: Int,
    val c2: Int,
    val c3: Int,
    val c4: Int,
    val c5: Int,

    val d1: Int,
    val d2: Int,
    val d3: Int,
    val d4: Int,
    val d5: Int,

    val e1: Int,
    val e2: Int,
    val e3: Int,
    val e4: Int,
    val e5: Int,

    val f1: Int,
    val f2: Int,
    val f3: Int,
    val f4: Int,
    val f5: Int,
)

/** ResultMatrix6x5
 *
 * false - fail
 * true  - win
 *
* */
data class ResultMatrix6x5(
    val a1: Boolean,
    val a2: Boolean,
    val a3: Boolean,
    val a4: Boolean,
    val a5: Boolean,

    val b1: Boolean,
    val b2: Boolean,
    val b3: Boolean,
    val b4: Boolean,
    val b5: Boolean,

    val c1: Boolean,
    val c2: Boolean,
    val c3: Boolean,
    val c4: Boolean,
    val c5: Boolean,

    val d1: Boolean,
    val d2: Boolean,
    val d3: Boolean,
    val d4: Boolean,
    val d5: Boolean,

    val e1: Boolean,
    val e2: Boolean,
    val e3: Boolean,
    val e4: Boolean,
    val e5: Boolean,

    val f1: Boolean,
    val f2: Boolean,
    val f3: Boolean,
    val f4: Boolean,
    val f5: Boolean,
)