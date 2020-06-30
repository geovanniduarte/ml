package com.geo.mercadolibre.product.ui.detail

import androidx.transition.TransitionSet.ORDERING_TOGETHER
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.ChangeTransform
import androidx.transition.TransitionSet

public class DetailsTransition: TransitionSet {
    constructor() {
        ordering = ORDERING_TOGETHER
        addTransition( ChangeBounds())
            .addTransition( ChangeTransform())
            .addTransition( ChangeImageTransform())
    }
}