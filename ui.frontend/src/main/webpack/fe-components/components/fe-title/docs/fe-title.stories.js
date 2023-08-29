import Handlebars from "handlebars/runtime.js";
import TitleFE from "../fe-title.hbs";
Handlebars.registerPartial("Title", TitleFE);
export default {
  title: "FE Components/TitleFE",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    variant: {
      options: ["cmp-title--superplus", "cmp-title--superpercentage",],
      control: { type: "radio" },
    }
  },
  args:{
    variant:'cmp-title--superpercentage',
  }
};

const TemplateTitleFE = ({ label, ...args }) => TitleFE({ ...args });
export const titleFE = TemplateTitleFE.bind();
