import HandleBars from "handlebars/runtime";
import List from "../list.hbs";
import ListWithTeaser from "../list-with-teaser.hbs";

HandleBars.registerPartial("List", List);
HandleBars.registerPartial("ListWithTeaser", ListWithTeaser);

export default {
    title: "Core Components/List",
};

const FooterList = ({ ...args }) => List({ ...args });
export const footer = FooterList.bind({});
footer.args = {
    variation: "footer",
};

const TemplateList = (args) => ListWithTeaser({ ...args });
export const listLayout = TemplateList.bind({});

listLayout.argTypes = {
    gap: {
        control: "radio",
        options: ["tight", "moderate", "loose"],
    },
    variation: {
        control: "radio",
        options: ["horizontal", "vertical", "column"],
    },
    alignment: {
        control: "radio",
        options: [
            "left-align",
            "right-align",
            "center-align",
            "top-align",
            "middle-align",
            "bottom-align",
        ],
    },
    'border-radius': {
        control: 'boolean',
    }
};

listLayout.args = {
    variation: "horizontal",
    gap: "moderate",
    alignment: "left-align",
    'border-radius': false,
};
