import HandleBars from "handlebars/runtime";
import ServiceButtonList from "../service-btn-list.hbs";

HandleBars.registerPartial("ServiceButtonList", ServiceButtonList);

export default {
    title: "Core Components/List",
};

const TemplateServiceButtonList = args => ServiceButtonList({ ...args });
export const serviceButtonList = TemplateServiceButtonList.bind({});

serviceButtonList.argTypes = {
    gap: {
        control: "radio",
        options: ["tight", "moderate", "loose"],
    },
    variation: {
        control: "radio",
        options: ["horizontal", "column"],
    },
    alignment: {
        control: "radio",
        options: [
            "left-align",
            "right-align",
            "center-align",
        ],
    },
    'border-radius': {
        control: 'boolean',
    }
};

serviceButtonList.args = {
    variation: "horizontal",
    gap: "tight",
    alignment: "left-align",
    'border-radius': false,
};
