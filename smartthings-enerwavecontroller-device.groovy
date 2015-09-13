/**
 *  Enerwave 7-button Scene Controller. Sets up the scene controller to accounce scene 1-7 on buttons 1-7.
 *  You can use the Enerwave Controller Lights or Enerwave Controller Mode SmartApps to take actions based on the scene. 
 *
 *  Copyright 2014 Jason Grey
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
metadata {
	
	definition (name: "Enerwave 7-Button Scene Controller", namespace: "jt55401", author: "Jason Grey") {
		
		capability "Configuration"
		capability "Button"
		capability "Polling"
		
		attribute "scene","number"
		
		fingerprint profileId: "0x0202", inClusters: "0x21 0x2D 0x85 0x86 0x72"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles {
		standardTile("Scene", "device.scene", width: 2, height: 2) {
			state "1", label: "Scene 1", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
			state "2", label: "Scene 2", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
			state "3", label: "Scene 3", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
			state "4", label: "Scene 4", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
			state "5", label: "Scene 5", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
			state "6", label: "Scene 6", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
			state "7", label: "Scene 7", icon: "st.unknown.zwave.remote-controller", backgroundColor: "#ffffff"
		}
		standardTile("configure", "device.configure", inactiveLabel: false, decoration: "flat") {
			state "configure", label:'', action:"configuration.configure", icon:"st.secondary.configure"
		}
		main "Scene"
			details(["scene","configure"])
	}
}

def parse(String description) {
	def result = null
	def cmd = zwave.parse(description)
	if (cmd) {
		result = zwaveEvent(cmd)
		log.debug "Parsed ${cmd} to ${result.inspect()}"
	} else {
		log.debug "Non-parsed event: ${description}"
	}
	return result
}

def zwaveEvent(physicalgraph.zwave.commands.sceneactivationv1.SceneActivationSet cmd){
	def map = [:]
	map.name = "scene"
	map.value = cmd.sceneId
	return createEvent(map)
}

def zwaveEvent(cmd){
	return null
}

def configure() {
	log.debug "configure"
	delayBetween([
		zwave.associationV1.associationSet(groupingIdentifier:1, nodeId:zwaveHubNodeId).format(),
		zwave.sceneControllerConfV1.sceneControllerConfSet(groupId:1, sceneId:1).format(),
		zwave.associationV1.associationSet(groupingIdentifier:2, nodeId:zwaveHubNodeId).format(),
		zwave.sceneControllerConfV1.sceneControllerConfSet(groupId:2, sceneId:2).format(),
		zwave.associationV1.associationSet(groupingIdentifier:3, nodeId:zwaveHubNodeId).format(),
		zwave.sceneControllerConfV1.sceneControllerConfSet(groupId:3, sceneId:3).format(),
		zwave.associationV1.associationSet(groupingIdentifier:4, nodeId:zwaveHubNodeId).format(),
		zwave.sceneControllerConfV1.sceneControllerConfSet(groupId:4, sceneId:4).format(),
		zwave.associationV1.associationSet(groupingIdentifier:5, nodeId:zwaveHubNodeId).format(),
		zwave.sceneControllerConfV1.sceneControllerConfSet(groupId:5, sceneId:5 ).format(),
		zwave.associationV1.associationSet(groupingIdentifier:6, nodeId:zwaveHubNodeId).format(),
		zwave.sceneControllerConfV1.sceneControllerConfSet(groupId:6, sceneId:6 ).format(),
		zwave.associationV1.associationSet(groupingIdentifier:7, nodeId:zwaveHubNodeId).format(),
		zwave.sceneControllerConfV1.sceneControllerConfSet(groupId:7, sceneId:7).format()
	])
}

