import Handlebars from "handlebars/runtime.js";
import TeaserImageLeft from "../teaser-image-left.hbs";

Handlebars.registerPartial("TeaserImageLeft", TeaserImageLeft);
export default {
  title: "Core Components/Teaser",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    serviceIcon: {
      options: ["service-financial", "service-giving", "service-corporate", "service-tax", "service-estate", "service-insurance", "service-investment", "service-small-business", "service-estate", "service-retirement"],
      control: { type: "radio" },
    },
    showButton: {
      control: { type: "boolean" },
    },
    showPretitle: {
      control: { type: "boolean" },
    },
    text: {
      control: { type: "text" },
    },
  },
};
const TemplateEfeTeaser = ({ label, ...args }) => TeaserImageLeft({ ...args });
export const TeaserImageLeftPage = TemplateEfeTeaser.bind();
TeaserImageLeftPage.args = {
  variant: "image left",
}