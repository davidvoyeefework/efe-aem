import TitleEFE from "../title.hbs";
import "../../../site/main.scss";

export default {
  title: "Core Components/Title/Headings",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["heading-01", "heading-02", "heading-03", "heading-04", "subhead-01", "subhead-02"],
      control: { type: "radio" },
    }
  },
};

const TemplateHeadings = ({ label, ...args }) => TitleEFE({ ...args });
export const Headings = TemplateHeadings.bind();
Headings.args = {
  variant: "heading-01",
}

