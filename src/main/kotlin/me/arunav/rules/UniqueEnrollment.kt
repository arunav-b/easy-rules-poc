package me.arunav.rules

import me.arunav.model.Enrollment
import org.jeasy.rules.annotation.Action
import org.jeasy.rules.annotation.Condition
import org.jeasy.rules.annotation.Rule
import org.jeasy.rules.api.Fact
import org.jeasy.rules.api.Facts

@Rule(
    name = "UniqueEnrollment",
    description = "Checks if the provided instrument has no other enrollments other than the input set of card accounts",
    priority = 3
)
object UniqueEnrollment {

    @Condition
    fun `when`(facts: Facts) = facts.get<List<Enrollment>>("enrollmentsByCardAccounts")
        .count { enrollment ->
            facts.get<List<Enrollment>>("enrollmentsByInstruments")
                .none { enrollment.enrollmentId != it.enrollmentId }
        }
        .let { it < 1 }

    @Action
    fun then(facts: Facts) = facts.add(Fact("UniqueEnrollment", true))
}