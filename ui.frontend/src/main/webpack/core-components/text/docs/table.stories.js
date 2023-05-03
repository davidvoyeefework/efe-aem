import Handlebars from "handlebars/runtime.js";
import Tables from "../table.hbs";

Handlebars.registerPartial("Tables", Tables);
export default {
  title: "Core Components/Text",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    variant: {
      options: ["table", "table-horizontal-scroll"],
      control: { type: "radio" },
    }
  },
};

const TextVariations = ({ label, ...args }) => Tables({ ...args });
export const Table = TextVariations.bind();
Table.args = {
  variant: "table",
}

