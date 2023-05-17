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
    varaition: {
      options: ["cmp-teaser--image-left", "cmp-teaser--image-left-rte"],
      control: {type:"radio"}
    },
    text: {
      control: { type: "text" },
    },
    showPretitle: {
      control: { type: "boolean" },
    },
  },
};
const TemplateEfeTeaser = ({ label, ...args }) => TeaserImageLeft({ ...args });
export const TeaserImageLeftPage = TemplateEfeTeaser.bind();
TeaserImageLeftPage.args = {
  variant: "image left",
}