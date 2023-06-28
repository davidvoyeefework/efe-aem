import Handlebars from "handlebars/runtime.js";
import Seperator from "../seperator.hbs";
Handlebars.registerPartial("Seperator", Seperator);
// More on default export: https://storybook.js.org/docs/html/writing-stories/introduction#default-export
export default {
  title: "Core Components/Seperator",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["primary"],
      control: { type: "radio" },
    },
    color : {
      options: [ "efe-gray-mid", "efe-gray-blue" ],
      control: { type: "select" },
    },
    id: {
      control: { type: "text" },
    },
  },
};

const TemplatePrimary = ({ label, ...args }) => Seperator({ ...args });
export const SeperatorPrimary = TemplatePrimary.bind();
SeperatorPrimary.args = {
  variant: "primary",
  color: "efe-gray-blue"
}
