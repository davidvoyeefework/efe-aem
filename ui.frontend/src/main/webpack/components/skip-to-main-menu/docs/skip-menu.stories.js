import Handlebars from "handlebars/runtime.js";
import SkipMenu from "../skip-menu.hbs";

Handlebars.registerPartial("SkipMenu", SkipMenu);

export default {
  title: "Components/SkipMenu",
};

const TemplateSkipMenu = ({ ...args }) => SkipMenu({ ...args });
export const SkipToMainMenu = TemplateSkipMenu.bind();

