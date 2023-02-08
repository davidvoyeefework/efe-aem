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
      options: ["body-01", "body-02", "body-03"],
      control: { type: "radio" },
    }
  },
};

const TextVariations = ({ label, ...args }) => Text({ ...args });
export const BodyCopy = TextVariations.bind();
BodyCopy.args = {
  variant: "body-02",
}

