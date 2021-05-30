package me.arunav.rules

import me.arunav.model.Bank
import org.jeasy.rules.annotation.Action
import org.jeasy.rules.annotation.Condition
import org.jeasy.rules.annotation.Priority
import org.jeasy.rules.annotation.Rule
import org.jeasy.rules.api.Fact
import org.jeasy.rules.api.Facts

@Rule(name = "ActiveBank", description = "Checks if the provided bank is active", priority = 1)
object ActiveBank {

    @Condition
    fun `when`(facts: Facts) = facts.get<Bank>("bank").bankStatus == "ACTIVE"

    @Action
    fun then(facts: Facts) = facts.add(Fact("ActiveBank", true))
}