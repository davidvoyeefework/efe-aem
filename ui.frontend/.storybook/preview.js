// Stylesheets
import "../src/main/webpack/site/main.scss";
import "../src/main/webpack/site/core-components/main.ts";

import "../src/main/webpack/site/main.ts";
// import "../src/main/webpack/efe-unbounce/site/main.ts"
// import "../src/main/webpack/efe-unbounce/site/main.scss"
import { MINIMAL_VIEWPORTS } from '@storybook/addon-viewport';

const customViewports = {
  mobile1: {
    name: 'Small mobile',
    styles: {
      width: '375px',
      height: '568px',
    },
  },
};

export const parameters = {
  viewport: {
    viewports: {
      ...MINIMAL_VIEWPORTS,
      ...customViewports,
    },
    defaultViewport: 'responsive',
  },
};

