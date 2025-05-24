package com.colderonetrains.battlesskates.game.utils

import com.colderonetrains.battlesskates.game.data.DataTrick

const val WIDTH_UI  = 1080f
const val HEIGHT_UI = 1920f

const val TIME_ANIM_SCREEN = 0.225f

val GLOBAL_listDataTrick_Beginner = listOf(
    DataTrick(
        nName       = "Ollie",
        description = "The fundamental skate trick where you pop your board into the air without grabbing it.",
        listStep    = listOf(
            "Place your back foot on the tail, front foot near the center.",
            "Pop the tail down sharply with your back foot.",
            "Slide your front foot up towards the nose of the board.",
            "Lift your knees to raise the board higher.",
            "Level out and land with bent knees to absorb impact.",
        )
    ),

    DataTrick(
        nName = "Manual",
        description = "Balancing on two wheels while rolling, either front (nose manual) or back wheels (manual).",
        listStep = listOf(
            "Approach with moderate speed, feet evenly positioned.",
            "Slightly shift weight to your back foot (for manual).",
            "Lift the front wheels off the ground, maintaining balance.",
            "Hold your balance evenly, controlling with subtle body adjustments.",
            "Gently lower the wheels to land safely.",
        )
    ),

    DataTrick(
        nName = "Kickturn",
        description = "Turning your skateboard quickly by pivoting on the back wheels.",
        listStep = listOf(
            "Ride slowly or stop completely with knees bent.",
            "Press down lightly on your tail with your back foot.",
            "Swing your shoulders and hips in the desired direction.",
            "Pivot on your back wheels, guiding with your front foot.",
            "Level the board out to ride away smoothly.",
        )
    ),

    DataTrick(
        nName = "Shuvit",
        description = "Spinning your board 180 degrees horizontally beneath you without flipping.",
        listStep = listOf(
            "Stand with your back foot on the tail, front foot near bolts.",
            "Scoop the tail behind you using your back foot.",
            "Jump slightly upward, allowing the board to spin beneath you.",
            "Catch the board with your feet as it completes a 180-degree rotation.",
            "Land balanced and roll away with bent knees.",
        )
    ),

    DataTrick(
        nName = "Hippie Jump",
        description = "Jumping off your board into the air while it rolls beneath an obstacle, then landing back on.",
        listStep = listOf(
            "Approach obstacle at steady speed.",
            "Position your feet slightly wider than usual for balance.",
            "Jump vertically upwards, allowing the board to continue rolling underneath.",
            "Clear the obstacle in the air with your body.",
            "Land back on your rolling board, bending knees slightly to maintain control.",
        )
    ),

    DataTrick(
        nName = "Caveman",
        description = "Jumping onto your skateboard from your hands while holding it.",
        listStep = listOf(
            "Hold your skateboard by the nose with your hand.",
            "Run or stand still, preparing to jump onto your board.",
            "Swing your board gently forward, releasing it evenly below you.",
            "Jump onto your board in mid-air, placing feet securely on bolts.",
            "Land balanced, knees bent slightly, and roll away smoothly.",
        )
    ),

    DataTrick(
        nName = "Nose Stall",
        description = "Balancing on the front (nose) of your board on a ledge or coping.",
        listStep = listOf(
            "Approach the ledge or coping slowly and straight.",
            "Lift your front trucks up onto the edge by gently popping the tail.",
            "Shift your weight onto the nose of your skateboard.",
            "Balance steadily with knees slightly bent and arms out.",
            "Gently shift weight backward and roll away smoothly.",
        )
    ),

    DataTrick(
        nName = "Fakie Ollie",
        description = "Performing an ollie while rolling backwards (in fakie stance).",
        listStep = listOf(
            "Roll backwards (fakie stance) with feet positioned as in regular ollie.",
            "Snap the tail sharply (the nose acts as tail when riding fakie).",
            "Slide your front foot upward toward the nose.",
            "Lift your knees to raise the board higher.",
            "Level out your skateboard and land cleanly, rolling away fakie.",
        )
    ),

    DataTrick(
        nName = "180 Ollie",
        description = "An ollie combined with a 180-degree rotation, landing facing the opposite direction.",
        listStep = listOf(
            "Set feet like a regular ollie, body slightly twisted in the direction you plan to spin.",
            "Pop your tail down sharply.",
            "Slide your front foot upward while rotating your shoulders and hips.",
            "Rotate 180 degrees in mid-air along with the board.",
            "Land smoothly facing the opposite direction, knees bent to absorb impact.",
        )
    ),

    DataTrick(
        nName = "Boneless",
        description = "Grabbing your board with your hand and planting your foot on the ground briefly while jumping.",
        listStep = listOf(
            "Roll slowly or at medium speed.",
            "Reach down and grab your skateboard's rail with your back hand.",
            "Step off briefly with your front foot onto the ground, lifting the skateboard upward.",
            "Jump upwards, bringing your front foot back onto the skateboard mid-air.",
            "Land with your feet firmly on the skateboard, bend knees slightly, and roll away.",
        )
    )


)
val GLOBAL_listDataTrick_Intermediate = listOf(
    DataTrick(
        nName = "Kickflip",
        description = "A trick where your skateboard flips horizontally beneath your feet during an ollie.",
        listStep = listOf(
            "Place your back foot on the tail, front foot angled slightly behind bolts.",
            "Pop the tail down sharply as in an ollie.",
            "Slide your front foot diagonally off the side of the nose to flip the board.",
            "Allow the board to spin horizontally beneath your feet.",
            "Catch the board mid-air with both feet and land balanced, knees bent slightly.",
        )
    ),

    DataTrick(
        nName = "Heelflip",
        description = "Similar to a kickflip, but the board flips in the opposite direction using your heel.",
        listStep = listOf(
            "Set feet like an ollie, front foot toes slightly hanging off the edge.",
            "Pop the tail sharply downwards.",
            "Kick outward and forward with your front heel to flip the board opposite of a kickflip.",
            "Allow the skateboard to rotate horizontally underneath you.",
            "Catch the board with your feet in mid-air, land smoothly, knees slightly bent.",
        )
    ),

    DataTrick(
        nName = "Pop Shuvit",
        description = "A trick where the skateboard rotates horizontally 180 degrees beneath you.",
        listStep = listOf(
            "Set your feet in ollie position, back foot centered on the tail.",
            "Pop and scoop the tail slightly behind you to spin the board 180 degrees.",
            "Jump upwards, allowing the skateboard to rotate under you.",
            "Catch the skateboard with your feet once it completes the rotation.",
            "Land steadily, knees bent to absorb impact, and roll away.",
        )
    ),

    DataTrick(
        nName = "50-50 Grind",
        description = "Grinding both trucks evenly along a ledge or rail.",
        listStep = listOf(
            "Approach ledge or rail at moderate speed, slightly angled.",
            "Pop an ollie, positioning the board parallel above the obstacle.",
            "Land evenly with both trucks firmly locked onto the edge.",
            "Maintain balance, distributing weight evenly between front and back feet.",
            "Shift your weight slightly backward, ollie off the edge smoothly, and roll away.",
        )
    ),

    DataTrick(
        nName = "Boardslide",
        description = "Sliding across a rail or ledge with the middle of your board, perpendicular to the obstacle.",
        listStep = listOf(
            "Approach rail or ledge at slight angle with moderate speed.",
            "Ollie onto obstacle, turning 90 degrees mid-air.",
            "Land centered with the rail beneath your skateboard, feet evenly spaced.",
            "Maintain balance with shoulders aligned, knees slightly bent.",
            "Turn your shoulders and hips back to regular stance, land clearly, and roll away smoothly.",
        )
    ),

    DataTrick(
        nName = "Frontside 180",
        description = "Performing an ollie combined with a 180-degree rotation, turning your chest forward in the direction of travel.",
        listStep = listOf(
            "Position feet as for a regular ollie, shoulders slightly pre-wound forward.",
            "Pop the tail sharply downwards while rotating your shoulders and hips frontside.",
            "Slide front foot up to level the board during rotation.",
            "Complete the 180-degree spin mid-air, turning your chest forward.",
            "Land evenly and roll away switch, knees slightly bent for balance.",
        )
    ),

    DataTrick(
        nName = "Backside 180",
        description = "An ollie combined with a 180-degree spin, turning your back forward (backside).",
        listStep = listOf(
            "Position your feet in ollie stance, shoulders slightly pre-wound backside.",
            "Pop the tail down sharply, turning shoulders and hips backside.",
            "Slide front foot upwards to level board mid-air while rotating backside.",
            "Complete the 180-degree rotation, turning your back forward.",
            "Land securely rolling switch, knees bent slightly to absorb impact.",
        )
    ),

    DataTrick(
        nName = "Varial Kickflip",
        description = "A combination of a kickflip and pop shuvit—the board flips and spins 180 degrees simultaneously.",
        listStep = listOf(
            "Place your back foot for pop shuvit position, front foot as for kickflip.",
            "Pop and scoop tail backward while flicking your front foot diagonally forward.",
            "Allow board to simultaneously flip and rotate beneath your feet.",
            "Catch board mid-air after completing rotation.",
            "Land centered, knees slightly bent, and roll away steadily.",
        )
    ),

    DataTrick(
        nName = "Manual Kickflip Out",
        description = "Performing a manual and ending the balance by popping a kickflip out.",
        listStep = listOf(
            "Approach smoothly and perform a manual by balancing on back wheels.",
            "Maintain steady balance and prepare for exit.",
            "Near end of manual, set front foot in kickflip position.",
            "Pop the tail and flick front foot diagonally to flip the board.",
            "Catch the flipping board mid-air, land balanced, and roll away cleanly.",
        )
    ),

    DataTrick(
        nName = "Nollie",
        description = "Performing an ollie from the nose of the skateboard while rolling forward in your normal stance.",
        listStep = listOf(
            "Position front foot firmly on the nose, back foot in the center.",
            "Pop sharply down with your front foot from the nose.",
            "Slide back foot upwards to level board mid-air.",
            "Lift knees to get additional height and board control.",
            "Land evenly, knees slightly bent, and roll away smoothly.",
        )
    )

)
val GLOBAL_listDataTrick_Pro = listOf(
    DataTrick(
        nName = "360 Flip (Tre Flip)",
        description = "A trick combining a kickflip and a 360-degree pop shuvit, spinning and flipping simultaneously beneath your feet.",
        listStep = listOf(
            "Place your back foot in the tail pocket, front foot angled like a kickflip.",
            "Scoop the tail sharply and strongly to initiate a 360-degree shuvit rotation.",
            "Flick your front foot diagonally outward to trigger the flip simultaneously.",
            "Allow the skateboard to rotate fully beneath your feet.",
            "Catch the board mid-air, land with bent knees, balanced and roll away.",
        )
    ),

    DataTrick(
        nName = "Hardflip",
        description = "A challenging trick combining a frontside shuvit and a kickflip, rotating vertically between your legs.",
        listStep = listOf(
            "Set your back foot in pop position, front foot angled like a kickflip.",
            "Pop tail hard while flicking front foot diagonally forward, slightly backward motion.",
            "Allow board to flip vertically between your legs while spinning frontside 180°.",
            "Spread your feet slightly to avoid interference, let the board complete rotation.",
            "Catch the board cleanly, land with knees bent, and roll away smoothly.",
        )
    ),

    DataTrick(
        nName = "Inward Heelflip",
        description = "Combining a backside pop shuvit with a heelflip, causing the board to spin inward.",
        listStep = listOf(
            "Position feet similar to a heelflip, back foot set for scooping inward.",
            "Pop and scoop tail inwardly with your back foot while flicking front heel forward.",
            "Allow skateboard to rotate horizontally backside and flip simultaneously.",
            "Catch the rotating board mid-air securely.",
            "Land balanced with knees bent to absorb impact and roll away.",
        )
    ),

    DataTrick(
        nName = "Laser Flip",
        description = "A 360-degree frontside shuvit combined with a heelflip—a challenging and stylish trick.",
        listStep = listOf(
            "Set back foot in pocket of tail, front foot angled as for heelflip.",
            "Scoop tail sharply forward and outward (frontside direction).",
            "Flick your front heel outward to initiate the heelflip rotation.",
            "Allow skateboard to fully spin 360 degrees horizontally and flip beneath you.",
            "Catch the skateboard firmly mid-air, land balanced with bent knees, and roll away.",
        )
    ),

    DataTrick(
        nName = "Crooked Grind",
        description = "Grinding on a rail or ledge with your front truck at an angled position, tail slightly raised.",
        listStep = listOf(
            "Approach the ledge or rail at moderate speed and a slight angle.",
            "Ollie onto the obstacle, placing front truck angled onto the edge (nose pointing downwards slightly).",
            "Shift weight forward to lock securely into the grind position.",
            "Balance steadily, tail elevated slightly, controlling with subtle adjustments.",
            "Exit by slightly popping or shifting weight off the edge, land cleanly, and roll away smoothly.",
        )
    ),

    DataTrick(
        nName = "Noseblunt Slide",
        description = "Sliding on a ledge or rail balanced only on your skateboard's nose with your tail angled outward.",
        listStep = listOf(
            "Approach obstacle at a moderate speed with a slight angle.",
            "Pop a high ollie, turning board 90° and positioning the nose onto the ledge.",
            "Balance weight forward firmly onto the nose, keeping tail angled outward.",
            "Slide steadily while maintaining balance with slight body adjustments.",
            "Pivot or pop out smoothly, landing cleanly with knees bent.",
        )
    ),

    DataTrick(
        nName = "Smith Grind",
        description = "Grinding with your back truck locked on the ledge or rail and your front truck angled downward, nose pointing below the obstacle level.",
        listStep = listOf(
            "Approach rail or ledge with moderate speed at slight angle.",
            "Ollie and lock back truck firmly onto obstacle, front truck hanging angled downward.",
            "Keep weight slightly toward back foot to maintain control.",
            "Slide consistently, controlling balance carefully with subtle movements.",
            "Pop or shift weight off smoothly, land balanced, knees bent, and roll away.",
        )
    ),

    DataTrick(
        nName = "360 Hardflip",
        description = "A complex combination of a 360 frontside shuvit with a kickflip, rotating vertically between the legs.",
        listStep = listOf(
            "Position back foot to scoop strongly frontside, front foot angled for kickflip.",
            "Pop sharply, scooping hard for a full 360° frontside shuvit rotation.",
            "Flick front foot diagonally outward to initiate the vertical kickflip rotation.",
            "Allow board to flip vertically between legs while completing 360° rotation.",
            "Catch the board cleanly in mid-air, land balanced with bent knees, and roll away smoothly.",
        )
    ),

    DataTrick(
        nName = "Impossible",
        description = "A vertical rotation of the skateboard around your back foot, wrapping the board around your foot mid-air.",
        listStep = listOf(
            "Set back foot near tail pocket, toes slightly hanging over, front foot near bolts.",
            "Pop tail sharply downward while scooping your back foot upward and forward.",
            "Allow skateboard to vertically wrap around your back foot completely.",
            "Keep back foot in control of rotation, guiding skateboard around fully.",
            "Unwrap the board mid-air, land securely on bolts with knees slightly bent.",
        )
    ),

    DataTrick(
        nName = "Bigspin",
        description = "A trick combining a 360-degree board rotation (shuvit) with a simultaneous 180-degree body rotation.",
        listStep = listOf(
            "Set your feet as for a pop shuvit, shoulders pre-wound slightly backside.",
            "Scoop tail strongly, initiating a 360-degree board rotation backside.",
            "Simultaneously jump and rotate your body 180 degrees backside.",
            "Allow board to fully rotate beneath your feet.",
            "Land balanced switch stance, knees bent to absorb impact, and roll away smoothly.",
        )
    )

)