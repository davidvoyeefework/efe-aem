import MinimalHeader from "../minimal-header.hbs";

export default {
  title: "Components/Header/EFE minimal header",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {},
};
const TemplateMinimalHeader = (args) => MinimalHeader({ ...args });
TemplateMinimalHeader.bind({});
TemplateMinimalHeader.args = {
  'with-two-logos': false
}
export { TemplateMinimalHeader };
