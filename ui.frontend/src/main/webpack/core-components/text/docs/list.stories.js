import Handlebars from "handlebars/runtime.js";
import TextList from "../list.hbs";

Handlebars.registerPartial("TextList", TextList);
export default {
  title: "Core Components/Text",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    variant: {
      options: ["cmp-text--list-large"],
      control: { type: "radio" },
    }
  },
};

const TextListVariations = ({ label, ...args }) => TextList({ ...args });
export const TextListComponent = TextListVariations.bind();
TextListComponent.args = {
  variant: "table",
}

