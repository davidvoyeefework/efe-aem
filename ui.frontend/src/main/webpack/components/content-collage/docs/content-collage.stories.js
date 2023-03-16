import Handlebars from "handlebars/runtime.js";
import ContentCollage from "../content-collage.hbs";

Handlebars.registerPartial("ContentCollage", ContentCollage);

export default {
  title: "Components/Content-Collage",
  argTypes: {
  },
};

const TemplateCollageRight = (args) => ContentCollage({ ...args });
export const RightLarge = TemplateCollageRight.bind({});
RightLarge.args = {
    variation: 'right-large'
};

const TemplateCollageLeft = (args) => ContentCollage({ ...args });
export const LeftLarge = TemplateCollageLeft.bind({});
LeftLarge.args = {
    variation: 'left-large'
};

const TemplateCollageTop = (args) => ContentCollage({ ...args });
export const TopLarge = TemplateCollageTop.bind({});
TopLarge.args = {
    variation: 'top-large'
};

const TemplateCollageBottom = (args) => ContentCollage({ ...args });
export const BottomLarge = TemplateCollageBottom.bind({});
BottomLarge.args = {
    variation: 'bottom-large'
};

export { ContentCollage };

