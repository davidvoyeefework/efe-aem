import Handlebars from "handlebars/runtime.js";
import Search from "../search.hbs";

Handlebars.registerPartial("Search", Search);
export default {
  title: "Core Components/Search",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    placeholder: {
        control: { type: "text" },
    },
    variant: {
      options: ["primary"],
      control: { type: "radio" },
    }
  },
};

const SearchEFE = ({ label, ...args }) => Search({ ...args });
export const SearchPrimary = SearchEFE.bind();
SearchPrimary.args = {
  variant: "primary",
  text: "search",
  placeholder: "Search ..."
}

