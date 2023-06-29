import LinkedImage from "../efe-logo-linked.hbs";
import Handlebars from "handlebars/runtime.js";
Handlebars.registerPartial("EfeLogo", LinkedImage);
export default {
  title: "Core Components/Image",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    link: {
      control: { type: "text" },
    },
    variant: {
      options: [ "efe-logo-primary" ],
      control: { type: "radio" },
    },
    altText : {
      control: 'text'
    },
    titleText : {
      control: 'text'
    },
    size: {
      control: {type: "select"},
      options: ["small"]
    },
    id: {
      control: 'text'
    }
  },
};

const TemplateLinkedImage = ({ label, ...args }) => LinkedImage({ ...args });
export const EFELogo = TemplateLinkedImage.bind();
EFELogo.args = {
  link: "https://www.edelmanfinancialengines.com/", 
  variant: "efe-logo-primary",
  altText: "Edelman Financial Engines Logo",
  size: "small",
  titleText: "Edelman Financial Engines Homepage"
}

// const TemplateSvgGif = ({ label, ...args }) => SvgGifImage();
// export const SvgGif = TemplateSvgGif.bind();
