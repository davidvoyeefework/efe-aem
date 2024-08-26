/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2020 Adobe Systems Incorporated
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

const path = require("path");

const BUILD_DIR = path.join(__dirname, "dist");
const CLIENTLIB_DIR = path.join(
  __dirname,
  "..",
  "ui.apps",
  "src",
  "main",
  "content",
  "jcr_root",
  "apps",
  "efe",
  "clientlibs"
);

const libsBaseConfig = {
  allowProxy: true,
  serializationFormat: "xml",
  cssProcessor: ["default:none", "min:none"],
  jsProcessor: ["default:none", "min:none"],
};

// Config for `aem-clientlib-generator`
module.exports = {
  context: BUILD_DIR,
  clientLibRoot: CLIENTLIB_DIR,
  libs: [
    {
      ...libsBaseConfig,
      name: "clientlib-dependencies",
      categories: ["efe.dependencies"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-dependencies",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-dependencies",
          files: ["**/*.css"],
          flatten: false,
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-site",
      categories: ["efe.site"],
      dependencies: [],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-site",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-site",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-site",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-fe",
      categories: ["fe.site"],
      dependencies: ["efe.site"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-fe",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-fe",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-fe",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
//    {
//      ...libsBaseConfig,
//      name: "clientlib-wpi",
//      categories: ["fe.wpi"],
//      dependencies: ["efe.site"],
//      assets: {
//        // Copy entrypoint scripts and stylesheets into the respective ClientLib
//        // directories
//        js: {
//          cwd: "clientlib-wpi",
//          files: ["**/*.js"],
//          flatten: false,
//        },
//        css: {
//          cwd: "clientlib-wpi",
//          files: ["**/*.css"],
//          flatten: false,
//        },
//
//        // Copy all other files into the `resources` ClientLib directory
//        resources: {
//          cwd: "clientlib-wpi",
//          files: ["**/*.*"],
//          flatten: false,
//          ignore: ["**/*.js", "**/*.css"],
//        },
//      },
//    },
    {
      ...libsBaseConfig,
      name: "clientlib-wpialight",
      categories: ["fe.wpialight"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-wpialight",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-wpialight",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-wpialight",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-wpiafa",
      categories: ["fe.wpiafa"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-wpiafa",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-wpiafa",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-wpiafa",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-wpifidelity",
      categories: ["fe.wpifidelity"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-wpifidelity",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-wpifidelity",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-wpifidelity",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-wpimellon",
      categories: ["fe.wpimellon"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-wpimellon",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-wpimellon",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-wpimellon",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-wpiprincipal",
      categories: ["fe.wpiprincipal"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-wpiprincipal",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-wpiprincipal",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-wpiprincipal",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-wpitransamerica",
      categories: ["fe.wpitransamerica"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-wpitransamerica",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-wpitransamerica",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-wpitransamerica",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-wpivanguard",
      categories: ["fe.wpivanguard"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-wpivanguard",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-wpivanguard",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-wpivanguard",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
    {
      ...libsBaseConfig,
      name: "clientlib-wpivoya",
      categories: ["fe.wpivoya"],
      assets: {
        // Copy entrypoint scripts and stylesheets into the respective ClientLib
        // directories
        js: {
          cwd: "clientlib-wpivoya",
          files: ["**/*.js"],
          flatten: false,
        },
        css: {
          cwd: "clientlib-wpivoya",
          files: ["**/*.css"],
          flatten: false,
        },

        // Copy all other files into the `resources` ClientLib directory
        resources: {
          cwd: "clientlib-wpivoya",
          files: ["**/*.*"],
          flatten: false,
          ignore: ["**/*.js", "**/*.css"],
        },
      },
    },
  ],
};
