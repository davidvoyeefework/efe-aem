import Handlebars from "handlebars/runtime.js";
import MinimalHeader from "../minimal-header.hbs";
Handlebars.registerPartial("MinimalHeader", MinimalHeader);
export default {
  title: "Components/Header/EFE minimal header",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {},
};
const TemplateMinimalHeader = (args) => MinimalHeader({ ...args });
TemplateMinimalHeader.bind({});
TemplateMinimalHeader.args = {
  'with-two-logos': false,
  'sponsor-logo': true
}
export { TemplateMinimalHeader };
