import Handlebars from "handlebars/runtime.js";
import TeaserThumbnail from "../teaser-thumbnail.hbs";

Handlebars.registerPartial("TeaserThumbnail", TeaserThumbnail);
export default {
  title: "Core Components/Teaser",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    serviceIcon: {
    //   options: ["service-financial", "service-giving", "service-corporate", "service-tax", "service-estate", "service-insurance", "service-investment", "service-small-business", "service-estate", "service-retirement"],
    //   control: { type: "radio" },
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
const TemplateEfeTeaser = ({ label, ...args }) => TeaserThumbnail({ ...args });
export const TeaserThumbnailPage = TemplateEfeTeaser.bind();
TeaserThumbnailPage.args = {
  variant: "",
}
