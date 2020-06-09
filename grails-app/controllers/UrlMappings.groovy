//package main.scripts.net.nosegrind.apiframework
/* ****************************************************************************
 * Copyright 2014 Owen Rubel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/




class UrlMappings {

    static mappings = {
        "/oauth2/$provider/callback"(controller: 'springSecurityOAuth2', action: 'callback')
        "/oauth2/$provider/success"(controller: 'springSecurityOAuth2', action: 'onSuccess')
        "/oauth2/$provider/failure"(controller: 'springSecurityOAuth2', action: 'onFailure')
        "/oauth2/ask"(controller: 'springSecurityOauth2', action: 'ask')
        "/oauth2/linkaccount"(controller: 'springSecurityOAuth2', action: 'linkAccount')
        "/oauth2/createaccount"(controller: 'springSecurityOAuth2', action: 'createAccount')

    }


}
