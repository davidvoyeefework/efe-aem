import TitleEFE from "../title.hbs";
import "../../../site/main.scss";

export default {
  title: "Core Components/Title",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["heading-01", "heading-02", "heading-03"],
      control: { type: "radio" },
    }
  },
};

const TemplateEFETitle = ({ label, ...args }) => TitleEFE({ ...args });
export const TitleEFEDefault = TemplateEFETitle.bind();
TitleEFEDefault.args = {
  variant: "heading-01",
}

