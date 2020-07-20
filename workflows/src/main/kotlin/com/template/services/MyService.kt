package com.template.services

import net.corda.core.node.AppServiceHub
import net.corda.core.node.services.CordaService
import net.corda.core.node.services.ServiceLifecycleEvent
import net.corda.core.serialization.SingletonSerializeAsToken
import net.corda.core.utilities.loggerFor
import java.util.concurrent.Executors

@CordaService
class MyService(private val appServiceHub: AppServiceHub) : SingletonSerializeAsToken() {
    private companion object {
        // logger
        val log = loggerFor<MyService>()

        // Thread
        val executor = Executors.newFixedThreadPool(8)!!
    }

    init {
        showNodeInfo()
        appServiceHub.register { customEvent(it) }
    }

    /**
     * custom event
     */
    private fun customEvent(event: ServiceLifecycleEvent) {
        when (event) {
            ServiceLifecycleEvent.STATE_MACHINE_STARTED -> {
                // ノードが完全に起動したら実行される
                // executor.execute{ appServiceHub.startFlow(Initiator())}
            }
            else -> {
            }
        }
    }

    /**
     * show my node info
     */
    private fun showNodeInfo() {
        val nodeInfo = appServiceHub.diagnosticsService.nodeVersionInfo()
        println("\n------ node info ------\n" +
                "Platform ver : ${nodeInfo.platformVersion} \n" +
                "Release ver  : ${nodeInfo.releaseVersion} \n" +
                "Version      : ${nodeInfo.revision} \n" +
                "Vendor       : ${nodeInfo.vendor} \n" +
                "Party Name   : ${appServiceHub.myInfo.legalIdentities[0].name} \n" +
                "------ node info ------\n")
    }
}
