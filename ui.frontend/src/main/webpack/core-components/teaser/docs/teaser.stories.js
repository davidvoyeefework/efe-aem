import Handlebars from "handlebars/runtime.js";
import "../../../site/main.scss";
import HeroTeaser from "../hero-teaser.hbs";

Handlebars.registerPartial("HeroTeaser", HeroTeaser);
export default {
  title: "Core Components/Teaser",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    variant: {
      options: ["hero", "banner", "image-left", "image-right", "image-bottom"],
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
const TemplateEfeTeaser = ({ label, ...args }) => HeroTeaser({ ...args });
export const HeroTeaserHome = TemplateEfeTeaser.bind();
HeroTeaserHome.args = {
  variant: "hero",
}