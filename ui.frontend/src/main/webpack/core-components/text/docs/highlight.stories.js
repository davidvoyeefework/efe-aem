import Handlebars from "handlebars/runtime.js";
import High from "../highlight.hbs";

Handlebars.registerPartial("High", High);
export default {
  title: "Core Components/Text",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["highlight"],
      control: { type: "radio" },
    }
  },
};

const TextVariations = ({ label, ...args }) => High({ ...args });
export const Highlight = TextVariations.bind();
Highlight.args = {
  variant: "highlight",
  text: "Sed egestas fringilla phasellus faucibus scelerisque. Etiam sit amet nisl purus inmollis nunc sed id. Bibendum neque egestas commodo."
}

