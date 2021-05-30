package me.arunav.rules

import me.arunav.model.Payment
import org.jeasy.rules.annotation.Action
import org.jeasy.rules.annotation.Condition
import org.jeasy.rules.annotation.Rule
import org.jeasy.rules.api.Fact
import org.jeasy.rules.api.Facts
import java.time.LocalDate

@Rule(
    name = "MinNumberOfPaymentsInATimeRange",
    description = "Checks if the provided instrument has minimum number of payments in a specific date range",
    priority = 2
)
object MinNumberOfPaymentsInATimeRange {

    @Condition
    fun `when`(facts: Facts) = facts.get<List<Payment>>("payments")
        .count { dateCheck(it.paymentDate, 45, 390) }
        .let { it >= 6 }

    @Action
    fun then(facts: Facts) = facts.add(Fact("MinNumberOfPaymentsInATimeRange", true))

    private fun dateCheck(date: LocalDate, startRange: Long, endRange: Long) =
        date <= LocalDate.now().minusDays(startRange) && date >= LocalDate.now().minusDays(endRange)
}