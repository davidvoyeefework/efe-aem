import Handlebars from "handlebars/runtime.js";
import Text from "../text.hbs";
Handlebars.registerPartial("ButtonText", Text);
export default {
  title: "Core Components/Text",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["button-text"],
      control: { type: "radio" },
    }
  },
};

const TextVariations = ({ label, ...args }) => Text({ ...args });
export const Button = TextVariations.bind();
Button.args = {
  variant: "button-text",
  text:"Connect with a planner"
}

