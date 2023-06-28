import Handlebars from "handlebars/runtime.js";
import RobustButton from "../robust-button.hbs";
Handlebars.registerPartial("RobustButton", RobustButton);
// More on default export: https://storybook.js.org/docs/html/writing-stories/introduction#default-export
export default {
  title: "Core Components/Links",
  // More on argTypes: https://storybook.js.org/docs/html/api/argtypes
  argTypes: {
    text: {
      control: { type: "text" },
    },
    variant: {
      options: ["service-financial", "service-giving", "service-corporate", "service-tax", "service-estate", "service-insurance", "service-investment", "service-small-business", "service-estate", "service-retirement"],
      control: { type: "radio" },
    },
    link: {
      control: { type: "text" },
    },
  },
};

const TemplatePrimary = ({ label, ...args }) => RobustButton({ ...args });
export const RobustButtonTemplate = TemplatePrimary.bind();
RobustButtonTemplate.args = {
  variant: "primary-nav-link",
}
