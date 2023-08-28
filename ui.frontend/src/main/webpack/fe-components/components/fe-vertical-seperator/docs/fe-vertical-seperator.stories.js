import FeVerticalSep from "../fe-vertical-seperator.hbs";

export default {
    title: "FE Components/FeVerticalSep",
    argTypes: {
    },
};
const TemplateFeVerticalSep = ({ label, ...args }) => FeVerticalSep({...args});
export const verticalSeperator = TemplateFeVerticalSep.bind();


