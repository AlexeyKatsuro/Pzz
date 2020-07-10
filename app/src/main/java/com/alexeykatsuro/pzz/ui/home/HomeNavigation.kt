package com.alexeykatsuro.pzz.ui.home

import com.alexeykatsuro.pzz.R
import com.alexeykatsuro.pzz.utils.NavCommand
import javax.inject.Inject

interface HomeNavigation {
     val toBasket: NavCommand
}

class HomeNavigationImpl @Inject constructor() :
    HomeNavigation {
     override val toBasket: NavCommand = NavCommand(R.id.showBasketFragment)
}