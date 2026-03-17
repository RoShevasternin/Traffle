package com.quantumplay.orbitcrasher.game.utils.advanced

import com.quantumplay.orbitcrasher.game.utils.Block

abstract class AdvancedMainGroup : AdvancedGroup() {

    // Anim ------------------------------------------------

    abstract fun animShowMain(blockEnd: Block = Block {  })

    abstract fun animHideMain(blockEnd: Block = Block {  })

}