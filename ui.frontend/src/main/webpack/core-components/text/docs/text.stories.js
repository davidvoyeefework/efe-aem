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
      options: ["body-01", "body-02", "body-03", "caption"],
      control: { type: "radio" },
    },
    color: {
      options: ["deep-charcoal"],
      control: { type: "radio" },
    },
    class: {
      control: { type: "text" },
    },
  },
};

const TextVariations = ({ label, ...args }) => Text({ ...args });
export const BodyCopy = TextVariations.bind();
BodyCopy.args = {
    variant: "body-02",
    text:"Edelman Financial Engines Typography styles",
}

