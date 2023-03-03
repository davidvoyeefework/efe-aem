import Handlebars from "handlebars/runtime.js";
import Footer from "../footer.hbs";
import data from "../../social-links/docs/social-links.json";
Handlebars.registerPartial("Footer", Footer);

export default {
  title: "Components/Footer",
  argTypes: {
    variant: {
      options: ["primary"],
      control: { type: "radio" },
    },
    content: {
      control: { type: "text" },
    },
  },
};

const Template = ({ label, ...args }) => Footer({...args});
export const Primary = Template.bind();
Primary.args = {
  variation: "mobile",
  data
};

