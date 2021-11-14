package com.azielu.lorempicsumbrowser

import com.azielu.lorempicsumbrowser.rules.RxImmediateTestSchedulerRule
import org.junit.Rule

abstract class BaseTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateTestSchedulerRule()
}

