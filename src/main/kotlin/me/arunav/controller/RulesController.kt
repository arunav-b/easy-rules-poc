package me.arunav.controller

import me.arunav.services.RulesService
import org.slf4j.LoggerFactory.getLogger
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/easy-rules")
class RulesController(@Inject val rulesService: RulesService) {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{data}")
    fun processRules(@PathParam("data") data: String): String {

        LOG.info("Entry")
        return rulesService.process(data)
            .also { LOG.info("Exit") }
    }

    companion object{
        private val LOG = getLogger(RulesController::class.java)
    }
}