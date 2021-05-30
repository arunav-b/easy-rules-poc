package me.arunav.rules

import me.arunav.model.Enrollment
import org.jeasy.rules.annotation.Action
import org.jeasy.rules.annotation.Condition
import org.jeasy.rules.annotation.Rule
import org.jeasy.rules.api.Fact
import org.jeasy.rules.api.Facts

@Rule(name = "ActiveEnrollment", description = "Checks if the provided enrollment is active", priority = 4)
object ActiveEnrollment {

    @Condition
    fun `when`(facts: Facts) = facts.get<List<Enrollment>>("enrollmentsByCardAccounts").any { it.enrollmentStatus == 1 }

    @Action
    fun then(facts: Facts) = facts.add(Fact("ActiveEnrollment", true))
}