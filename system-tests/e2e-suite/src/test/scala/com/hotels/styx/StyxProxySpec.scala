/**
 * Copyright (C) 2013-2017 Expedia Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hotels.styx

import com.hotels.styx.infrastructure.MemoryBackedRegistry
import com.hotels.styx.plugins.PluginPipelineSpec
import com.hotels.styx.support.{ImplicitStyxConversions, configuration}
import com.hotels.styx.support.configuration.{ImplicitOriginConversions, StyxBackend, StyxBaseConfig}
import org.scalatest._


trait StyxProxySpec extends StyxClientSupplier
  with BeforeAndAfterAll
  with BeforeAndAfterEach
  with ShouldMatchers
  with ImplicitOriginConversions
  with ImplicitStyxConversions
  with StyxConfiguration
  with SSLSetup {
  this: Suite =>

  var backendsRegistry = new MemoryBackedRegistry[com.hotels.styx.client.applications.BackendService]
  var styxServer: StyxServer = _

  implicit class StyxServerOperations(val styxServer: StyxServer) extends StyxServerSupplements
    with BackendServicesRegistrySupplier {
    def setBackends(backends: (String, StyxBackend)*): Unit = setBackends(backendsRegistry, backends:_*)
  }

  def resourcesPluginsPath: String = {
    val url = classOf[PluginPipelineSpec].getClassLoader.getResource("plugins")
    url.getPath
  }

  override protected def beforeAll() = {
    styxServer = styxConfig.startServer(backendsRegistry)
    println("Styx http port is: [%d]".format(styxServer.httpPort))
    println("Styx https port is: [%d]".format(styxServer.secureHttpPort))
  }

  override protected def afterAll() = {
    println("Styx http port was: [%d]".format(styxServer.httpPort))
    println("Styx https port was: [%d]".format(styxServer.secureHttpPort))
    styxServer.stopAsync().awaitTerminated()
  }
}

trait StyxConfiguration {
  def styxConfig: StyxBaseConfig
}

trait DefaultStyxConfiguration extends StyxConfiguration {
  lazy val styxConfig: StyxBaseConfig = configuration.StyxConfig()
}
