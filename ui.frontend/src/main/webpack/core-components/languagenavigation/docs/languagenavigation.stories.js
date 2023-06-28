import "../../../site/main.scss";
import LanguageNavSingleLevel from "../singlelevel.hbs";
import LanguageNavMultiLevel from "../multilevel.hbs";
import LanguageNavCountryList from "../language-navigation-country-list.hbs";

export default {
    title: "Core Components/LanguageNavigation",
    argTypes: {},
};
const TemplateSingleLevel = ({ label, ...args }) => LanguageNavSingleLevel();
export const SingleLevel = TemplateSingleLevel.bind();

const TemplateMultiLevel = ({ label, ...args }) => LanguageNavMultiLevel();
export const MultiLevel = TemplateMultiLevel.bind();

const TemplateLanguageNavCountryList = (args) =>
    LanguageNavCountryList({ ...args });
export const countryList = TemplateLanguageNavCountryList.bind();
countryList.args = {
    variation: "country-list",
    enableLastTitle: false,
};
