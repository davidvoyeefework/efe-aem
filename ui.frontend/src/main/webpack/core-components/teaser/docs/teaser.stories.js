import Handlebars from "handlebars/runtime.js";
import "../../../site/main.scss";
import HeroTeaser from "../hero-teaser.hbs";

Handlebars.registerPartial("HeroTeaser", HeroTeaser);
export default {
  title: "Core Components/Teaser",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["hero", "banner", "service-details-block"],
      control: { type: "radio" },
    }
  },
};
const TemplateEfeTeaser = ({ label, ...args }) => HeroTeaser({ ...args });
export const HeroTeaserHome = TemplateEfeTeaser.bind();
HeroTeaserHome.args = {
  variant: "hero",
}