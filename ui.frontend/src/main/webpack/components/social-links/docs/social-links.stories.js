import Handlebars from "handlebars/runtime.js";
import SocialLinks from "../social-links.hbs";
import SocialLinksMobile from "../social-links-mobile.hbs";

import data from "../docs/social-links.json";

Handlebars.registerPartial("SocialLinks", SocialLinks);
Handlebars.registerPartial("SocialLinksMobile", SocialLinksMobile);

export default {
  title: "Components/SocialLinks",
  argTypes: {},
};

const Template = ({ ...args }) => SocialLinks({ ...args });
export const Primary = Template.bind();
Primary.args = {
  data,
};

const MobileTemplate = ({ ...args }) => SocialLinksMobile({ ...args });
export const Mobile = MobileTemplate.bind();
Mobile.args = {
  variation: "mobile",
  data,
};
