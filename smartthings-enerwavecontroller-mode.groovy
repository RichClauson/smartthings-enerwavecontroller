/**
 *  Set mode based on scene number.
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

definition(
	name: "Set mode based on scene number",
	namespace: "jt55401",
	author: "Jason Grey",
	description: "Set the house mode based on which scene is active on a scene controller.",
	category: "My Apps",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")

preferences {
	section("When this scene controller reaches scene number...") {
		input "sceneController", "capability.button", title: "Pick an Enerwave Scene Controller", required: true
		input "sceneNumber", "enum", title: "Changes to Scene:", description: "scene number 1-7", required: true, options: ["1","2","3","4","5","6","7"]
	}
	section("Set the mode to") {
		input "theMode", "mode", title:"Set the mode to?"
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	// TODO: subscribe to attributes, devices, locations, etc.
	subscribe(sceneController, "scene", sceneChanged)
}

def sceneChanged(evt){
	def s = sceneController.latestValue("scene").toInteger()
	def s2 = sceneNumber.toInteger()
	
	log.debug "the scene is now ${s}, looking for ${s2}"
	
	if( s == s2 ){
		def msg = "Changed mode to '${theMode}' because '${sceneController}' said so."
		log.info(msg)
		sendNotificationEvent(msg)
		setLocationMode(theMode)
	}
}

// TODO: implement event handlers
