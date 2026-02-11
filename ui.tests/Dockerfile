# Copyright 2023 Adobe Systems Incorporated
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

FROM cypress/included:13.17.0
 
ENV APP_PATH /usr/src/app
 
# Avoid broken/unsigned browser apt repos baked into the Cypress image
RUN rm -f /etc/apt/sources.list.d/google-chrome.list \
          /etc/apt/sources.list.d/microsoft-edge.list \
          /etc/apt/sources.list.d/microsoft-edge-stable.list \
&& apt-get -qqy update \
&& apt-get -qqy --no-install-recommends install \
        curl \
        build-essential \
&& rm -rf /var/lib/apt/lists/*
 
# Set Application Environment
WORKDIR ${APP_PATH}
COPY ./test-module ./
RUN npm install
 
ENTRYPOINT ["bash","run.sh"]
