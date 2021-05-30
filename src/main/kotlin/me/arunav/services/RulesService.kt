package me.arunav.services

import me.arunav.model.Bank
import me.arunav.model.Enrollment
import me.arunav.model.Instrument
import me.arunav.model.Payment
import me.arunav.rules.*
import org.jeasy.rules.api.*
import org.jeasy.rules.core.DefaultRulesEngine
import org.slf4j.LoggerFactory
import java.time.LocalDate
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class RulesService {

    fun process(data: String): String {

        val startTime = System.currentTimeMillis()
        val bank = Bank("R1", "ACTIVE", "DUMMY BANK1")
        val enrollmentsByCardAccounts = mutableListOf<Enrollment>()
        enrollmentsByCardAccounts.addAll(
            listOf(
                Enrollment(1, "C1", 101, "R1", "BA1", 1),
                Enrollment(2, "C2", 101, "R1", "BA1", 1),
                Enrollment(3, "C3", 101, "R1", "BA1", 1),
            )
        )
        val payments = mutableListOf<Payment>()
        payments.addAll(
            listOf(
                Payment(201, "C1", "R1", "BA1", LocalDate.now().minusDays(50), 2),
                Payment(202, "C1", "R1", "BA1", LocalDate.now().minusDays(150), 2),
                Payment(203, "C1", "R1", "BA1", LocalDate.now().minusDays(250), 2),
                Payment(204, "C1", "R1", "BA1", LocalDate.now().minusDays(350), 2),
                Payment(205, "C1", "R1", "BA1", LocalDate.now().minusDays(450), 2),
            )
        )

        // Facts
        val facts = Facts()
        facts.add(Fact("instrument", Instrument("R1", "BA1")))
        facts.add(Fact("enrollmentsByCardAccounts", enrollmentsByCardAccounts))
        facts.add(Fact("enrollmentsByInstruments", enrollmentsByCardAccounts))
        facts.add(Fact("payments", payments))
        facts.add(Fact("bank", bank))

        // Rules
        val rules = Rules(
            ActiveBank,
            ActiveEnrollment,
            UniqueEnrollment,
            MinNumberOfPaymentsInATimeRange,
            MaxAllowedReturnsInATimeRange,
        )

        val parameters = RulesEngineParameters()
            .skipOnFirstFailedRule(true)
            .skipOnFirstNonTriggeredRule(true)
        // Fire rules-engine
        val rulesEngine = DefaultRulesEngine(parameters)

        var check = rulesEngine.check(rules, facts)
        LOG.info("before...")
        check.entries.forEach { LOG.info("Rule=${it.key.name}, Status=${it.value}") }
        rulesEngine.fire(rules, facts)

        // Check rule status
        check = rulesEngine.check(rules, facts)
        LOG.info("after...")
        check.entries.forEach { LOG.info("Rule=${it.key.name}, Status=${it.value}") }
        facts.asMap().entries.forEach { LOG.info("key=${it.key}, value=${it.value}") }
        LOG.info("Total time taken = ${System.currentTimeMillis() - startTime}")
        return "Rules Processed"
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(RulesService::class.java)
    }
}