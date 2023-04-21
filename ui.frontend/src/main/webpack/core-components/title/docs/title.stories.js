import Handlebars from "handlebars/runtime.js";
import TitleEFE from "../title.hbs";
import "../../../site/main.scss";
Handlebars.registerPartial("Title", TitleEFE);
export default {
  title: "Core Components/Title/Headings",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["stats-01", "stats-02", "stats-02-underline", "heading-01", "heading-02", "heading-03", "heading-04", "subhead-01", "subhead-02"],
      control: { type: "radio" },
    }
  },
};

const TemplateHeadings = ({ label, ...args }) => TitleEFE({ ...args });
export const Headings = TemplateHeadings.bind();
Headings.args = {
  variant: "heading-01",
  text:"Edelman Financial Engines Typography styles"
}

const TitleSuperScriptTemplate = (args) => TitleEFE({...args});
export const TitleWithSuperScript = TitleSuperScriptTemplate.bind({});
TitleWithSuperScript.args = {
  variation: "superplus",
  variant: "stats-01",
  text: "145",
}
