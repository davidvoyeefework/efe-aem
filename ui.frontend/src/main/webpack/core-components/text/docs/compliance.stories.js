import Handlebars from "handlebars/runtime.js";
import Text from "../text.hbs";

Handlebars.registerPartial("Text", Text);
export default {
  title: "Core Components/Text",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["detail"],
      control: { type: "radio" },
    }
  },
};

const TextVariations = ({ label, ...args }) => Text({ ...args });
export const Compliance = TextVariations.bind();
Compliance.args = {
  variant: "detail",
}

