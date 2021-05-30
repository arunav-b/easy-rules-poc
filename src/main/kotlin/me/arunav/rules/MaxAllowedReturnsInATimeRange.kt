package me.arunav.rules

import me.arunav.model.Payment
import org.jeasy.rules.annotation.Action
import org.jeasy.rules.annotation.Condition
import org.jeasy.rules.annotation.Rule
import org.jeasy.rules.api.Fact
import org.jeasy.rules.api.Facts
import java.time.LocalDate

@Rule(
    name = "MaxAllowedReturnsInATimeRange",
    description = "Checks if the provided instrument has maximum allowed returns in a specific date range",
    priority = 5
)
object MaxAllowedReturnsInATimeRange{

    @Condition
    fun `when`(facts: Facts) =
        facts.get<List<Payment>>("payments")
            .count { dateCheck(it.returnDate, 0, 180) }
            .let { it < 1 }

    @Action
    fun then(facts: Facts) = facts.add(Fact("MaxAllowedReturnsInATimeRange", true))


    private fun dateCheck(date: LocalDate?, startRange: Long, endRange: Long) =
        date != null &&
                date > LocalDate.now().minusDays(startRange) && date >= LocalDate.now().minusDays(endRange)
}