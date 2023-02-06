import Handlebars from "handlebars/runtime.js";
import "../../../site/main.scss";
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
  },
};

const TemplatePrimary = ({ label, ...args }) => Seperator({ ...args });
export const SeperatorPrimary = TemplatePrimary.bind();
SeperatorPrimary.args = {
  variant: "primary",
}
